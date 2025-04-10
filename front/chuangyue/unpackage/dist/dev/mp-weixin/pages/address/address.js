"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const _sfc_main = {
  data() {
    return {
      arr: []
      //存放所有的地址信息
    };
  },
  onLoad(option) {
    this.getDatas();
  },
  onShow() {
    this.getDatas();
  },
  methods: {
    getDatas() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/address/list`,
        method: "GET",
        header: {
          "authorization": token
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            this.arr = res.data.data;
          }
        }
      });
    },
    // 去更新地址信息
    onUpdate(addressId) {
      common_vendor.index.navigateTo({
        url: `/pages/address/update_address?addressId=${addressId}`
      });
    },
    // 新增地址
    onAdd() {
      common_vendor.index.navigateTo({
        url: "/pages/address/add_address"
      });
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.o((...args) => $options.onAdd && $options.onAdd(...args)),
    b: common_vendor.f($data.arr, (item, index, i0) => {
      return common_vendor.e({
        a: common_vendor.t(item.province),
        b: common_vendor.t(item.city),
        c: common_vendor.t(item.district),
        d: common_vendor.t(item.detail),
        e: common_vendor.t(item.receiverName),
        f: common_vendor.t(item.receiverPhone),
        g: item.isDefault == 1
      }, item.isDefault == 1 ? {} : {}, {
        h: common_vendor.o(($event) => $options.onUpdate(item.id), item.id),
        i: item.id
      });
    })
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-40ca010a"], ["__file", "D:/chuangyue/front/chuangyue/pages/address/address.vue"]]);
wx.createPage(MiniProgramPage);
