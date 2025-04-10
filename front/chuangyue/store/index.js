// 页面路径：store/index.js
import { createStore } from 'vuex'

const store = createStore({
	state:{//存放状态
		id:'',
		username:'',
		avatar:'',
		token:'',
		is_login:0
	},
	mutations:{
		updateId(state, id){
			state.id = id;
		},
		updateUsername(state, username){
			state.username= username
		},
		updateAvatar(state,avatar){
			state.avatar = avatar
		},
		updateToken(state,token){
			state.token = token
		},
		updateIsLogin(state,is_login){
			state.is_login = is_login;
		}
		
	}
	
})

export default store