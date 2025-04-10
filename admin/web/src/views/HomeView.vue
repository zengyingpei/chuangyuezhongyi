<template>
  <div class="box">
    <div class="header">header</div>
    <div class="body">
      <div class="aside">
        <div class="list-group">
          <router-link :class="route_name=='doctor_manage'?'list-group-item list-group-item-action active':'list-group-item list-group-item-action'  " :to="{name:'doctor_manage'}">
            医生管理
          </router-link>
          <router-link :class="route_name=='medicine_manage'?'list-group-item list-group-item-action active':'list-group-item list-group-item-action'  " :to="{name:'medicine_manage'}">
            药品管理
          </router-link>
        </div>
      </div>
      <div class="main">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import { useRoute } from "vue-router";
import { computed } from "vue";
// import { useStore } from "vuex";
export default {
  setup: () => {
    const route = useRoute(); //获取当前路由对象
    // const store = useStore(); //获取vuex对象

    let route_name = computed(() => route.name); //通过computed实时计算当前页面路由的字符串
    return {
      route_name,
    };
  },
};
</script>

<style scoped>
.box {
  height: 100vh;
}

.header {
  background-color: aqua;
  width: 100vw;
  height: 8vh;
}

.body {
  width: 100vw;
  display: inline-flex;
  height: 92vh;
}

.aside {
  background-color: #343744;
  width: 10vw;
}

.main {
  width: 90vw;
}
</style>
