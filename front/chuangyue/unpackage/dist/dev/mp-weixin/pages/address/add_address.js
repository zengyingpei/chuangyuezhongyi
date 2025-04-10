"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const _sfc_main = {
  data() {
    return {
      form_data: {
        // 保存表单数据
        receiverName: "",
        receiverPhone: "",
        province: "",
        city: "",
        district: "",
        detail: "",
        isDefault: 0
      }
    };
  },
  onLoad(option) {
  },
  methods: {
    // 新增
    onSubmit() {
      console.log(this.form_data);
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/address/add`,
        method: "POST",
        header: {
          "authorization": token
        },
        data: {
          receiverName: this.form_data.receiverName,
          receiverPhone: this.form_data.receiverPhone,
          province: this.form_data.province,
          city: this.form_data.city,
          district: this.form_data.district,
          detail: this.form_data.detail,
          isDefault: this.form_data.isDefault
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            common_vendor.index.navigateBack();
          }
        }
      });
    },
    nameChange(e) {
      this.form_data.receiverName = e.detail.value;
    },
    phoneChange(e) {
      this.form_data.receiverPhone = e.detail.value;
    },
    provinceChange(e) {
      this.form_data.province = e.detail.value;
    },
    cityChange(e) {
      this.form_data.city = e.detail.value;
    },
    districtChange(e) {
      this.form_data.district = e.detail.value;
    },
    detailChange(e) {
      this.form_data.detail = e.detail.value;
    },
    defauleChange(e) {
      if (e.detail.value) {
        this.form_data.isDefault = 1;
      } else {
        this.form_data.isDefault = 0;
      }
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: $data.form_data.receiverName,
    b: common_vendor.o((...args) => $options.nameChange && $options.nameChange(...args)),
    c: $data.form_data.receiverPhone,
    d: common_vendor.o((...args) => $options.phoneChange && $options.phoneChange(...args)),
    e: $data.form_data.province,
    f: common_vendor.o((...args) => $options.provinceChange && $options.provinceChange(...args)),
    g: $data.form_data.city,
    h: common_vendor.o((...args) => $options.cityChange && $options.cityChange(...args)),
    i: $data.form_data.district,
    j: common_vendor.o((...args) => $options.districtChange && $options.districtChange(...args)),
    k: $data.form_data.detail,
    l: common_vendor.o((...args) => $options.detailChange && $options.detailChange(...args)),
    m: common_vendor.o((...args) => $options.defauleChange && $options.defauleChange(...args)),
    n: common_vendor.o((...args) => $options.onSubmit && $options.onSubmit(...args))
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-e1e1f767"], ["__file", "D:/chuangyue/front/chuangyue/pages/address/add_address.vue"]]);
wx.createPage(MiniProgramPage);
