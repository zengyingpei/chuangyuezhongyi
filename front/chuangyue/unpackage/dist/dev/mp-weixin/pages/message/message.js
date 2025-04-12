"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const _sfc_main = {
  data() {
    return {
      lists: []
    };
  },
  onLoad(option) {
    this.getList();
  },
  methods: {
    // 获取列表
    getList() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/chat/list`,
        method: "GET",
        header: {
          authorization: token
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            this.lists = res.data.data;
          } else {
            common_vendor.index.showToast({
              duration: 1e3,
              icon: "error",
              title: "数据获取失败"
            });
          }
        }
      });
    },
    // chat
    toChat(linkId, doctorId) {
      common_vendor.index.navigateTo({
        url: `/pages/message/chat?linkId=${linkId}&doctorId=${doctorId}`
      });
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.f($data.lists, (item, index, i0) => {
      return {
        a: common_vendor.t(item.doctorName),
        b: common_vendor.t(item.createTime),
        c: item.id,
        d: common_vendor.o(($event) => $options.toChat(item.id, item.doctorId), item.id)
      };
    })
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/毕业设计/projects/front/chuangyue/pages/message/message.vue"]]);
wx.createPage(MiniProgramPage);
