<template>
  <div class="schedule-container">
    <h2>接诊排班设置</h2>
    <div v-if="loading" class="loading">加载中...</div>
    <div v-else-if="error" class="error-message">{{ error }}</div>
    <div v-else class="schedule-grid">
      <div v-for="daySchedule in weeklySchedule" :key="daySchedule.date" class="day-column">
        <div class="day-header">{{ formatDate(daySchedule.date) }} ({{ getDayOfWeek(daySchedule.date) }})</div>
        <div class="time-slot">
          <label :for="'am-' + daySchedule.date">上午 (AM)</label>
          <div class="input-wrapper">
            <input
              type="number"
              :id="'am-' + daySchedule.date"
              v-model.number="daySchedule.am.surplus"
              min="0"
              class="surplus-input"
              @change="markAsModified(daySchedule.date, 0)"
              
            />
            <!-- 当 id 不存在时，显示“暂无排班” -->
            <span v-if="!daySchedule.am.id" class="no-schedule-indicator">暂无排班</span>
            <!-- 当 id 存在且 surplus 为 0 时，显示“已约满” -->
            <span v-else-if="daySchedule.am.id && daySchedule.am.surplus === 0" class="fully-booked-indicator">已约满</span>
          </div>
          <span class="slot-id" v-if="daySchedule.am.id">ID: {{ daySchedule.am.id }}</span>
        </div>
        <div class="time-slot">
          <label :for="'pm-' + daySchedule.date">下午 (PM)</label>
           <div class="input-wrapper">
            <input
              type="number"
              :id="'pm-' + daySchedule.date"
              v-model.number="daySchedule.pm.surplus"
              min="0"
              class="surplus-input"
              @change="markAsModified(daySchedule.date, 1)"
              
            />
         
            <span v-if="!daySchedule.pm.id" class="no-schedule-indicator">暂无排班</span>
            <!-- 新增：当 id 存在且 surplus 为 0 时，显示“已约满” -->
            <span v-else-if="daySchedule.pm.id && daySchedule.pm.surplus === 0" class="fully-booked-indicator">已约满</span>
          </div>
           <span class="slot-id" v-if="daySchedule.pm.id">ID: {{ daySchedule.pm.id }}</span>
        </div>
      </div>
    </div>
    <div class="actions">
      <button @click="saveSchedule" :disabled="!isModified || saving" class="save-button">
        {{ saving ? '保存中...' : '保存排班' }}
      </button>
       <div v-if="saveMessage" :class="['save-message', saveStatus === 'success' ? 'success' : 'error']">
            {{ saveMessage }}
        </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue';
import $ from 'jquery';
import { baseUrl } from '../../common/util.js';
import { useStore } from 'vuex';

export default {
  setup() {
    const store = useStore();
    const weeklySchedule = ref([]);
    const originalSchedule = ref({});
    const modifiedSlots = ref(new Set());
    const loading = ref(true);
    const error = ref('');
    const saving = ref(false);
    const saveMessage = ref('');
    const saveStatus = ref('');

    // 生成未来7天的日期
    const generateWeekDates = () => {
      const dates = [];
      const today = new Date();
      for (let i = 0; i < 7; i++) {
        const date = new Date(today);
        date.setDate(today.getDate() + i);
        dates.push(date.toISOString().split('T')[0]); // 格式 YYYY-MM-DD
      }
      return dates;
    };

    // 获取排班数据
    const fetchSchedule = () => {
      loading.value = true;
      error.value = '';
      const doctorId = store.state.id;
       if (!doctorId) {
           error.value = "无法获取医生信息，请重新登录。";
           loading.value = false;
           return;
       }

      $.ajax({
          url: `${baseUrl}/api/doctor/schedule/week`, 
          type: 'GET',
          headers: {
              authorization: localStorage.getItem("token"),
          },
        
          success: (res) => {
              if (res.code === 1) {
                  originalSchedule.value = res.data || [];
                  processScheduleData(originalSchedule.value);
              } else {
                  error.value = `获取排班失败: ${res.message}`;
                  initializeEmptySchedule();
              }
          },
          error: (err) => {
              console.error("获取排班请求失败:", err);
              error.value = '获取排班数据失败，请检查网络或联系管理员。';
              initializeEmptySchedule();
          },
          complete: () => {
              loading.value = false;
          }
      });
    };

    const processScheduleData = (backendData) => {
        const dates = generateWeekDates();
        const scheduleMap = new Map();
        backendData.forEach(item => {
            // --- 修改：使用小写的 'ap' 字段 ---
            const key = `${item.date}_${item.ap}`;
            scheduleMap.set(key, item);
        });

        weeklySchedule.value = dates.map(date => {
            const amData = scheduleMap.get(`${date}_0`) || { date: date, ap: 0, surplus: 0, id: null };
            const pmData = scheduleMap.get(`${date}_1`) || { date: date, ap: 1, surplus: 0, id: null };
            return {
                date: date,
                am: { ...amData },
                pm: { ...pmData }
            };
        });
        modifiedSlots.value.clear();
    };

    // 初始化空的排班表（获取数据失败时调用）
    const initializeEmptySchedule = () => {
        const dates = generateWeekDates();
        weeklySchedule.value = dates.map(date => ({
            date: date,
            
            am: { date: date, ap: 0, surplus: 0, id: null },
            pm: { date: date, ap: 1, surplus: 0, id: null }
        }));
         modifiedSlots.value.clear();
    };


    // 标记时段已被修改
    const markAsModified = (date, ap) => {
        const key = `${date}_${ap}`;
        modifiedSlots.value.add(key);
    };

    // 计算是否被修改过
    const isModified = computed(() => modifiedSlots.value.size > 0);

    // 保存排班数据
    const saveSchedule = () => {
      saving.value = true;
      saveMessage.value = '';
      saveStatus.value = '';

      const payload = [];
      modifiedSlots.value.forEach(key => {
          const [date, apStr] = key.split('_');
          const ap = parseInt(apStr, 10);
          const dayData = weeklySchedule.value.find(d => d.date === date);
          if (dayData) {
              const slotData = (ap === 0) ? dayData.am : dayData.pm;
              const surplus = Math.max(0, slotData.surplus || 0);
              payload.push({
                  date: slotData.date,
                  ap: slotData.ap,
                  surplus: surplus
              });
          }
      });

      if (payload.length === 0) {
          saveMessage.value = '没有检测到修改。';
          saveStatus.value = 'error';
          saving.value = false;
          return;
      }

      console.log("准备保存的数据:", payload);

      $.ajax({
          url: `${baseUrl}/api/doctor/schedule/submit`,
          type: 'POST',
          headers: {
              authorization: localStorage.getItem("token"),
              'Content-Type': 'application/json'
          },
          data: JSON.stringify(payload),
          success: (res) => {
              if (res.code === 1) {
                  saveMessage.value = '排班保存成功！';
                  saveStatus.value = 'success';
                  fetchSchedule(); 
              } else {
                  saveMessage.value = `保存失败: ${res.message}`;
                  saveStatus.value = 'error';
              }
          },
          error: (err) => {
              console.error("保存排班请求失败:", err);
              saveMessage.value = '保存排班失败，请检查网络或联系管理员。';
              saveStatus.value = 'error';
          },
          complete: () => {
              saving.value = false;
              setTimeout(() => {
                  saveMessage.value = '';
                  saveStatus.value = '';
              }, 3000);
          }
      });
    };

    // 格式化日期显示
    const formatDate = (dateString) => {
      const date = new Date(dateString);
       const year = date.getFullYear();
       const month = (date.getMonth() + 1).toString().padStart(2, '0');
       const day = date.getDate().toString().padStart(2, '0');
       return `${year}-${month}-${day}`;
    };

    // 获取星期几
    const getDayOfWeek = (dateString) => {
      const date = new Date(dateString);
      const days = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];
      return days[date.getDay()];
    };


    onMounted(() => {
      fetchSchedule();
    });

    return {
      weeklySchedule,
      loading,
      error,
      saving,
      saveMessage,
      saveStatus,
      isModified,
      saveSchedule,
      formatDate,
      getDayOfWeek,
      markAsModified, 
    };
  }
};
</script>

<style scoped>
.schedule-container {
  padding: 20px;
  font-family: sans-serif;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.loading, .error-message {
  text-align: center;
  padding: 20px;
  font-size: 16px;
}
.error-message {
    color: red;
}

.schedule-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); /* 响应式列 */
  gap: 15px;
  margin-bottom: 20px;
}

.day-column {
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 15px;
  background-color: #f9f9f9;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
}

.day-header {
  font-weight: bold;
  margin-bottom: 15px;
  text-align: center;
  color: #555;
  font-size: 14px;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}

.time-slot {
  margin-bottom: 15px;
  background-color: #fff;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #e0e0e0;
}

.time-slot label {
  display: block;
  margin-bottom: 5px;
  font-size: 13px;
  color: #666;
  font-weight: 500;
}

.surplus-input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  font-size: 14px;
}
.surplus-input:focus {
    border-color: #4a90e2;
    outline: none;
    box-shadow: 0 0 0 2px rgba(74, 144, 226, 0.2);
}

.surplus-input::-webkit-outer-spin-button,
.surplus-input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
.surplus-input[type=number] {
  -moz-appearance: textfield;
}

.slot-id {
    display: block;
    font-size: 10px;
    color: #aaa;
    text-align: right;
    margin-top: 3px;
}

.actions {
  text-align: center;
  margin-top: 20px;
}

.save-button {
  padding: 10px 25px;
  background-color: #4a90e2;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.save-button:hover:not(:disabled) {
  background-color: #3a7bc8;
}

.save-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.save-message {
    margin-top: 15px;
    padding: 10px;
    border-radius: 4px;
    font-size: 14px;
    text-align: center;
}

.save-message.success {
    background-color: #e6f7f2;
    color: #18a058;
    border: 1px solid #a0d9c4;
}

.save-message.error {
    background-color: #fdeaea;
    color: #d03050;
    border: 1px solid #f5b1bb;
}

.input-wrapper {
    display: flex;
    align-items: center;
    gap: 5px;
    position: relative;
}

.surplus-input {
  width: 100%;
  box-sizing: border-box;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
}
/* 可选：为禁用的输入框添加样式 */
.surplus-input:disabled {
    background-color: #f8f8f8;
    cursor: not-allowed;
    color: #aaa;
}
.surplus-input:focus {
    border-color: #4a90e2;
    outline: none;
    box-shadow: 0 0 0 2px rgba(74, 144, 226, 0.2);
}
.surplus-input::-webkit-outer-spin-button,
.surplus-input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
.surplus-input[type=number] {
  -moz-appearance: textfield;
}

.slot-id {
    display: block;
    font-size: 10px;
    color: #aaa;
    text-align: right;
    margin-top: 3px;
}

.actions {
  text-align: center;
  margin-top: 20px;
}

.save-button {
  padding: 10px 25px;
  background-color: #4a90e2;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.save-button:hover:not(:disabled) {
  background-color: #3a7bc8;
}

.save-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.save-message {
    margin-top: 15px;
    padding: 10px;
    border-radius: 4px;
    font-size: 14px;
    text-align: center;
}

.save-message.success {
    background-color: #e6f7f2;
    color: #18a058;
    border: 1px solid #a0d9c4;
}

.save-message.error {
    background-color: #fdeaea;
    color: #d03050;
    border: 1px solid #f5b1bb;
}


.no-schedule-indicator {
    font-size: 12px;
    color: red; /* 保持红色 */
    white-space: nowrap;
    flex-shrink: 0;
}

/* 新增：“已约满”提示的样式 */
.fully-booked-indicator {
    font-size: 12px;
    color: orange; /* 使用橙色或其他区分颜色 */
    white-space: nowrap;
    flex-shrink: 0;
}
</style>
