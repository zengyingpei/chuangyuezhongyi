"use strict";
const common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  data() {
    return {};
  },
  methods: {
    goToBuyMedicine() {
      common_vendor.index.navigateTo({
        url: "../medicine/medicine"
      });
    },
    goToHeathy() {
      common_vendor.index.navigateTo({
        url: "../keep_medicine/keep_medicine"
      });
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.o((...args) => $options.goToBuyMedicine && $options.goToBuyMedicine(...args)),
    b: common_vendor.o((...args) => $options.goToHeathy && $options.goToHeathy(...args))
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/chuangyue/front/chuangyue/pages/shop/shop.vue"]]);
wx.createPage(MiniProgramPage);
