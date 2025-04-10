"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const _sfc_main = {
  data() {
    return {
      searchValue: "",
      sicknessId: null,
      //当前展示的是什么疾病(id)
      sicknessName: null,
      //当前展示的是什么疾病(name)	
      doctors: null
      //获取该疾病对应的所有医生
    };
  },
  onLoad(option) {
    this.sicknessId = option.sicknessId;
    this.sicknessName = option.sicknessName;
    this.getDatas(this.sicknessId);
  },
  methods: {
    onInput(value) {
      this.searchValue = value;
    },
    onConfirm() {
      this.onSearch(this.searchValue);
    },
    onSearch(value) {
      console.log("搜索的内容：", value);
    },
    // 下面是获取医生数据
    getDatas(sicknessId) {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/doctor`,
        method: "POST",
        header: {
          "authorization": token
        },
        data: {
          page: 1,
          pageSize: 2,
          sicknessId
        },
        success: (res) => {
          if (res.data.code == 1) {
            this.doctors = res.data.data.items;
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
    //下面是处理重新选择疾病分类
    goToBack() {
      common_vendor.index.navigateBack();
    },
    // 进入医生详情页
    goDetail(doctorId) {
      common_vendor.index.navigateTo({
        url: `/pages/doctor_detail/doctor_detail?doctorId=${doctorId}`
      });
    }
  }
};
if (!Array) {
  const _easycom_uni_icons2 = common_vendor.resolveComponent("uni-icons");
  _easycom_uni_icons2();
}
const _easycom_uni_icons = () => "../../uni_modules/uni-icons/components/uni-icons/uni-icons.js";
if (!Math) {
  _easycom_uni_icons();
}
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.o((...args) => $options.onInput && $options.onInput(...args)),
    b: common_vendor.o((...args) => $options.onConfirm && $options.onConfirm(...args)),
    c: common_vendor.o((...args) => $options.onSearch && $options.onSearch(...args)),
    d: common_vendor.t($data.sicknessName),
    e: common_vendor.p({
      type: "down"
    }),
    f: common_vendor.o((...args) => $options.goToBack && $options.goToBack(...args)),
    g: common_vendor.p({
      type: "down"
    }),
    h: common_vendor.f($data.doctors, (item, index, i0) => {
      return {
        a: item.avatar,
        b: common_vendor.t(item.name),
        c: common_vendor.t(item.level),
        d: common_vendor.t(item.workplace),
        e: common_vendor.t(item.desc),
        f: item.id,
        g: common_vendor.o(($event) => $options.goDetail(item.id), item.id)
      };
    })
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/chuangyue/front/chuangyue/pages/doctor_list/doctor_list.vue"]]);
wx.createPage(MiniProgramPage);
