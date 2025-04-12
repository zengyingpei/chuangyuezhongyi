"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const _sfc_main = {
  data() {
    return {
      sorts: [],
      // 存储分类
      arr: [],
      // 存储养生品信息
      searchName: "",
      // 存储搜索框内容
      nowSelectedSort: -1
      //开始时展示的是id为-1的分类，表示 “全部”
    };
  },
  onLoad(option) {
    this.getDatas();
  },
  methods: {
    // 初始化数据
    getDatas() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/medicine/list2`,
        method: "GET",
        header: {
          "authorization": token
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            this.sorts = res.data.data.categories;
            this.sorts.unshift({
              id: -1,
              name: "全部"
            });
            this.arr = res.data.data.medicines;
          }
        },
        fail: (err) => {
          common_vendor.index.showToast({
            duration: 1e3,
            title: "数据获取失败",
            icon: "error"
          });
        }
      });
    },
    // 进入购物车
    goShoppingCart() {
      common_vendor.index.navigateTo({
        url: "/pages/shopping_cart/shopping_cart"
      });
    },
    // 添加购物车
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
          }
        }
      });
    },
    //展示无货
    showNoStorage() {
      common_vendor.index.showToast({
        duration: 1e3,
        title: "抱歉，当前无货",
        icon: "error"
      });
    },
    // 根据分类筛选
    onSelect(categoryId) {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/medicine/list2`,
        method: "GET",
        header: {
          "authorization": token
        },
        data: {
          categoryId
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            this.arr = res.data.data.medicines;
          }
        },
        fail: (err) => {
          common_vendor.index.showToast({
            duration: 1e3,
            title: "数据获取失败",
            icon: "error"
          });
        }
      });
      this.updateSelected(categoryId);
    },
    // 搜索框实时更新事件
    onInput(e) {
      this.searchName = e.detail.value;
      console.log(this.searchName);
    },
    // 根据搜索内容，筛选养生品
    onSearch() {
      console.log(this.searchName);
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/medicine/list2`,
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
            this.arr = res.data.data.medicines;
          }
        },
        fail: (err) => {
          common_vendor.index.showToast({
            duration: 1e3,
            title: "数据获取失败",
            icon: "error"
          });
        }
      });
    },
    updateSelected(idx) {
      this.nowSelectedSort = idx;
    },
    isSelected(idx) {
      return this.nowSelectedSort == idx;
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
    f: common_vendor.f($data.sorts, (item, index, i0) => {
      return {
        a: common_vendor.t(item.name),
        b: item.id,
        c: common_vendor.o(($event) => $options.onSelect(item.id), item.id),
        d: $options.isSelected(item.id) ? "#7ca0ec" : "black"
      };
    }),
    g: common_vendor.f($data.arr, (item, index, i0) => {
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
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/毕业设计/projects/front/chuangyue/pages/keep_medicine/keep_medicine.vue"]]);
wx.createPage(MiniProgramPage);
