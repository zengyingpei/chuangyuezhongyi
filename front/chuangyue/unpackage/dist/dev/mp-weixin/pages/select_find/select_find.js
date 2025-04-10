"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const _sfc_main = {
  data() {
    return {
      departments: [],
      sickness: [],
      nowSelectedDept: null
      //用于存储当前被选中的科室索引
    };
  },
  onLoad(option) {
    this.getDatas();
  },
  methods: {
    getDatas() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/department`,
        method: "GET",
        header: {
          authorization: token
        },
        success: (res) => {
          if (res.data.code == 1) {
            this.departments = res.data.data;
            console.log(this.departments);
          } else {
            common_vendor.index.showToast({
              duration: 1e3,
              title: "数据加载失败",
              icon: "error"
            });
          }
        }
      });
    },
    getSickness(deptId) {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/sickness`,
        method: "GET",
        header: {
          authorization: token
        },
        data: {
          deptId
        },
        success: (res) => {
          if (res.data.code == 1) {
            this.sickness = res.data.data;
          } else {
            common_vendor.index.showToast({
              duration: 1e3,
              title: "数据加载失败",
              icon: "error"
            });
          }
        }
      });
      this.handSelect(deptId);
    },
    handSelect(idx) {
      this.nowSelectedDept = idx;
    },
    isSelected(idx) {
      return this.nowSelectedDept == idx;
    },
    //跳转到新页面展示对应的医生
    getDoctors(sicknessId, sicknessName) {
      common_vendor.index.navigateTo({
        url: `/pages/doctor_list/doctor_list?sicknessId=${sicknessId}&sicknessName=${sicknessName}`
      });
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.f($data.departments, (item, index, i0) => {
      return {
        a: common_vendor.t(item.name),
        b: item.id,
        c: common_vendor.o(($event) => $options.getSickness(item.id), item.id),
        d: $options.isSelected(item.id) ? "#7ca0ec" : "black"
      };
    }),
    b: common_vendor.f($data.sickness, (item, index, i0) => {
      return {
        a: common_vendor.t(item.name),
        b: item.id,
        c: common_vendor.o(($event) => $options.getDoctors(item.id, item.name), item.id)
      };
    })
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/chuangyue/front/chuangyue/pages/select_find/select_find.vue"]]);
wx.createPage(MiniProgramPage);
