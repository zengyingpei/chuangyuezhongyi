<template>
  <div class="box">
    <div class="aside">
      <div class="group">
        <div
          class="item"
          v-for="item in lists"
          :key="item.id"
          :style="select_user == item.userId ? 'color: red' : 'color:white'"
          @click="selectUser(item.userId, item.id)"
        >
          {{ item.username }}
        </div>
      </div>
    </div>
    <div class="main">
      <div class="chat_box">
        <div class="chat_content">
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
            style="width: 90vw; height: 16vh"
            name=""
            id=""
            cols=""
            rows=""
            v-model="input"
          ></textarea>
        </div>
        <div class="chat_operate">
          <button
            type="button"
            class="btn btn-primary"
            id="submit"
            @click="send"
          >
            发送
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { baseUrl } from "../../common/util.js";
import { wsUrl } from "../../common/util.js";
import $ from "jquery";
import { ref } from "vue";
import { useStore } from "vuex";

export default {
  setup: () => {
    const store = useStore(); // 引入store

    let lists = ref([]); // 问诊列表
    let select_user = ref(0); // 当前问诊用户
    let linkId = ref(0); // 当前问诊id
    let input = ref(""); // 获取输入框的文本

    let websocket = null; // websocket对象
    let messages = ref([]); // 聊天记录

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
            lists.value = res.data;
            console.log(lists.value);
          }
        },
      });
    };

    // 连接时的回调函数
    const onopen = () => {
      console.log("连接成功");
    };

    // 连接失败的回调函数
    const onerror = () => {
      console.log("连接失败");
    };

    // 接收到消息的回调函数
    const onmessage = (e) => {
      // console.log(e);
      // console.log(e.data);
      // printMessage(e.data);

      messages.value.push({
        content: e.data,
        time: getTime(),
        sender: select_user.value,
        receiver: store.state.id,
      });
    };

    // 连接关闭的回调函数
    const onclose = () => {
      console.log("连接关闭");
    };

    // 监听窗口关闭事件，当窗口关闭的时候，要先关闭连接，在关闭窗口
    window.onbeforeunload = function () {
      if (websocket != null) {
        websocket.close();
      }
    };

    // 发送消息
    const send = () => {
      // printMessage(input.value);

      messages.value.push({
        content: input.value,
        time: getTime(),
        sender: store.state.id,
        receiver: select_user.value,
      });

      let msg = `${select_user.value}` + "|" + input.value;
      console.log("发送消息", msg);
      websocket.send(msg);

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
          content: input.value,
          state: 0,
        }),
        success: (res) => {
          if (res.code == 1) {
            console.log(res);
          }
        },
      });

      input.value = "";
    };

    // // 打印消息
    // const printMessage = (msg)=>{
    //   messages.value.push({
    //     content: msg,
    //     time: getTime(),

    //   });
    //   console.log(messages.value);
    // }

    // 初始化websocket
    const initWebSocket = (doctorId) => {
      let sid = "doctor" + "|" + doctorId;
      websocket = new WebSocket(`${wsUrl}` + "/ws/inquiry/" + sid);
      websocket.addEventListener("open", onopen);
      websocket.addEventListener("error", onerror);
      websocket.addEventListener("message", onmessage);
      websocket.addEventListener("close", onclose);
    };

    // 点击选择用户
    const selectUser = (userId, chatLinkId) => {
      // 建立新的websocket连接之前，先把旧的关掉
      if (websocket != null) {
        websocket.close();
      }

      // 更新当前问诊用户
      select_user.value = userId;
      linkId.value = chatLinkId;

      // 初始化websocket
      let me_id = store.state.id;
      initWebSocket(me_id);

      getMessage(chatLinkId);
    };

    // 获取聊天记录
    const getMessage = (chatLinkId) => {
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
            console.log(res.data);
            messages.value = res.data;
          }
        },
      });
    };

    const getTime = () => {
      var date = new Date();
      //年 getFullYear()：四位数字返回年份
      var year = date.getFullYear(); //getFullYear()代替getYear()
      //月 getMonth()：0 ~ 11
      var month = date.getMonth() + 1;
      //日 getDate()：(1 ~ 31)
      var day = date.getDate();
      //时 getHours()：(0 ~ 23)
      var hour = date.getHours();
      //分 getMinutes()： (0 ~ 59)
      var minute = date.getMinutes();
      //秒 getSeconds()：(0 ~ 59)
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

    const addZero = (s) => {
      return s < 10 ? "0" + s : s;
    };

    // 调用函数
    getList();

    return {
      lists,
      select_user,
      selectUser,
      input,
      send,
      messages,
    };
  },
};
</script>

<style scoped>
.box {
  display: flex;
  height: 91vh;
}

.aside {
  width: 10vw;
  height: 92vh;
  background-color: aqua;
}

.main {
  width: 90vw;
  height: 92vh;
  box-sizing: border-box;
  /* background-color: antiquewhite; */
}

.item {
  height: 4vh;
  line-height: 4vh;
  font-size: 2vh;
  text-align: center;
}

.chat_box {
}

.chat_content {
  height: 70vh;
  overflow: auto;
}

.chat_input {
  height: 17vh;
}

.chat_operate {
  height: 5vh;
  display: flex;
  /* text-align: right; */
}

#submit {
  width: 6vw;
  font-size: 2vh;
  height: 5vh;
  color: white;
  float: right;
  margin-left: 80vw;
}

.chat_item {
  width: 85vw;
  margin: 1vh;
  box-sizing: border-box;
}

.chat_item_time_left {
  font-size: 1vw;
  color: #6d746e;
}
.chat_item_box_left {
}
.chat_item_content_left {
  font-size: 1.2vw;
  border: 2px solid white;
  border-radius: 2vh;
  box-shadow: 0 0 1vh #6d746e;
  width: max-content;
  padding: 0.2vh 1vh;
}
.chat_item_time_right {
  font-size: 1vw;
  color: #6d746e;
  text-align: right;
}
.chat_item_box_right {
  display: flex;
  justify-content: flex-end;
}
.chat_item_content_right {
  font-size: 1.2vw;
  border: 2px solid white;
  border-radius: 2vh;
  box-shadow: 0 0 1vh #6d746e;
  width: max-content;
  padding: 0.2vh 1vh;
  background-color: #6ea3e5;
}
</style>
