"use strict";
const common_vendor = require("../common/vendor.js");
const store = common_vendor.createStore({
  state: {
    //存放状态
    id: "",
    username: "",
    avatar: "",
    token: "",
    is_login: 0
  },
  mutations: {
    updateId(state, id) {
      state.id = id;
    },
    updateUsername(state, username) {
      state.username = username;
    },
    updateAvatar(state, avatar) {
      state.avatar = avatar;
    },
    updateToken(state, token) {
      state.token = token;
    },
    updateIsLogin(state, is_login) {
      state.is_login = is_login;
    }
  }
});
exports.store = store;
