"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const _sfc_main = {
  data() {
    return {
      arr: [],
      assist: [],
      //辅助数组，初始化长度为this.arr.length的 0 数组。表示没有被选中，1表示被选中
      addressList: [],
      // 存储收货地址列表
      selectedAddressId: null
      // 选中的地址ID
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
    },
    // 获取选中商品数量
    getSelectedCount() {
      let count = 0;
      for (let i = 0; i < this.assist.length; i++) {
        if (this.assist[i] === 1) {
          count++;
        }
      }
      return count;
    },
    // 计算选中商品总价
    getTotalPrice() {
      let total = 0;
      for (let i = 0; i < this.assist.length; i++) {
        if (this.assist[i] === 1) {
          total += this.arr[i].price * this.arr[i].number;
        }
      }
      return total.toFixed(2);
    },
    // 显示地址选择器
    showAddressSelector() {
      if (this.getSelectedCount() === 0) {
        common_vendor.index.showToast({
          title: "请先选择商品",
          icon: "none",
          duration: 2e3
        });
        return;
      }
      this.getAddressList();
      this.$refs.addressPopup.open();
    },
    // 隐藏地址选择器
    hideAddressSelector() {
      this.$refs.addressPopup.close();
    },
    // 获取地址列表
    getAddressList() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.showLoading({
        title: "加载地址中..."
      });
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/address/list`,
        method: "GET",
        header: {
          "authorization": token
        },
        success: (res) => {
          common_vendor.index.hideLoading();
          if (res.data.code === 1) {
            this.addressList = res.data.data;
            console.log("地址列表:", this.addressList);
            const defaultAddress = this.addressList.find((addr) => addr.isDefault === 1);
            if (defaultAddress) {
              this.selectedAddressId = defaultAddress.id;
            } else if (this.addressList.length > 0) {
              this.selectedAddressId = this.addressList[0].id;
            } else {
              this.selectedAddressId = null;
            }
          } else {
            common_vendor.index.showToast({
              title: "获取地址失败",
              icon: "none",
              duration: 2e3
            });
          }
        },
        fail: (err) => {
          common_vendor.index.hideLoading();
          common_vendor.index.showToast({
            title: "网络错误",
            icon: "none",
            duration: 2e3
          });
        }
      });
    },
    // 选择地址
    selectAddress(addressId) {
      this.selectedAddressId = addressId;
    },
    // 跳转到添加地址页面
    goToAddAddress() {
      common_vendor.index.navigateTo({
        url: "/pages/address/add_address",
        events: {
          // 监听添加地址成功事件
          addressAdded: () => {
            this.getAddressList();
          }
        }
      });
    },
    // 提交订单
    submitOrder() {
      if (!this.selectedAddressId) {
        common_vendor.index.showToast({
          title: "请选择收货地址",
          icon: "none",
          duration: 2e3
        });
        return;
      }
      const selectedIds = [];
      for (let i = 0; i < this.assist.length; i++) {
        if (this.assist[i] === 1) {
          selectedIds.push(this.arr[i].id);
        }
      }
      if (selectedIds.length === 0) {
        common_vendor.index.showToast({
          title: "请选择商品",
          icon: "none",
          duration: 2e3
        });
        return;
      }
      this.$refs.loadingPopup.open();
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/order/submit`,
        method: "POST",
        header: {
          "authorization": token,
          "Content-Type": "application/json"
        },
        data: {
          ids: selectedIds,
          addressId: this.selectedAddressId
        },
        success: (res) => {
          this.$refs.loadingPopup.close();
          if (res.data.code === 1) {
            common_vendor.index.showToast({
              title: "订单提交成功",
              icon: "success",
              duration: 2e3
            });
            this.hideAddressSelector();
            this.freshDatas();
            this.assist = new Array(this.arr.length).fill(0);
            setTimeout(() => {
              common_vendor.index.navigateTo({
                url: "/pages/order/orders"
              });
            }, 1500);
          } else {
            common_vendor.index.showToast({
              title: res.data.message || "订单提交失败",
              icon: "none",
              duration: 2e3
            });
          }
        },
        fail: (err) => {
          this.$refs.loadingPopup.close();
          common_vendor.index.showToast({
            title: "网络错误",
            icon: "none",
            duration: 2e3
          });
        }
      });
    }
  }
};
if (!Array) {
  const _easycom_uni_popup2 = common_vendor.resolveComponent("uni-popup");
  _easycom_uni_popup2();
}
const _easycom_uni_popup = () => "../../uni_modules/uni-popup/components/uni-popup/uni-popup.js";
if (!Math) {
  _easycom_uni_popup();
}
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
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
    b: common_vendor.o(($event) => $options.selectAll()),
    c: $options.getSelectedCount() > 0
  }, $options.getSelectedCount() > 0 ? {
    d: common_vendor.t($options.getTotalPrice())
  } : {}, {
    e: common_vendor.t($options.getSelectedCount()),
    f: common_vendor.o((...args) => $options.showAddressSelector && $options.showAddressSelector(...args)),
    g: common_vendor.o((...args) => $options.hideAddressSelector && $options.hideAddressSelector(...args)),
    h: $data.addressList.length === 0
  }, $data.addressList.length === 0 ? {
    i: common_vendor.o((...args) => $options.goToAddAddress && $options.goToAddAddress(...args))
  } : {}, {
    j: common_vendor.f($data.addressList, (address, index, i0) => {
      return common_vendor.e({
        a: common_vendor.t(address.receiverName),
        b: common_vendor.t(address.receiverPhone),
        c: address.isDefault === 1
      }, address.isDefault === 1 ? {} : {}, {
        d: common_vendor.t(address.province),
        e: common_vendor.t(address.city),
        f: common_vendor.t(address.district),
        g: common_vendor.t(address.detail),
        h: $data.selectedAddressId === address.id ? "../../static/icons/select.svg" : "../../static/icons/no_select.svg",
        i: address.id,
        j: $data.selectedAddressId === address.id ? 1 : "",
        k: common_vendor.o(($event) => $options.selectAddress(address.id), address.id)
      });
    }),
    k: common_vendor.o((...args) => $options.hideAddressSelector && $options.hideAddressSelector(...args)),
    l: common_vendor.o((...args) => $options.submitOrder && $options.submitOrder(...args)),
    m: !$data.selectedAddressId,
    n: common_vendor.sr("addressPopup", "7cc1684a-0"),
    o: common_vendor.p({
      type: "center"
    }),
    p: common_vendor.sr("loadingPopup", "7cc1684a-1"),
    q: common_vendor.p({
      type: "center",
      ["mask-click"]: false
    })
  });
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-7cc1684a"], ["__file", "D:/毕业设计/projects/front/chuangyue/pages/shopping_cart/shopping_cart.vue"]]);
wx.createPage(MiniProgramPage);
