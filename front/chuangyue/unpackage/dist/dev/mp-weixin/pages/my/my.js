"use strict";
const common_vendor = require("../../common/vendor.js");
require("../../store/index.js");
const _sfc_main = {
  computed: {
    username() {
      return this.$store.state.username;
    },
    avatar() {
      return this.$store.state.avatar;
    },
    isLogin() {
      return this.$store.state.is_login;
    }
  },
  data() {
    return {
      section1: [
        { id: 1, img: "../../static/icons/bag.svg", title: "待付款" },
        { id: 2, img: "../../static/icons/clock2.svg", title: "待发货" },
        { id: 3, img: "../../static/icons/car.svg", title: "待收货" },
        { id: 4, img: "../../static/icons/pingjia.svg", title: "待评价" },
        { id: 5, img: "../../static/icons/tuikuang.svg", title: "退款/售后" }
      ],
      section2: [
        { id: 1, img: "../../static/icons/Geotag.svg", title: "收货地址" },
        { id: 2, img: "../../static/icons/capsules.svg", title: "我的处方" },
        { id: 3, img: "../../static/icons/yuyue.svg", title: "我的预约" },
        { id: 4, img: "../../static/icons/messge.svg", title: "我的评价" },
        { id: 5, img: "../../static/icons/hospital.svg", title: "就诊人管理" },
        { id: 6, img: "../../static/icons/bl.svg", title: "我的病例" },
        { id: 7, img: "../../static/icons/bell.svg", title: "就诊提醒" },
        { id: 8, img: "../../static/icons/record.svg", title: "就诊记录" }
      ],
      section3: [
        { id: 1, img: "../../static/icons/sc.svg", title: "我的收藏" },
        { id: 2, img: "../../static/icons/VIP.svg", title: "我的会员" },
        { id: 3, img: "../../static/icons/youhuiyuan.svg", title: "优惠券" },
        { id: 4, img: "../../static/icons/qingdan.svg", title: "常见问题" },
        { id: 5, img: "../../static/icons/person.svg", title: "在线客服" },
        { id: 6, img: "../../static/icons/about.svg", title: "关于" }
      ]
    };
  },
  methods: {
    goToAbout(item_id) {
      if (item_id == 6) {
        common_vendor.index.navigateTo({
          url: "/pages/about/about"
        });
      }
    },
    goToLogin() {
      common_vendor.index.navigateTo({
        url: "/pages/login/login"
      });
    },
    goToUpdateMessage() {
      common_vendor.index.navigateTo({
        url: "/pages/my/update_user_message"
      });
    },
    goToSection2(item_id) {
      if (item_id == 1) {
        common_vendor.index.navigateTo({
          url: "/pages/address/address"
        });
      }
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: $options.isLogin == 1
  }, $options.isLogin == 1 ? {
    b: $options.avatar
  } : {}, {
    c: common_vendor.o((...args) => $options.goToLogin && $options.goToLogin(...args)),
    d: $options.isLogin == 1
  }, $options.isLogin == 1 ? {
    e: common_vendor.t($options.username)
  } : {}, {
    f: common_vendor.o((...args) => $options.goToUpdateMessage && $options.goToUpdateMessage(...args)),
    g: common_vendor.f($data.section1, (item, k0, i0) => {
      return {
        a: item.img,
        b: common_vendor.t(item.title),
        c: item.id
      };
    }),
    h: common_vendor.f($data.section2, (item, k0, i0) => {
      return {
        a: item.img,
        b: common_vendor.t(item.title),
        c: item.id,
        d: common_vendor.o(($event) => $options.goToSection2(item.id), item.id)
      };
    }),
    i: common_vendor.f($data.section3, (item, k0, i0) => {
      return {
        a: item.img,
        b: common_vendor.t(item.title),
        c: item.id,
        d: common_vendor.o(($event) => $options.goToAbout(item.id), item.id)
      };
    })
  });
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-2f1ef635"], ["__file", "D:/chuangyue/front/chuangyue/pages/my/my.vue"]]);
wx.createPage(MiniProgramPage);
