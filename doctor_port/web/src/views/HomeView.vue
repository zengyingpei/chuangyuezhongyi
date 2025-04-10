<template>
  <div class="box">
    <!-- 首页顶部导航栏 -->
    <div class="header">
      <div class="chat">
        <router-link :class="route_name == 'chat' ? ' select ' : ' no_select'" :to="{ name: 'chat' }" style="text-decoration: none">
          问诊
        </router-link>
      </div>
      <div class="appointment">
        <router-link :class="route_name == 'appointment' ? ' select' : ' no_select'" :to="{ name: 'appointment' }" style="text-decoration: none">
          设置预约时间
        </router-link>
      </div>

      <div class="avatar" @click="goToMy()">
          <div class="photo">
            <img src="http://photo.chaoxing.com/photo_80.jpg" alt="" class="my_avatar">
          </div>
      </div>
    </div>
    <div class="body">
      <!-- 首页主题内容 -->
      <div class="main">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import { useRoute,useRouter } from "vue-router";
import { computed } from "vue";

export default {
  setup: () => {
    const route = useRoute(); //获取当前路由只读对象
    const router = useRouter(); //获取路由只写对象
    let route_name = computed(() => route.name); //通过computed实时计算当前页面路由的字符串

    // 点击头像之后，路由跳转
    const goToMy=()=>{
      router.push({name:'my'})
    }
    
    return {
      route_name,
      goToMy
    };
  },
};
</script>

<style scoped>
.box {
  height: 100vh;
}

.header {
  background-color: #6ea3e5;
  width: 100vw;
  height: 8vh;
  display: flex;
  font-size: 3vh;
  padding-left: 3vw;
}

.chat {
  width: 10vw;
  height: 8vh;
  line-height: 8vh;
}

.appointment {
  width: 20vw;
  height: 8vh;
  line-height: 8vh;
}

.avatar{
  /* background-color: black; */
  margin-left: 60vw;
}

.photo{

}

.my_avatar{
  margin-top: 1vw;
  width:  5vh;
  height: 5vh;
  border-radius: 50%;
}

.select {
  color: white;
}

.no_select {
  color: black;
}

.body {
  width: 100vw;
  display: inline-flex;
  height: 91vh;
}

.main {
  width: 100vw;
}

.router-link-active {
  text-decoration: none;
  color: white;
}
</style>
