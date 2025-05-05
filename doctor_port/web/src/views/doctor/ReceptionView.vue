<template>
  <div class="reception-container">
    <h2>预约接诊列表</h2>

    <!-- 新增：操作反馈信息区域 -->
    <div v-if="actionMessage" :class="['action-message', actionStatus === 'success' ? 'success' : 'error']">
      {{ actionMessage }}
    </div>

    <div v-if="loading" class="loading-indicator">加载中...</div>
    <div v-else-if="error" class="error-message">{{ error }}</div>
    <div v-else-if="groupedAppointments.length === 0" class="no-appointments">暂无预约记录</div>

    <div v-else class="appointments-list">
      <!-- 按日期分组 -->
      <div v-for="group in groupedAppointments" :key="group.date" class="date-group">
        <h3 class="date-header">{{ group.date }}</h3>

        <!-- 上午 -->
        <div v-if="group.slots['上午'].length > 0" class="slot-group">
          <h4>上午</h4>
          <div v-for="appointment in group.slots['上午']" :key="appointment.id" class="appointment-card">
            <div class="user-info">
              <p><strong>患者姓名:</strong> {{ appointment.user.name }}</p>
              <p><strong>联系电话:</strong> {{ appointment.user.phone || '未提供' }}</p>
              <p><strong>性别:</strong> {{ appointment.user.sex || '未提供' }}</p>
              <p><strong>状态:</strong> <span :class="`status-${appointment.status}`">{{ appointment.status }}</span></p>
            </div>
            <div class="actions">
              <!-- 修改：添加 :disabled="actionLoading" -->
              <button @click="handleComplete(appointment.id)" class="btn btn-complete" :disabled="appointment.status !== '待就诊' || actionLoading">
                {{ actionLoading ? '处理中...' : '完成接诊' }}
              </button>
              <button @click="handleReject(appointment.id)" class="btn btn-reject" :disabled="appointment.status !== '待就诊' || actionLoading">
                 {{ actionLoading ? '处理中...' : '拒绝接诊' }}
              </button>
            </div>
          </div>
        </div>

        <!-- 下午 -->
         <div v-if="group.slots['下午'].length > 0" class="slot-group">
          <h4>下午</h4>
          <div v-for="appointment in group.slots['下午']" :key="appointment.id" class="appointment-card">
             <div class="user-info">
              <p><strong>患者姓名:</strong> {{ appointment.user.name }}</p>
              <p><strong>联系电话:</strong> {{ appointment.user.phone || '未提供' }}</p>
              <p><strong>性别:</strong> {{ appointment.user.sex || '未提供' }}</p>
              <p><strong>状态:</strong> <span :class="`status-${appointment.status}`">{{ appointment.status }}</span></p>
            </div>
            <div class="actions">
               <!-- 修改：添加 :disabled="actionLoading" -->
              <button @click="handleComplete(appointment.id)" class="btn btn-complete" :disabled="appointment.status !== '待就诊' || actionLoading">
                 {{ actionLoading ? '处理中...' : '完成接诊' }}
              </button>
              <button @click="handleReject(appointment.id)" class="btn btn-reject" :disabled="appointment.status !== '待就诊' || actionLoading">
                 {{ actionLoading ? '处理中...' : '拒绝接诊' }}
              </button>
            </div>
          </div>
        </div>

      </div>
    </div>
    <!-- 新增：拒绝原因模态框 -->
    <div v-if="showRejectModal" class="modal-overlay" @click.self="closeRejectModal">
      <div class="modal-content">
        <h3>输入拒绝原因</h3>
        <textarea
          v-model="rejectReason"
          placeholder="请输入拒绝本次预约的原因..."
          rows="4"
          class="reason-textarea"
          :disabled="actionLoading"
        ></textarea>
        <!-- 在模态框内显示操作消息 -->
        <div v-if="actionMessage && showRejectModal" :class="['modal-action-message', actionStatus === 'success' ? 'success' : 'error']">
            {{ actionMessage }}
        </div>
        <div class="modal-actions">
          <button @click="closeRejectModal" class="btn btn-cancel" :disabled="actionLoading">取消</button>
          <button @click="submitRejection" class="btn btn-confirm" :disabled="actionLoading || !rejectReason.trim()">
            {{ actionLoading ? '提交中...' : '确认拒绝' }}
          </button>
        </div>
      </div>
    </div>
    <!-- 结束新增 -->

  </div>
</template>
  
<script>
import { ref, onMounted, computed } from 'vue';
import $ from 'jquery';
import { baseUrl } from '../../common/util.js';

export default {
  setup: () => {
    const appointments = ref([]);
    const loading = ref(true);
    const error = ref('');
    const actionLoading = ref(false);
    const actionMessage = ref('');
    const actionStatus = ref('');

    // --- 新增：模态框相关状态 ---
    const showRejectModal = ref(false); // 控制模态框显示
    const rejectAppointmentId = ref(null); // 当前要拒绝的预约ID
    const rejectReason = ref(''); // 拒绝原因
    // --- 结束新增 ---

    const fetchAppointments = () => {
      loading.value = true;
      error.value = '';
      appointments.value = [];

      $.ajax({
        url: `${baseUrl}/api/doctor/appointment/all`,
        type: 'GET',
        headers: {
          authorization: localStorage.getItem("token"),
        },
        success: (res) => {
          if (res.code === 1) {
            appointments.value = (res.data || []).sort((a, b) => {
              const dateComparison = a.date.localeCompare(b.date);
              if (dateComparison !== 0) {
                return dateComparison;
              }
              return a.slot === '上午' ? -1 : 1;
            });
          } else {
            error.value = `获取预约列表失败: ${res.message}`;
          }
        },
        error: (err) => {
          console.error("获取预约列表请求失败:", err);
          error.value = '获取预约列表失败，请检查网络或联系管理员。';
        },
        complete: () => {
          loading.value = false;
        }
      });
    };

    const groupedAppointments = computed(() => {
      const groups = {};
      appointments.value.forEach(app => {
        const date = app.date;
        const slot = app.slot;
        if (!groups[date]) {
          groups[date] = { '上午': [], '下午': [] };
        }
        if (groups[date][slot]) {
             groups[date][slot].push(app);
        } else {
            console.warn(`发现无效的时间段值: ${slot} for date ${date}`);
        }
      });
       return Object.entries(groups)
            .sort(([dateA], [dateB]) => dateA.localeCompare(dateB))
            .map(([date, slots]) => ({ date, slots }));
    });

    const handleComplete = (appointmentId) => {
      if (actionLoading.value) return;
      actionLoading.value = true;
      actionMessage.value = '';
      actionStatus.value = '';

      $.ajax({
        url: `${baseUrl}/api/doctor/appointment/finish`,
        type: 'POST',
        headers: {
          authorization: localStorage.getItem("token"),
          'Content-Type': 'application/json'
        },
        data: JSON.stringify({ id: appointmentId }),
        success: (res) => {
          if (res.code === 1) {
            actionMessage.value = '操作成功：已完成接诊。';
            actionStatus.value = 'success';
            fetchAppointments();
          } else {
            actionMessage.value = `操作失败: ${res.message}`;
            actionStatus.value = 'error';
          }
        },
        error: (err) => {
          console.error("完成接诊请求失败:", err);
          actionMessage.value = '操作失败，请检查网络或联系管理员。';
          actionStatus.value = 'error';
        },
        complete: () => {
          actionLoading.value = false;
        }
      });
    };

    // --- 修改：handleReject 现在只打开模态框 ---
    const handleReject = (appointmentId) => {
      if (actionLoading.value) return;
      rejectAppointmentId.value = appointmentId; // 存储ID
      rejectReason.value = ''; // 清空上次的原因
      showRejectModal.value = true; // 显示模态框
    };
    // --- 结束修改 ---

    // --- 新增：提交拒绝请求的函数 ---
    const submitRejection = () => {
      if (!rejectReason.value.trim()) {
        alert("拒绝原因不能为空！");
        return;
      }
      if (actionLoading.value) return;

      actionLoading.value = true;
      actionMessage.value = '';
      actionStatus.value = '';

      $.ajax({
        url: `${baseUrl}/api/doctor/appointment/refuse`,
        type: 'POST',
        headers: {
          authorization: localStorage.getItem("token"),
          'Content-Type': 'application/json'
        },
        data: JSON.stringify({
          id: rejectAppointmentId.value,
          reason: rejectReason.value
        }),
        success: (res) => {
          if (res.code === 1) {
            actionMessage.value = '操作成功：已拒绝接诊。';
            actionStatus.value = 'success';
            fetchAppointments(); // 刷新列表
            closeRejectModal(); // 关闭模态框
          } else {
            actionMessage.value = `操作失败: ${res.message}`;
            actionStatus.value = 'error';
             // 失败时保留模态框，让用户看到错误信息或修改原因
          }
        },
        error: (err) => {
          console.error("拒绝接诊请求失败:", err);
          actionMessage.value = '操作失败，请检查网络或联系管理员。';
          actionStatus.value = 'error';
           // 失败时保留模态框
        },
        complete: () => {
          actionLoading.value = false;
        }
      });
    };
    // --- 结束新增 ---

    // --- 新增：关闭模态框的函数 ---
    const closeRejectModal = () => {
      showRejectModal.value = false;
      rejectAppointmentId.value = null;
      rejectReason.value = '';
       // 如果有错误信息，可以选择在这里清除，或者让它显示一会儿
       // actionMessage.value = '';
       // actionStatus.value = '';
    };
    // --- 结束新增 ---


    onMounted(() => {
      fetchAppointments();
    });

    return {
      loading,
      error,
      groupedAppointments,
      handleComplete,
      handleReject,
      actionLoading,
      actionMessage,
      actionStatus,
      // --- 返回模态框相关状态和方法 ---
      showRejectModal,
      rejectReason,
      submitRejection,
      closeRejectModal,
      // --- 结束返回 ---
    };
  },
};
</script>
  
<style scoped>
.reception-container {
  padding: 20px;
  font-family: sans-serif;
  max-width: 900px; /* 限制最大宽度 */
  margin: 0 auto; /* 居中显示 */
}

h2 {
  text-align: center;
  margin-bottom: 15px; /* 调整间距给消息留空间 */
  color: #333;
}

/* 新增：操作反馈信息样式 */
.action-message {
  padding: 10px 15px;
  margin-bottom: 15px;
  border-radius: 4px;
  text-align: center;
  font-weight: bold;
}
.action-message.success {
  background-color: #e6f7f2;
  color: #18a058;
  border: 1px solid #a0e0c8;
}
.action-message.error {
  background-color: #fdeaea;
  color: #d03050;
  border: 1px solid #f5b1bb;
}


.loading-indicator, .error-message, .no-appointments {
  text-align: center;
  padding: 30px;
  font-size: 16px;
  color: #666;
}

.error-message {
  color: red;
  background-color: #fdeaea;
  border: 1px solid #f5b1bb;
  border-radius: 4px;
}

.no-appointments {
    background-color: #f0f0f0;
    border: 1px solid #e0e0e0;
    border-radius: 4px;
}

.appointments-list {
  margin-top: 20px;
}

.date-group {
  margin-bottom: 30px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  background-color: #f9f9f9;
  padding: 10px;
}

.date-header {
  font-size: 1.4em;
  color: #4a90e2;
  margin-bottom: 10px;
  padding-bottom: 5px;
  border-bottom: 1px solid #eee;
}

.slot-group {
  margin-bottom: 20px;
}

.slot-group h4 {
  font-size: 1.1em;
  color: #555;
  margin-bottom: 10px;
  padding-left: 5px;
  border-left: 3px solid #4a90e2;
}

.appointment-card {
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 6px;
  padding: 5px;
  margin-bottom: 5px;
  display: flex; /* 使用 flex 布局 */
  justify-content: space-between; /* 两端对齐 */
  align-items: center; /* 垂直居中 */
  box-shadow: 0 1px 3px rgba(0,0,0,0.05);
}

.user-info p {
  margin: 5px 0;
  font-size: 14px;
  color: #333;
}
.user-info p strong {
    color: #555;
    min-width: 70px; /* 标签宽度对齐 */
    display: inline-block;
}

/* 状态样式 */
.status-待就诊 {
    color: #ffa500; /* 橙色 */
    font-weight: bold;
}
.status-已完成 {
    color: #18a058; /* 绿色 */
    font-weight: bold;
}
.status-已取消 {
    color: #999; /* 灰色 */
    text-decoration: line-through;
}
.status-已拒绝 {
    color: #d03050; /* 红色 */
    font-weight: bold;
}


.actions {
  display: flex;
  gap: 10px; /* 按钮间距 */
}

.btn {
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s;
}

.btn-complete {
  background-color: #4CAF50; /* 绿色 */
  color: white;
}
.btn-complete:hover:not(:disabled) {
  background-color: #45a049;
}

.btn-reject {
  background-color: #f44336; /* 红色 */
  color: white;
}
.btn-reject:hover:not(:disabled) {
  background-color: #da190b;
}

.btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

/* 新增：模态框样式 */
.modal-overlay {
  position: fixed; /* 固定定位，覆盖整个屏幕 */
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6); /* 半透明黑色背景 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000; /* 确保在顶层 */
}

.modal-content {
  background-color: #fff;
  padding: 25px 30px;
  border-radius: 8px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  width: 90%;
  max-width: 450px; /* 限制最大宽度 */
  display: flex;
  flex-direction: column;
}

.modal-content h3 {
  margin-top: 0;
  margin-bottom: 15px;
  text-align: center;
  color: #333;
}

.reason-textarea {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  resize: vertical; /* 允许垂直调整大小 */
  box-sizing: border-box;
}
.reason-textarea:disabled {
    background-color: #f8f8f8;
    cursor: not-allowed;
}


/* 模态框内的操作消息样式 */
.modal-action-message {
  padding: 8px 12px;
  margin-bottom: 15px;
  border-radius: 4px;
  text-align: center;
  font-size: 14px;
  font-weight: bold;
}
.modal-action-message.success {
  background-color: #e6f7f2;
  color: #18a058;
  border: 1px solid #a0e0c8;
}
.modal-action-message.error {
  background-color: #fdeaea;
  color: #d03050;
  border: 1px solid #f5b1bb;
}


.modal-actions {
  display: flex;
  justify-content: flex-end; /* 按钮靠右 */
  gap: 10px;
  margin-top: 10px;
}

.btn-cancel {
  background-color: #aaa;
  color: white;
}
.btn-cancel:hover:not(:disabled) {
  background-color: #888;
}

.btn-confirm {
  background-color: #f44336; /* 保持拒绝按钮的红色 */
  color: white;
}
.btn-confirm:hover:not(:disabled) {
  background-color: #da190b;
}
/* 结束新增 */
</style>
  