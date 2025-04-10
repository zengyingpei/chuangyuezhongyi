"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
require("../../store/index.js");
const _sfc_main = {
  data() {
    return {
      linkId: null,
      // 存储当前聊天的id
      doctorId: null,
      // 存储医生的id
      input_msg: "",
      // 输入框的内容	
      contents: [],
      // 聊天数据
      websocket: null,
      // ws服务
      time: ""
      // 当前时间
    };
  },
  // 钩子函数
  onLoad(option) {
    this.linkId = option.linkId;
    this.doctorId = option.doctorId;
    this.getHistory(option.linkId);
    this.initWebSocket();
  },
  onUnload() {
    common_vendor.index.closeSocket({
      success: () => {
        console.log("连接关闭");
      }
    });
  },
  // onShow(option){
  // 	this.linkId = option.linkId;
  // 	this.doctorId = option.doctorId;
  // 	this.getHistory(option.linkId);
  // },
  methods: {
    // 获取历史聊天记录
    getHistory(linkId) {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/chat/one`,
        method: "GET",
        header: {
          "authorization": token
        },
        data: {
          chatlinkId: linkId
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            this.contents = res.data.data;
          } else {
            common_vendor.index.showToast({
              duration: 1e3,
              icon: "error",
              title: "数据加载失败"
            });
          }
        }
      });
    },
    // 更新输入框的内容
    onWrite(e) {
      this.input_msg = e.detail.value;
      console.log(this.input_msg);
    },
    // 初始化连接websocket
    initWebSocket() {
      let sid = "user|" + this.$store.state.id;
      console.log("sid=", sid);
      this.websocket = common_vendor.index.connectSocket({
        url: `${common_js_utils.wsUrl}/ws/inquiry/` + sid,
        success: () => {
          console.log("连接成功");
        }
      });
      this.websocket.onOpen((res) => {
        console.log("连接成功", res);
      });
      this.websocket.onMessage((res) => {
        console.log(res);
        this.getTime();
        this.contents.push({
          receiver: this.$store.state.id,
          sender: this.doctorId,
          content: res.data,
          time: this.time
        });
      });
    },
    // 发送消息
    send() {
      let msg = this.input_msg;
      if (msg === "")
        return;
      console.log(msg);
      this.websocket.send({
        data: `${this.doctorId}|` + msg,
        success: () => {
          this.getTime();
          this.contents.push({
            receiver: this.doctorId,
            sender: this.$store.state.id,
            content: msg,
            time: this.time
          });
          this.input_msg = "";
        }
      });
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/chat/add`,
        method: "POST",
        header: {
          "authorization": token
        },
        data: {
          linkId: this.linkId,
          receiver: this.doctorId,
          content: msg,
          state: 0
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
          } else {
            common_vendor.index.showToast({
              duration: 1e3,
              icon: "error",
              title: "数据加载失败"
            });
          }
        }
      });
    },
    // 获取当前时间的函数
    getTime() {
      var date = /* @__PURE__ */ new Date();
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      var hour = date.getHours();
      var minute = date.getMinutes();
      var second = date.getSeconds();
      var time = year + "-" + this.addZero(month) + "-" + this.addZero(day) + " " + this.addZero(hour) + ":" + this.addZero(minute) + ":" + this.addZero(second);
      this.time = time;
    },
    //小于10的拼接上0字符串
    addZero(s) {
      return s < 10 ? "0" + s : s;
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.f($data.contents, (item, index, i0) => {
      return common_vendor.e({
        a: item.sender == $data.doctorId
      }, item.sender == $data.doctorId ? {
        b: common_vendor.t(item.time)
      } : {
        c: common_vendor.t(item.time)
      }, {
        d: item.sender == $data.doctorId
      }, item.sender == $data.doctorId ? {
        e: common_vendor.t(item.content)
      } : {
        f: common_vendor.t(item.content)
      }, {
        g: index
      });
    }),
    b: common_vendor.o([($event) => $data.input_msg = $event.detail.value, (...args) => $options.onWrite && $options.onWrite(...args)]),
    c: $data.input_msg,
    d: common_vendor.o((...args) => $options.send && $options.send(...args))
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-013fa921"], ["__file", "D:/chuangyue/front/chuangyue/pages/message/chat.vue"]]);
wx.createPage(MiniProgramPage);
