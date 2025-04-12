"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const _sfc_main = {
  data() {
    return {
      arr: [],
      //存储yao
      searchName: ""
      //搜索框的内容
    };
  },
  onLoad(option) {
    this.getDatas();
  },
  methods: {
    getDatas() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/medicine/list1`,
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
    showNoStorage() {
      common_vendor.index.showToast({
        duration: 1e3,
        title: "抱歉，当前无货",
        icon: "error"
      });
    },
    goShoppingCart() {
      common_vendor.index.navigateTo({
        url: "/pages/shopping_cart/shopping_cart"
      });
    },
    addToShoppingCart(medicine_id) {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/shoppingcart/addNew`,
        method: "POST",
        header: {
          "authorization": token
        },
        data: {
          medicineId: medicine_id
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            common_vendor.index.showToast({
              duration: 1e3,
              title: "添加成功",
              icon: "success"
            });
          } else {
            common_vendor.index.showToast({
              duration: 1e3,
              title: "不能再多了，库存不足了",
              icon: "error"
            });
          }
        }
      });
    },
    // 搜索框实时更新
    onInput(e) {
      this.searchName = e.detail.value;
      console.log(this.searchName);
    },
    // 搜索指定内容
    onSearch() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/medicine/list1`,
        method: "GET",
        header: {
          "authorization": token
        },
        data: {
          name: this.searchName
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            this.arr = res.data.data;
          }
        }
      });
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.o(($event) => $options.goShoppingCart()),
    b: common_vendor.o(($event) => $options.goShoppingCart()),
    c: common_vendor.o((...args) => $options.onInput && $options.onInput(...args)),
    d: $data.searchName,
    e: common_vendor.o((...args) => $options.onSearch && $options.onSearch(...args)),
    f: common_vendor.f($data.arr, (item, index, i0) => {
      return common_vendor.e({
        a: item.image,
        b: common_vendor.t(item.name),
        c: common_vendor.t(item.price),
        d: item.number === 0
      }, item.number === 0 ? {
        e: common_vendor.o(($event) => $options.showNoStorage(), item.id)
      } : {
        f: common_vendor.o(($event) => $options.addToShoppingCart(item.id), item.id)
      }, {
        g: item.id
      });
    })
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/毕业设计/projects/front/chuangyue/pages/medicine/medicine.vue"]]);
wx.createPage(MiniProgramPage);
