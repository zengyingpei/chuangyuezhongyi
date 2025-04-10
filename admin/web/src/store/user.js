

export default ({
    state: {
        id:"",
        username:"",
        name:"",
        is_login:false,
        token:""
    },
    getters: {
    },
    mutations: {        
       updateUser(state,data){
           state.id = data.id;
           state.username = data.username;
           state.name = data.name;
           state.is_login = data.is_login;
       },
       updateToken(state, token){
            state.token=token;
       },
       logout(state){
            state.id = "";
            state.username = "";
            state.is_login = false;
            state.token = "";
       }    
    },
    actions: {
        
    },
    modules: {
    }
  })
  