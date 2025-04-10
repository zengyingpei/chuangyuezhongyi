<template>
	<view class="container">
		<!-- <view v-for="(item,index) in arr" :key="index">
			{{item.username}}asds
		</view> -->
	</view>
</template>

<script>
	export default {
		name:"heathy",
		data() {
			return {
				arr:[
					{id:1, username:"aaa",position:"bbb"},
					{id:2, username:"aaa",position:"bbb"},
					{id:3, username:"aaa",position:"bbb"},
					{id:4, username:"aaa",position:"bbb"}
				]
			};
		}
	}
</script>

<style lang="scss">
	.container{
		width: 690rpx;
		margin: 20rpx auto;
		border: 1rpx solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
	}
</style>