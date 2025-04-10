"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const store_index = require("../../store/index.js");
const _sfc_main = {
  data() {
    return {};
  },
  computed: {
    username() {
      return store_index.store.state.username;
    }
  },
  onLoad(option) {
    console.log(common_js_utils.baseUrl);
  },
  methods: {
    send1() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/department`,
        header: {
          authorization: token
        },
        method: "GET",
        success: (res) => {
          console.log(res.data);
        }
      });
    },
    send2() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: "http://localhost:8080/api/user/sickness",
        method: "GET",
        header: {
          authorization: token
        },
        data: {
          deptId: 2
        },
        success: (res) => {
          console.log(res.data);
        }
      });
    },
    send3() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: "http://localhost:8080/api/user/doctor",
        method: "POST",
        header: {
          "authorization": token
        },
        data: {
          page: 1,
          pageSize: 2,
          sicknessId: 2
        },
        success: (res) => {
          console.log(res.data);
        }
      });
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.o((...args) => _ctx.getPhoneNumber && _ctx.getPhoneNumber(...args)),
    b: common_vendor.o((...args) => $options.send1 && $options.send1(...args)),
    c: common_vendor.o((...args) => $options.send2 && $options.send2(...args)),
    d: common_vendor.o((...args) => $options.send3 && $options.send3(...args)),
    e: common_vendor.t($options.username)
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/chuangyue/front/chuangyue/pages/text/text.vue"]]);
wx.createPage(MiniProgramPage);
