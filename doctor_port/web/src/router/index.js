import { createRouter, createWebHistory } from 'vue-router'

import store from '@/store/index'

const routes = [
  
  {
    path: '/',
    name: 'home',
    component: ()=> import ('../views/HomeView.vue'),
    
    children:[
      {
        path:'/chat',
        name:'chat',
        component: ()=> import ('../views/doctor/ChatView.vue'),
        meta:{
          requestAuth: true
        }
      },
      {
        path:'/appointment',
        name:'appointment',
        component: ()=> import ('../views/doctor/AppointmentView.vue'),
        meta:{
          requestAuth: true
        }
      },
      {
        path:'/reception',
        name:'reception',
        component: ()=> import ('../views/doctor/ReceptionView.vue'),
        meta:{
          requestAuth: true
        }
      },
      {
        path:'/my',
        name:'my',
        component: ()=> import ('../views/my/MyView.vue'),
        meta:{
          requestAuth: true
        },

        children:[
          {
            path:'/my/info',
            name:'myInfo',
            component: ()=> import ('../views/my/AccountManageView.vue'),
            meta:{
              requestAuth: true
            }
          }
        ]
      }
    ],

    meta:{
      requestAuth: true
    }
  },
  {
    path: '/login',
    name: 'login',
    component: ()=> import ('../views/user/LoginView.vue'),
    meta:{
      requestAuth: false
    }
  },
  {
    path: '/404',
    name: '404',
    component: ()=> import ('../views/error/NotFound.vue'),
    meta:{
      requestAuth: false
    }
  },
  {
    path:'/*',
    redirect:'/404'
  }
  
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

//router.beforeEach(()=>{}),每当有路由跳转时，都会先执行这个函数，
//to是想要跳转的路由，from是在跳转之前所在的路由，next是会被改变到某个路由
router.beforeEach((to ,from , next)=>{
  if(to.meta.requestAuth && !store.state.is_login){
    next({name : "login"});  //改到登录的路由
  }else{
    next();   //为空不会改变
  }
})


export default router
