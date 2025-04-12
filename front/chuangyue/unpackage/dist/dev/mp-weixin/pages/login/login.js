"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const store_index = require("../../store/index.js");
const _sfc_main = {
  data() {
    return {
      avatarUrl: "",
      userName: "",
      code: ""
    };
  },
  onLoad(option) {
  },
  methods: {
    bindblur(e) {
      this.userName = e.detail.value;
    },
    bindinput(e) {
      this.userName = e.detail.value;
    },
    onChooseavatar(e) {
      let {
        avatarUrl
      } = e.detail;
      this.avatarUrl = avatarUrl;
    },
    onSubmit() {
      let that = this;
      common_vendor.index.login({
        success: (res) => {
          that.code = res.code;
          console.log("res ", that.code);
          common_vendor.index.request({
            url: `${common_js_utils.baseUrl}/api/user/user/login`,
            method: "POST",
            data: {
              code: that.code
            },
            success: (res2) => {
              if (res2.data.code == 1) {
                console.log("成功 ", res2.data);
                store_index.store.commit("updateToken", res2.data.data.token);
                store_index.store.commit("updateId", res2.data.data.id);
                store_index.store.commit("updateAvatar", this.avatarUrl);
                store_index.store.commit("updateUsername", this.userName);
                store_index.store.commit("updateIsLogin", 1);
                common_vendor.index.setStorageSync("authorization", res2.data.data.token);
                common_vendor.index.showToast({
                  duration: 1e3,
                  icon: "success",
                  title: "登录成功"
                });
                common_vendor.index.switchTab({
                  url: "/pages/index/index"
                });
              } else {
                console.log("失败 ", res2.data.message);
              }
            },
            fail: (res2) => {
              console.log("登录失败", res2.errMsg);
            }
          });
        }
      });
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: $data.avatarUrl,
    b: common_vendor.o((...args) => $options.onChooseavatar && $options.onChooseavatar(...args)),
    c: $data.userName,
    d: common_vendor.o((...args) => $options.bindblur && $options.bindblur(...args)),
    e: common_vendor.o((...args) => $options.bindinput && $options.bindinput(...args)),
    f: common_vendor.o((...args) => $options.onSubmit && $options.onSubmit(...args))
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-e4e4508d"], ["__file", "D:/毕业设计/projects/front/chuangyue/pages/login/login.vue"]]);
wx.createPage(MiniProgramPage);
