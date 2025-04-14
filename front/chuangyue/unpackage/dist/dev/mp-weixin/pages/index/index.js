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
        { id: 2, name: "小儿糖尿病" },
        { id: 3, name: "痘痘" },
        { id: 4, name: "前列腺炎" },
        { id: 5, name: "激素失调" },
        { id: 6, name: "癫痫" },
        { id: 7, name: "月经不调" },
        { id: 8, name: "腹泻" }
      ],
      list2: [
        { id: 1, name: "皮炎" },
        { id: 2, name: "荨麻疹" },
        { id: 3, name: "偏头痛" },
        { id: 4, name: "生长迟缓" },
        { id: 5, name: "红斑狼疮" },
        { id: 6, name: "日光性角化病" },
        { id: 7, name: "天疱疮" },
        { id: 8, name: "甲沟炎" }
      ],
      // consults:[
      // 	{id:1, title:"晨起后的黄金时间别浪费，改掉4个坏习惯，更能健康长寿",from:"每日健康",img_url:"../../static/imgs/women.jpg"},
      // 	{id:2, title:"“三高”必喝茶饮:降压、降糖还能滋养肝肾",from:"每日健康",img_url:"../../static/imgs/tea.jpg"},
      // 	{id:3, title:"“饭量决定寿命”，吃得越多“走”得越早，是真的吗?",from:"每日健康",img_url:"../../static/imgs/ta.jpg"},
      // 	{id:4, title:"“入夏无病三分虚”，做好这些防护措施，让夏天不再难熬",from:"每日健康",img_url:"../../static/imgs/flower.jpg"},
      // 	{id:5, title:"食物分阴阳，吃对才健康，一招教您辨别食物的阴阳属性",from:"每日健康",img_url:"../../static/imgs/阴阳.jpg"},
      // 	{id:6, title:"一年四季都“怕冷”，这类人要如何养?",from:"每日健康",img_url:"../../static/imgs/阳虚.jpg"},
      // ]
      consults: []
    };
  },
  onLoad(option) {
    this.getDatas();
    this.getConsults();
  },
  onShow() {
    this.getDatas();
    this.getConsults();
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
    },
    getConsults() {
      let token = common_vendor.index.getStorageSync("authorization");
      common_vendor.index.request({
        url: `${common_js_utils.baseUrl}/api/user/consults/list`,
        method: "GET",
        header: {
          authorization: token
        },
        success: (res) => {
          if (res.data.code == 1) {
            console.log("res", res);
            this.consults = res.data.data;
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
    // 进入医生详情页
    goDetail(doctorId) {
      common_vendor.index.navigateTo({
        url: `/pages/doctor_detail/doctor_detail?doctorId=${doctorId}`
      });
    },
    handleClick(id) {
      console.log("id=", id);
      common_vendor.index.navigateTo({
        url: "/pages/index/info?&id=" + id
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
        f: common_vendor.o(($event) => $options.goDetail($data.arr1[index].id), item.id),
        g: $data.arr2[index].avatar,
        h: common_vendor.t($data.arr2[index].name),
        i: common_vendor.t($data.arr2[index].level),
        j: common_vendor.t($data.arr2[index].workplace),
        k: common_vendor.t($data.arr2[index].desc),
        l: common_vendor.o(($event) => $options.goDetail($data.arr2[index].id), item.id),
        m: item.id
      };
    }),
    d: common_vendor.f($data.consults, (item, index, i0) => {
      return {
        a: common_vendor.t(item.title),
        b: common_vendor.t(item.from),
        c: common_vendor.o(($event) => $options.handleClick(item.id), item.id),
        d: item.image,
        e: item.id
      };
    })
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/毕业设计/projects/front/chuangyue/pages/index/index.vue"]]);
wx.createPage(MiniProgramPage);
