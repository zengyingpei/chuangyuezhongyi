"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const _sfc_main = {
  data() {
    return {
      arr: [],
      assist: []
      //辅助数组，初始化长度为this.arr.length的 0 数组。表示没有被选中，1表示被选中
    };
  },
  onLoad(option) {
    this.getDatas();
  },
  methods: {
    // 初始化辅助数组
    initAssist() {
      this.assist = new Array(this.arr.length).fill(0);
    },
    // 初始化购物车信息
    getDatas() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/shoppingcart/list`,
        method: "GET",
        header: {
          "authorization": token
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            this.arr = res.data.data;
            this.assist = new Array(this.arr.length).fill(0);
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
    // 刷新购物车信息
    freshDatas() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/shoppingcart/list`,
        method: "GET",
        header: {
          "authorization": token
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            this.arr = res.data.data;
          }
        },
        fail: (err) => {
          common_vendor.index.showToast({
            duration: 1e3,
            title: "资源刷新失败",
            icon: "error"
          });
        }
      });
    },
    // 购买数 加一
    addOne(shoppingcartId) {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/shoppingcart/addOne`,
        method: "POST",
        header: {
          "authorization": token
        },
        data: {
          id: shoppingcartId,
          num: 1
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            this.freshDatas();
          } else {
            console.log(res.data);
            common_vendor.index.showToast({
              duration: 1e3,
              title: "不能再多了,库存不足了",
              icon: "error"
            });
          }
        }
      });
    },
    // 购买数 减一
    subOne(shoppingcartId) {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/shoppingcart/addOne`,
        method: "POST",
        header: {
          "authorization": token
        },
        data: {
          id: shoppingcartId,
          num: -1
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            this.freshDatas();
          }
        }
      });
    },
    // 从购物车删除
    toDelete(id) {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/shoppingcart/delete`,
        method: "POST",
        header: {
          "authorization": token
        },
        data: {
          id
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data);
            this.freshDatas();
          }
        }
      });
    },
    // 修改选中状态
    onUpdate(index) {
      if (this.assist[index] === 0) {
        this.assist[index] = 1;
      } else {
        this.assist[index] = 0;
      }
    },
    // 全选
    selectAll() {
      for (let i = 0; i < this.assist.length; i++) {
        this.assist[i] = 1;
      }
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.f($data.arr, (item, index, i0) => {
      return common_vendor.e({
        a: common_vendor.o(($event) => $options.toDelete(item.id), item.id),
        b: $data.assist[index] === 0
      }, $data.assist[index] === 0 ? {
        c: common_vendor.o(($event) => $options.onUpdate(index), item.id)
      } : {
        d: common_vendor.o(($event) => $options.onUpdate(index), item.id)
      }, {
        e: item.image,
        f: common_vendor.t(item.name),
        g: common_vendor.t(item.price),
        h: common_vendor.o(($event) => $options.subOne(item.id), item.id),
        i: common_vendor.t(item.number),
        j: common_vendor.o(($event) => $options.addOne(item.id), item.id),
        k: item.id
      });
    }),
    b: common_vendor.o(($event) => $options.selectAll())
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-7cc1684a"], ["__file", "D:/chuangyue/front/chuangyue/pages/shopping_cart/shopping_cart.vue"]]);
wx.createPage(MiniProgramPage);
