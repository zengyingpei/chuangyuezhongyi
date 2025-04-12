"use strict";
const common_vendor = require("../../common/vendor.js");
const common_js_utils = require("../../common/js/utils.js");
const _sfc_main = {
  data() {
    return {
      arr1: null,
      arr2: null,
      list1: [
        { id: 1, name: "失眠" },
        { id: 2, name: "月经不调" },
        { id: 3, name: "痘痘" },
        { id: 4, name: "早些" },
        { id: 5, name: "客户尽快" },
        { id: 6, name: "3为" },
        { id: 7, name: "阿三大王" },
        { id: 8, name: "撒旦撒" }
      ],
      list2: [
        { id: 1, name: "皮炎" },
        { id: 2, name: "荨麻疹" },
        { id: 3, name: "偏头痛" },
        { id: 4, name: "dd" },
        { id: 5, name: "ee" },
        { id: 6, name: "ff" },
        { id: 7, name: "gg" },
        { id: 8, name: "hh" }
      ],
      consults: [
        { id: 1, title: "晨起后的黄金时间别浪费，改掉4个坏习惯，更能健康长寿", from: "每日健康", img_url: "../../static/imgs/women.jpg" },
        { id: 2, title: "“三高”必喝茶饮:降压、降糖还能滋养肝肾", from: "每日健康", img_url: "../../static/imgs/tea.jpg" },
        { id: 3, title: "“饭量决定寿命”，吃得越多“走”得越早，是真的吗?", from: "每日健康", img_url: "../../static/imgs/ta.jpg" },
        { id: 4, title: "“入夏无病三分虚”，做好这些防护措施，让夏天不再难熬", from: "每日健康", img_url: "../../static/imgs/flower.jpg" },
        { id: 5, title: "食物分阴阳，吃对才健康，一招教您辨别食物的阴阳属性", from: "每日健康", img_url: "../../static/imgs/阴阳.jpg" },
        { id: 6, title: "一年四季都“怕冷”，这类人要如何养?", from: "每日健康", img_url: "../../static/imgs/阳虚.jpg" }
      ]
    };
  },
  onLoad(option) {
    this.getDatas();
  },
  onShow() {
    this.getDatas();
  },
  methods: {
    goToFind() {
      common_vendor.index.navigateTo({
        url: "/pages/select_find/select_find"
      });
    },
    getDatas() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/doctor/select_doc`,
        method: "GET",
        header: {
          authorization: token
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log(res.data.data);
            this.arr1 = res.data.data.slice(0, 5);
            this.arr2 = res.data.data.slice(5, 10);
          } else {
            common_vendor.index.showToast({
              duration: 1e3,
              icon: "error",
              title: "数据获取失败"
            });
          }
        }
      });
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.o((...args) => $options.goToFind && $options.goToFind(...args)),
    b: common_vendor.f($data.list1, (item, index, i0) => {
      return {
        a: common_vendor.t($data.list1[index].name),
        b: common_vendor.t($data.list2[index].name),
        c: item.id
      };
    }),
    c: common_vendor.f($data.arr1, (item, index, i0) => {
      return {
        a: $data.arr1[index].avatar,
        b: common_vendor.t($data.arr1[index].name),
        c: common_vendor.t($data.arr1[index].level),
        d: common_vendor.t($data.arr1[index].workplace),
        e: common_vendor.t($data.arr1[index].desc),
        f: $data.arr2[index].avatar,
        g: common_vendor.t($data.arr2[index].name),
        h: common_vendor.t($data.arr2[index].level),
        i: common_vendor.t($data.arr2[index].workplace),
        j: common_vendor.t($data.arr2[index].desc),
        k: item.id
      };
    }),
    d: common_vendor.f($data.consults, (item, index, i0) => {
      return {
        a: common_vendor.t(item.title),
        b: common_vendor.t(item.from),
        c: item.img_url,
        d: item.id
      };
    })
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/毕业设计/projects/front/chuangyue/pages/index/index.vue"]]);
wx.createPage(MiniProgramPage);
