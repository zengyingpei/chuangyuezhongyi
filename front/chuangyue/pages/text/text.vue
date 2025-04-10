<template>
	<view>
		<view class="">
			<button style="color: #999; font-size: 36rpx; border: none"  class="login" open-type="getPhoneNumber" @getphonenumber="getPhoneNumber" withCredentials="true">点击登录</button>
			<input type="nickname" placeholder="请输入昵称"/>
			<button type="primary" @click="send1">获取所有的科室</button>
			<button type="primary" @click="send2">获取对应科室的所属疾病</button>
			<button type="primary" @click="send3">获取能治某个疾病的所有医生</button>
			<view class="">{{username}}</view>
		</view>
	</view>
</template>

<script>
	import {baseUrl} from '../../common/js/utils.js'
	import store from '@/store/index.js'
	export default{
		data(){
			return{
				
			};
		},
		computed:{
			username(){
				return store.state.username
			}
		},
		onLoad(option){
			console.log(baseUrl);
		},
		methods:{
			send1(){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/department`,
					header: {
						authorization : token
					},
					method:'GET',
					success: (res) => {
						console.log(res.data);
					}
				})
			},
			send2(){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: 'http://localhost:8080/api/user/sickness',
					method:'GET',
					header: {
						authorization : token
					},
					data:{
						deptId: 2
					},
					success: (res) => {
						console.log(res.data);
					}
				})
			},
			send3(){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: 'http://localhost:8080/api/user/doctor',
					method:'POST',
					header:{
						'authorization' : token,
					},
					data:{
						page: 1,
						pageSize: 2,
						sicknessId: 2
					},
					success: (res) => {
						console.log(res.data);
					}
				})
			}
		}
	}
	
	// function send1(){
	// 	let token=uni.getStorageSync('authorization');
	// 	uni.request({
	// 		url: 'http://localhost:8080/api/user/department',
	// 		header: {
	// 			authorization : token
	// 		},
	// 		method:'GET',
	// 		success: (res) => {
	// 			console.log(res.data);
	// 		}
	// 	})
	// }
	
	// function send2(){
	// 	let token=uni.getStorageSync('authorization');
	// 	uni.request({
	// 		url: 'http://localhost:8080/api/user/sickness',
	// 		method:'GET',
	// 		header: {
	// 			authorization : token
	// 		},
	// 		data:{
	// 			deptId: 2
	// 		},
	// 		success: (res) => {
	// 			console.log(res.data);
	// 		}
	// 	})
	// }
	
	// function send3(){
	// 	let token=uni.getStorageSync('authorization');
	// 	uni.request({
	// 		url: 'http://localhost:8080/api/user/doctor',
	// 		method:'POST',
	// 		header:{
	// 			'authorization' : token,
	// 		},
	// 		data:{
	// 			page: 1,
	// 			pageSize: 2,
	// 			sicknessId: 2
	// 		},
	// 		success: (res) => {
	// 			console.log(res.data);
	// 		}
	// 	})
	// }
</script>

<style lang="scss">

</style>
