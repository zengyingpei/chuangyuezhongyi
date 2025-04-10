"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const _sfc_main = {
  data() {
    return {
      docId: "",
      //接收上一个页面传过来的医生的id 
      futureDays: []
      // 存储所有可以预约的信息
    };
  },
  onLoad(option) {
    this.docId = option.docId;
    console.log("传过来的医生id ", option.docId);
    this.getAppointment(option.docId);
  },
  methods: {
    getAppointment(docId) {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/appointment/list`,
        method: "GET",
        header: {
          "authorization": token
        },
        data: {
          docId
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            this.futureDays = res.data.data;
          }
        }
      });
    },
    goSubmit(slotId) {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/appointment/add`,
        method: "POST",
        header: {
          "authorization": token
        },
        data: {
          slotId,
          doctorId: this.docId,
          status: 1
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
          }
        }
      });
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.f($data.futureDays, (item, index, i0) => {
      return {
        a: common_vendor.t(item.date),
        b: common_vendor.t(item.slot),
        c: common_vendor.t(item.available),
        d: common_vendor.o(($event) => $options.goSubmit(item.id), item.id),
        e: item.id
      };
    })
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-1e5d1fa0"], ["__file", "D:/chuangyue/front/chuangyue/pages/registration/registration.vue"]]);
wx.createPage(MiniProgramPage);
