import { createStore } from 'vuex'

export default createStore({
  state: {
    id:"",
    name:"",
    photo:"",
    is_login:false,
    token:""
  },
  getters: {
  },
  mutations: {
    updateUser(state, data){
      state.id = data.id;
      state.name = data.name;
      state.photo = data.photo;
      state.is_login = data.is_login;
    },
    updateToken(state, token){
      state.token = token;
    },
    logout(state){
      localStorage.removeItem("token");
      state.id = "";
      state.name = "";
      state.photo = "";
      state.is_login = false;
      state.token = "";
    }
  },
  actions: {
  },
  modules: {
  }
})
