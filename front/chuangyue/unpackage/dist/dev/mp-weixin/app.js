"use strict";
Object.defineProperty(exports, Symbol.toStringTag, { value: "Module" });
const common_vendor = require("./common/vendor.js");
const store_index = require("./store/index.js");
if (!Math) {
  "./pages/index/index.js";
  "./pages/index/info.js";
  "./pages/my/my.js";
  "./pages/message/message.js";
  "./pages/shop/shop.js";
  "./pages/medicine/medicine.js";
  "./pages/keep_medicine/keep_medicine.js";
  "./pages/about/about.js";
  "./pages/login/login.js";
  "./pages/text/text.js";
  "./pages/my/update_user_message.js";
  "./pages/questionnaire/questionnaire.js";
  "./pages/select_find/select_find.js";
  "./pages/doctor_list/doctor_list.js";
  "./pages/doctor_detail/doctor_detail.js";
  "./pages/registration/registration.js";
  "./pages/shopping_cart/shopping_cart.js";
  "./pages/address/address.js";
  "./pages/address/update_address.js";
  "./pages/address/add_address.js";
  "./pages/message/chat.js";
  "./pages/order/nodelivery.js";
  "./pages/order/nopay.js";
  "./pages/order/noreceive.js";
  "./pages/order/orders.js";
  "./pages/order/order_detail.js";
  "./pages/appointment/appointments.js";
  "./pages/client/clients.js";
  "./pages/cases/my_cases.js";
  "./pages/cases/case_detail.js";
}
const _sfc_main = {
  onLaunch: function() {
    console.log("App Launch");
  },
  onShow: function() {
    console.log("App Show");
  },
  onHide: function() {
    console.log("App Hide");
  },
  globalData: {}
};
const App = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/毕业设计/projects/front/chuangyue/App.vue"]]);
function createApp() {
  const app = common_vendor.createSSRApp(App);
  app.use(store_index.store);
  return {
    app
  };
}
createApp().app.mount("#app");
exports.createApp = createApp;
