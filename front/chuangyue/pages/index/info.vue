<template>
  <view class="page">
    <view class="title">{{ ans.title }}</view>
    <view class="content">{{ ans.content }}</view>
  </view>
</template>

<script setup>
import { baseUrl } from '../../common/js/utils.js'
import { onLoad } from '@dcloudio/uni-app'
import { ref } from 'vue'

const ans = ref({}) // 关键改动

onLoad((e) => {
  getData(e.id)
})

const getData = async (id) => {
  console.log('id', id)
  let token = uni.getStorageSync('authorization')
  uni.request({
    url: `${baseUrl}/api/user/consults/detail`,
    method: 'GET',
    header: {
      authorization: token
    },
    data: {
      id: id
    },
    success: (res) => {
      console.log('res', res)
      if (res.data.code == 1) {
        console.log(res.data.data)
        ans.value = res.data.data // 更新响应式数据
      } else {
        uni.showToast({
          duration: 1000,
          icon: 'error',
          title: '数据获取失败'
        })
      }
    }
  })
}
</script>

<style scoped>
.page {
  padding: 30rpx;
  background-color: #f5f7fa;
  min-height: 100vh;
  box-sizing: border-box;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
}

/* 标题样式 */
.title {
  font-size: 40rpx;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 24rpx;
  border-left: 10rpx solid #409eff;
  padding-left: 20rpx;
}

/* 内容样式 */
.content {
  background-color: #ffffff;
  padding: 36rpx;
  border-radius: 20rpx;
  box-shadow: 0 6rpx 18rpx rgba(0, 0, 0, 0.05);
  font-size: 30rpx;
  color: #444;
  line-height: 1.9;
  white-space: pre-wrap; /* 保留 \n 换行 */
  word-break: break-word;
}
</style>

