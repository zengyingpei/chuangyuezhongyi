<template>
  <div class="box">
    <div class="aside">
      <div class="group">
        <div
          class="item"
          v-for="item in lists"
          :key="item.id"
          :class="{ 'selected-item': select_user == item.userId }"
          @click="selectUser(item.userId, item.id, item.username, item.state)" 
        >
          {{ item.username }}
          <!-- 如果 state 为 1，显示已结束标识 -->
          <span v-if="item.state === 1" class="ended-indicator">(问诊结束)</span>
        </div>
      </div>
    </div>
    <div class="main">
      <div class="chat_box">
        <div class="chat_content">
          <div class="chat_head">
            <div class="chat_head_title">
              <!-- 显示当前聊天对象的名字 -->
              与 {{ select_user_name || "..." }} 的聊天
              <!-- 如果当前问诊已结束，也显示标识 -->
              <span v-if="select_user_state === 1" class="ended-indicator-header">(已结束)</span>
            </div>
            <div class="functions" v-if="select_user">
              <!-- 添加功能按钮，只有选中用户后才显示 -->
              <!-- 如果问诊已结束，禁用按钮 -->
              <button
                class="function-button add_case"
                @click="openCaseModal"
                :disabled="select_user_state === 1"
              >
                填写病历
              </button>
              <!-- 未来可以添加更多功能按钮 -->
            </div>
          </div>
          <div class="chat_item" v-for="item in messages" :key="item.id">
            <div class="chat_item_time_left" v-if="item.sender == select_user">
              {{ item.time }}
            </div>

            <div class="chat_item_time_right" v-else>
              {{ item.time }}
            </div>

            <div class="chat_item_box_left" v-if="item.sender == select_user">
              <div class="chat_item_content_left">
                {{ item.content }}
              </div>
            </div>

            <div class="chat_item_box_right" v-else>
              <div class="chat_item_content_right">
                {{ item.content }}
              </div>
            </div>
          </div>
        </div>
        <div class="chat_input">
          <textarea
            class="input-textarea"
            placeholder="请输入消息..."
            v-model="input"
            :disabled="!select_user || select_user_state === 1" 
          ></textarea>
        </div>
        <div class="chat_operate">
          <button
            type="button"
            class="btn btn-primary submit-button"
            id="submit"
            @click="send"
            :disabled="!input.trim() || !select_user || select_user_state === 1" 
          >
            发送
          </button>
        </div>
      </div>
    </div>

    <!-- 病历填写模态框 -->
    <div v-if="showCaseModal" class="modal-overlay" @click.self="closeCaseModal">
      <div class="modal-content">
        <h3 class="modal-title">为 {{ select_user_name }} 填写病历</h3>
        <div class="modal-form">
          <div class="form-group">
            <label for="symptoms">症状信息</label>
            <textarea id="symptoms" v-model="caseForm.symptoms" rows="3"></textarea>
          </div>
          <div class="form-group">
            <label for="diagnosis">诊断信息</label>
            <textarea id="diagnosis" v-model="caseForm.diagnosis" rows="3"></textarea>
          </div>
          <div class="form-group">
            <label for="treatment">治疗方案</label>
            <textarea id="treatment" v-model="caseForm.treatmentPlan" rows="3"></textarea>
          </div>
        </div>
        <div class="modal-actions">
          <button class="modal-button cancel-button" @click="closeCaseModal">取消</button>
          <button class="modal-button submit-case-button" @click="submitCase">提交病历</button>
        </div>
         <div v-if="caseSubmitMessage" :class="['submit-message', caseSubmitStatus === 'success' ? 'success' : 'error']">
            {{ caseSubmitMessage }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { baseUrl } from "../../common/util.js";
import { wsUrl } from "../../common/util.js";
import $ from "jquery";
import { ref, reactive, nextTick } from "vue";
import { useStore } from "vuex";

export default {
  setup: () => {
    const store = useStore();

    let lists = ref([]);
    let select_user = ref(0);
    let select_user_name = ref("");
    let select_user_state = ref(null); // 新增：存储当前选中问诊的状态 (0: 进行中, 1: 已结束)
    let linkId = ref(0);
    let input = ref("");

    let websocket = null;
    let messages = ref([]);
    const chatContentRef = ref(null);

    // --- 新增：病历模态框相关状态 ---
    let showCaseModal = ref(false);
    const caseForm = reactive({
        symptoms: '',
        diagnosis: '',
        treatmentPlan: ''
    });
    let caseSubmitMessage = ref('');
    let caseSubmitStatus = ref('');


    // 获取问诊列表
    const getList = () => {
      console.log(baseUrl);
      $.ajax({
        url: `${baseUrl}/api/doctor/chat/list`,
        type: "GET",
        headers: {
          authorization: localStorage.getItem("token"),
        },
        success: (res) => {
          if (res.code == 1) {
            lists.value = res.data || [];
            console.log(lists.value);
          } else {
            console.error("获取问诊列表失败:", res.message);
            lists.value = [];
          }
        },
        error: (err) => {
            console.error("获取问诊列表请求失败:", err);
            lists.value = [];
        }
      });
    };

    // --- 新增：滚动到底部函数 ---
    const scrollToBottom = () => {
        nextTick(() => {
            const chatContent = chatContentRef.value;
            if (chatContent) {
                chatContent.scrollTop = chatContent.scrollHeight;
            }
        });
    };
    // --- 结束：新增 ---

    // 连接时的回调函数
    const onopen = () => {
      console.log("WebSocket 连接成功");
    };

    // 连接失败的回调函数
    const onerror = (err) => {
      console.error("WebSocket 连接失败:", err);
    };

    // 接收到消息的回调函数
    const onmessage = (e) => {
      console.log("收到消息:", e.data);
      // 检查是否是心跳消息或其他非聊天内容
      if (e.data && e.data !== 'pong') { // 假设心跳回复是 'pong'
          messages.value.push({
            content: e.data,
            time: getTime(),
            sender: select_user.value,
            receiver: store.state.id,
          });
          scrollToBottom();
      } else {
          console.log("收到非聊天消息或空消息:", e.data);
      }
    };
    const onclose = () => {
      console.log("WebSocket 连接关闭");
    };

    // 监听窗口关闭事件
    window.onbeforeunload = function () {
      if (websocket != null && websocket.readyState === WebSocket.OPEN) {
        websocket.close();
      }
    };

    // 发送消息
    const send = () => {
        // 添加检查，如果问诊已结束则不允许发送
        if (select_user_state.value === 1) {
            console.warn("无法发送消息：当前问诊已结束");
            return;
        }
        if (!input.value.trim() || !select_user.value || !websocket || websocket.readyState !== WebSocket.OPEN) {
            console.warn("无法发送消息：输入为空、未选择用户或WebSocket未连接");
            return;
        }

        const messageContent = input.value;
        messages.value.push({
            content: messageContent,
            time: getTime(),
            sender: store.state.id,
            receiver: select_user.value,
        });
        scrollToBottom();

        let msg = `${select_user.value}|${messageContent}`;
        console.log("发送消息:", msg);
        websocket.send(msg);

        // 同时将消息存入数据库
        $.ajax({
            url: `${baseUrl}/api/doctor/chat/add`,
            type: "POST",
            headers: {
            authorization: localStorage.getItem("token"),
            },
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
            linkId: linkId.value,
            receiver: select_user.value,
            content: messageContent,
            state: 0,
            }),
            success: (res) => {
            if (res.code == 1) {
                console.log("消息存储成功:", res);
            } else {
                console.error("消息存储失败:", res.message);
            }
            },
            error: (err) => {
                console.error("消息存储请求失败:", err);
            }
        });

        input.value = ""; // 清空输入框
    };


    // 初始化websocket
    const initWebSocket = (doctorId) => {
      if (!doctorId) {
          console.error("无法初始化WebSocket：医生ID无效");
          return;
      }
      let sid = `doctor|${doctorId}`;
      const wsFullUrl = `${wsUrl}/ws/inquiry/${sid}`;
      console.log("尝试连接WebSocket:", wsFullUrl);

      // 确保旧连接已关闭
      if (websocket && websocket.readyState !== WebSocket.CLOSED) {
          websocket.close();
      }

      websocket = new WebSocket(wsFullUrl);
      websocket.addEventListener("open", onopen);
      websocket.addEventListener("error", onerror);
      websocket.addEventListener("message", onmessage);
      websocket.addEventListener("close", onclose);
    };

    // 点击选择用户
    const selectUser = (userId, chatLinkId, username, state) => { // 添加 state 参数
      if (select_user.value === userId) return;

      console.log(`选择用户: ID=${userId}, LinkID=${chatLinkId}, Name=${username}, State=${state}`);

      select_user.value = userId;
      select_user_name.value = username;
      select_user_state.value = state; // 存储问诊状态
      linkId.value = chatLinkId;
      messages.value = [];

      let me_id = store.state.id;
      if (me_id) {
          // 只有在问诊进行中 (state !== 1) 时才初始化 WebSocket
          if (state !== 1) {
              initWebSocket(me_id);
          } else {
              // 如果问诊已结束，确保关闭可能存在的旧连接
              if (websocket && websocket.readyState !== WebSocket.CLOSED) {
                  websocket.close();
                  websocket = null; // 清空引用
              }
              console.log("问诊已结束，不初始化WebSocket连接");
          }
      } else {
          console.error("无法操作WebSocket：未获取到当前医生ID");
      }

      getMessage(chatLinkId);
    };

    // 获取聊天记录
    const getMessage = (chatLinkId) => {
        if (!chatLinkId) return;
        $.ajax({
            url: `${baseUrl}/api/doctor/chat/one`,
            type: "GET",
            headers: {
            authorization: localStorage.getItem("token"),
            },
            data: {
            chatlinkId: chatLinkId,
            },
            success: (res) => {
            if (res.code == 1) {
                console.log("获取聊天记录成功:", res.data);
                messages.value = res.data || [];
                scrollToBottom();
            } else {
                console.error("获取聊天记录失败:", res.message);
                messages.value = [];
            }
            },
            error: (err) => {
                console.error("获取聊天记录请求失败:", err);
                messages.value = [];
            }
        });
    };

    // 获取当前时间
    const getTime = () => {
      var date = new Date();
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      var hour = date.getHours();
      var minute = date.getMinutes();
      var second = date.getSeconds();
      var now =
        year +
        "-" +
        addZero(month) +
        "-" +
        addZero(day) +
        " " +
        addZero(hour) +
        ":" +
        addZero(minute) +
        ":" +
        addZero(second);
      return now;
    };

    // 补零函数
    const addZero = (s) => {
      return s < 10 ? "0" + s : s;
    };

    const openCaseModal = () => {
        // 清空上次的提交消息和状态
        caseSubmitMessage.value = '';
        caseSubmitStatus.value = '';
        showCaseModal.value = true;
    };

    const closeCaseModal = () => {
        showCaseModal.value = false;
    };

    const submitCase = () => {
        caseSubmitMessage.value = ''; // 清空之前的消息
        caseSubmitStatus.value = '';

        // 简单的前端校验
        if (!caseForm.symptoms || !caseForm.diagnosis || !caseForm.treatmentPlan) {
            caseSubmitMessage.value = '请填写所有病历信息！';
            caseSubmitStatus.value = 'error';
            return;
        }

        console.log("提交病历数据:", {
            chatlinkId: linkId.value,
            symptoms: caseForm.symptoms,
            diagnosis: caseForm.diagnosis,
            treatmentPlan: caseForm.treatmentPlan,
        });

        $.ajax({
            url: `${baseUrl}/api/doctor/case/createcase`,
            type: "POST",
            headers: {
                authorization: localStorage.getItem("token"),
            },
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                linkId: linkId.value, // 注意后端API需要的字段名
                symptoms: caseForm.symptoms,
                diagnosis: caseForm.diagnosis,
                treatment: caseForm.treatmentPlan,
            }),
            success: (res) => {
                if (res.code == 1) {
                    console.log("病历提交成功:", res);
                    caseSubmitMessage.value = '病历提交成功！';
                    caseSubmitStatus.value = 'success';
                    select_user_state.value = 1;

                    // 2. 更新左侧列表对应项的状态
                    const currentLinkId = linkId.value; // 保存当前 linkId
                    const index = lists.value.findIndex(item => item.id === currentLinkId);
                    if (index !== -1) {
                        lists.value[index].state = 1;
                    }


                    setTimeout(() => {
                       closeCaseModal();
                       caseForm.symptoms = '';
                       caseForm.diagnosis = '';
                       caseForm.treatmentPlan = '';
                    }, 1500);
                } else {
                    console.error("病历提交失败:", res.message);
                    caseSubmitMessage.value = `病历提交失败: ${res.message}`;
                    caseSubmitStatus.value = 'error';
                }
            },
            error: (err) => {
                console.error("病历提交请求失败:", err);
                caseSubmitMessage.value = '病历提交请求失败，请检查网络或联系管理员。';
                caseSubmitStatus.value = 'error';
            }
        });
    };
    // --- 结束：新增 ---

    // 调用函数
    getList();

    return {
      lists,
      select_user,
      select_user_name, // 返回用户名
      select_user_state, // 导出 state
      selectUser,
      input,
      send,
      messages,
      chatContentRef, // 返回引用

      // --- 新增：返回模态框相关 ---
      showCaseModal,
      caseForm,
      openCaseModal,
      closeCaseModal,
      submitCase,
      caseSubmitMessage,
      caseSubmitStatus,
      // --- 结束：新增 ---
    };
  },
};
</script>

<style scoped>
.box {
  display: flex;
  height: calc(100vh - 8vh); /* 假设顶部导航栏高度为 8vh */
  background-color: #f4f5f7;
}

.aside {
  width: 200px; /* 固定宽度可能比 vw 更好 */
  height: 100%;
  background-color: #2e3238; /* 深色侧边栏 */
  overflow-y: auto;
  border-right: 1px solid #e0e0e0;
}

.group {
    padding-top: 10px;
}

.aside .item {
  height: 50px; /* 增加高度 */
  line-height: 50px; /* 垂直居中 */
  font-size: 15px; /* 调整字体大小 */
  text-align: left; /* 左对齐 */
  padding-left: 20px; /* 增加左内边距 */
  color: #c1c1c1; /* 默认文字颜色 */
  cursor: pointer;
  border-bottom: 1px solid #3a3f45; /* 分隔线 */
  transition: background-color 0.3s, color 0.3s;
  white-space: nowrap; /* 防止换行 */
  overflow: hidden; /* 隐藏溢出文本 */
  text-overflow: ellipsis; /* 显示省略号 */
}

.aside .item:hover {
  background-color: #3a3f45; /* 悬停背景色 */
  color: #ffffff;
}

.aside .item.selected-item { /* 选中项的样式 */
  background-color: #4a90e2; /* 选中背景色 */
  color: #ffffff; /* 选中文字颜色 */
  font-weight: bold;
}


.main {
  flex-grow: 1; /* 占据剩余空间 */
  height: 100%;
  display: flex; /* 使用 flex 布局 */
  flex-direction: column; /* 垂直排列 */
  background-color: #ffffff;
}

.chat_box {
    display: flex;
    flex-direction: column;
    height: 100%; /* 占据 main 的全部高度 */
}

.chat_content {
  flex-grow: 1; /* 占据可用空间 */
  overflow-y: auto; /* 超出内容滚动 */
  padding: 15px;
  border-bottom: 1px solid #e0e0e0;
}

.chat_head {
    display: flex;
    justify-content: space-between; /* 两端对齐 */
    align-items: center; /* 垂直居中 */
    padding: 10px 15px;
    border-bottom: 1px solid #e0e0e0;
    background-color: #f9f9f9;
    min-height: 50px; /* 最小高度 */
}

.chat_head_title {
    font-size: 16px;
    font-weight: bold;
    color: #333;
    display: flex; /* 让标题和标识在同一行 */
    align-items: center;
}

.ended-indicator {
    font-size: 12px;
    color: #888; /* 灰色标识 */
    font-style: italic;
    margin-left: 5px; /* 和用户名保持一点距离 */
    white-space: nowrap; /* 防止换行 */
}

.aside .item.selected-item .ended-indicator {
    color: #eee; /* 选中时标识颜色变浅 */
}


.functions {
    display: flex;
    gap: 10px; /* 按钮间距 */
}

.function-button {
    padding: 5px 12px;
    font-size: 13px;
    border: 1px solid #4a90e2;
    background-color: #ffffff;
    color: #4a90e2;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s, color 0.3s;
}

.function-button:hover {
    background-color: #4a90e2;
    color: #ffffff;
}


.chat_input {
  padding: 10px;
  border-top: 1px solid #e0e0e0;
  background-color: #f9f9f9;
}

.input-textarea { /* 修改 textarea 样式 */
    width: 100%;
    height: 80px; /* 合理的高度 */
    border: 1px solid #dcdfe6;
    border-radius: 4px;
    padding: 8px 12px;
    font-size: 14px;
    resize: none; /* 禁止调整大小 */
    box-sizing: border-box;
    transition: border-color 0.3s;
}

.input-textarea:focus {
    outline: none;
    border-color: #4a90e2;
}

.input-textarea:disabled {
    background-color: #f5f7fa;
    cursor: not-allowed;
}


.chat_operate {
  padding: 10px 15px;
  text-align: right; /* 按钮右对齐 */
  background-color: #f9f9f9;
}

.submit-button { /* 修改发送按钮样式 */
    width: auto; /* 自动宽度 */
    padding: 8px 20px; /* 调整内边距 */
    font-size: 14px; /* 调整字体大小 */
    height: auto; /* 自动高度 */
    /* color: white; */ /* bootstrap 默认 */
    /* background-color: #4a90e2; */ /* bootstrap 默认 */
    /* border: none; */ /* bootstrap 默认 */
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
    float: none; /* 移除 float */
    margin-left: 0; /* 移除 margin-left */
}

.submit-button:disabled {
    opacity: 0.6;
    cursor: not-allowed;
}

.chat_item {
  width: 100%; /* 占满容器宽度 */
  margin-bottom: 15px; /* 增加消息间距 */
  box-sizing: border-box;
}

.chat_item_time_left, .chat_item_time_right {
  font-size: 12px; /* 调整时间字体大小 */
  color: #999999; /* 时间颜色变浅 */
  text-align: center; /* 时间居中显示 */
  margin-bottom: 5px; /* 时间和内容的间距 */
}

.chat_item_box_left {
    display: flex;
    justify-content: flex-start; /* 左对齐 */
}
.chat_item_content_left {
  font-size: 14px; /* 调整内容字体大小 */
  border: none; /* 移除边框 */
  border-radius: 8px; /* 圆角 */
  box-shadow: none; /* 移除阴影 */
  width: auto; /* 自动宽度 */
  max-width: 60%; /* 最大宽度 */
  padding: 10px 12px; /* 调整内边距 */
  background-color: #e9e9e9; /* 左侧气泡背景色 */
  color: #333;
  word-wrap: break-word; /* 自动换行 */
  position: relative; /* 用于添加小三角 */
}

.chat_item_content_left::before {
    content: '';
    position: absolute;
    left: -6px;
    top: 10px;
    width: 0;
    height: 0;
    border-style: solid;
    border-width: 6px 8px 6px 0;
    border-color: transparent #e9e9e9 transparent transparent;
}


.chat_item_box_right {
  display: flex;
  justify-content: flex-end; /* 右对齐 */
}
.chat_item_content_right {
  font-size: 14px; /* 调整内容字体大小 */
  border: none; /* 移除边框 */
  border-radius: 8px; /* 圆角 */
  box-shadow: none; /* 移除阴影 */
  width: auto; /* 自动宽度 */
  max-width: 60%; /* 最大宽度 */
  padding: 10px 12px; /* 调整内边距 */
  background-color: #4a90e2; /* 右侧气泡背景色 */
  color: #ffffff; /* 右侧文字颜色 */
  word-wrap: break-word; /* 自动换行 */
  position: relative; /* 用于添加小三角 */
}

.chat_item_content_right::after {
    content: '';
    position: absolute;
    right: -6px;
    top: 10px;
    width: 0;
    height: 0;
    border-style: solid;
    border-width: 6px 0 6px 8px;
    border-color: transparent transparent transparent #4a90e2;
}



.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5); /* 半透明遮罩 */
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000; /* 确保在顶层 */
}

.modal-content {
    background-color: #fff;
    padding: 25px;
    border-radius: 8px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
    width: 90%;
    max-width: 500px; /* 最大宽度 */
    max-height: 80vh; /* 最大高度 */
    overflow-y: auto; /* 内容过多时滚动 */
}

.modal-title {
    margin-top: 0;
    margin-bottom: 20px;
    font-size: 18px;
    font-weight: bold;
    color: #333;
    text-align: center;
}

.modal-form .form-group {
    margin-bottom: 15px;
}

.modal-form label {
    display: block;
    margin-bottom: 5px;
    font-weight: 500;
    color: #555;
    font-size: 14px;
}

.modal-form textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 14px;
    box-sizing: border-box;
    resize: vertical; /* 允许垂直调整大小 */
    min-height: 60px; /* 最小高度 */
}

.modal-form textarea:focus {
    outline: none;
    border-color: #4a90e2;
    box-shadow: 0 0 0 2px rgba(74, 144, 226, 0.2);
}

.modal-actions {
    margin-top: 25px;
    display: flex;
    justify-content: flex-end; /* 按钮右对齐 */
    gap: 10px; /* 按钮间距 */
}

.modal-button {
    padding: 8px 18px;
    border-radius: 4px;
    font-size: 14px;
    cursor: pointer;
    border: 1px solid transparent;
    transition: background-color 0.3s, border-color 0.3s, color 0.3s;
}

.cancel-button {
    background-color: #f0f0f0;
    color: #555;
    border-color: #ddd;
}

.cancel-button:hover {
    background-color: #e0e0e0;
    border-color: #ccc;
}

.submit-case-button {
    background-color: #4a90e2;
    color: #fff;
    border-color: #4a90e2;
}

.submit-case-button:hover {
    background-color: #3a7bc8;
    border-color: #3a7bc8;
}


.submit-message {
    margin-top: 15px;
    padding: 10px;
    border-radius: 4px;
    font-size: 14px;
    text-align: center;
}

.submit-message.success {
    background-color: #e6f7f2;
    color: #18a058;
    border: 1px solid #a0d9c4;
}

.submit-message.error {
    background-color: #fdeaea;
    color: #d03050;
    border: 1px solid #f5b1bb;
}


</style>
