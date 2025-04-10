"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const _sfc_main = {
  data() {
    return {
      name: "",
      phone: "",
      gender: "",
      idNumber: ""
    };
  },
  onLoad(option) {
  },
  methods: {
    onSubmit() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/user/update`,
        method: "POST",
        header: {
          authorization: token
        },
        data: {
          name: this.name,
          phone: this.phone,
          sex: this.gender,
          idNumber: this.idNumber
        },
        success: (res) => {
          if (res.data.code == 1) {
            common_vendor.index.showToast({
              duration: 2e3,
              title: "修改成功",
              icon: "success"
            });
          } else {
            common_vendor.index.showToast({
              title: "修改失败",
              duration: 2e3,
              icon: "fail"
            });
          }
        },
        fail: (res) => {
          common_vendor.index.showToast({
            title: "修改失败",
            duration: 2e3,
            icon: "fail"
          });
        }
      });
    },
    radioChange(e) {
      this.gender = e.detail.value;
    },
    nameChange(e) {
      this.name = e.detail.value;
    },
    phoneChange(e) {
      this.phone = e.detail.value;
    },
    idNumberChange(e) {
      this.idNumber = e.detail.value;
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.o((...args) => $options.nameChange && $options.nameChange(...args)),
    b: common_vendor.o((...args) => $options.phoneChange && $options.phoneChange(...args)),
    c: common_vendor.o((...args) => $options.radioChange && $options.radioChange(...args)),
    d: common_vendor.o((...args) => $options.idNumberChange && $options.idNumberChange(...args)),
    e: common_vendor.o((...args) => $options.onSubmit && $options.onSubmit(...args))
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/chuangyue/front/chuangyue/pages/my/update_user_message.vue"]]);
wx.createPage(MiniProgramPage);
