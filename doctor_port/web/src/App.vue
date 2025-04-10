<template>
  <div>
    <router-view> </router-view>
  </div>
</template>

<script>
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap";
// import $ from 'jquery';

export default {
  // 用到的全部自己写的组件都要放进来
  components: {},
  setup: () => {},
};
</script>

<style>
/* 设置html的body部分的背景，且100%填充 */
/* body{
    background-image: url('@/assets/images/hh.jpg');
    background-size: cover;
  } */

.container {
  margin-left: 0 !important;
  margin-right: 0 !important;
}
</style>
