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
      <div class="reception">
        <router-link :class="route_name == 'reception' ? ' select ' : ' no_select'" :to="{ name: 'reception' }" style="text-decoration: none">
          预约接诊
        </router-link>
      </div>

      <div class="avatar" @click="goToMy()">
          <div class="photo">
            <img :src="avatarUrl" alt="医生头像" class="my_avatar">
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
import { useRoute, useRouter } from "vue-router";
import { computed, ref, onMounted } from "vue";
import $ from "jquery";
import { baseUrl } from "../common/util.js"; // 假设你有一个公共的 baseUrl

export default {
  setup: () => {
    const route = useRoute(); //获取当前路由只读对象
    const router = useRouter(); //获取路由只写对象
    let route_name = computed(() => route.name); //通过computed实时计算当前页面路由的字符串
    const avatarUrl = ref(""); // 用于存储头像URL

    // 获取医生头像
    const fetchAvatar = () => {
      $.ajax({
        url: `${baseUrl}/api/doctor/doctor/avatar`,
        type: "GET",
        headers: {
          authorization: localStorage.getItem("token"),
        },
        success: (res) => {
          if (res.code === 1 && res.data) {
            avatarUrl.value = res.data; // 直接使用返回的字符串作为头像URL
          } else {
            // 如果获取失败，使用默认头像
            
          }
        },
        error: () => {
          // 请求出错时也使用默认头像
          
        }
      });
    };

    // 点击头像之后，路由跳转
    const goToMy = () => {
      router.push({name:'my'})
    }
    
    // 组件挂载时获取头像
    onMounted(() => {
      fetchAvatar();
    });
    
    return {
      route_name,
      goToMy,
      avatarUrl
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
  width: 17vw;
  height: 8vh;
  line-height: 8vh;
}

.reception{
  width: 20vw;
  height: 8vh;
  line-height: 8vh;
}
.avatar{
  /* background-color: black; */
  margin-left: 40vw;
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
