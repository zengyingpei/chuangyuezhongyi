import { createRouter, createWebHistory } from 'vue-router'

import store from '@/store/index'

const routes = [
  
  {
    path: '/',
    name: 'home',
    component: ()=> import ('../views/HomeView.vue'),
    children:[
      {
        path: '/doctor_manage',
        name: 'doctor_manage',
        component: ()=> import ('../views/doctor/DoctorView.vue'),
      },
      {
        path: '/medicine_manage',
        name: 'medicine_manage',
        component: ()=> import ('../views/medicine/MedicineView.vue'),
      },
    ],
    meta:{
      requestAuth: false
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
  if(to.meta.requestAuth && !store.state.user.is_login){
    next({name : "login"});  //改到登录的路由
  }else{
    next();   //为空不会改变
  }
})


export default router
