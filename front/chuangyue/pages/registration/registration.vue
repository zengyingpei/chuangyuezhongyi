<template>
	<view>
		<view class="row" v-for="(item, index) in futureDays" :key="item.id">
			<view class="left">
				<text class="date">{{item.date}}</text> &nbsp;
				 <text class="time">{{item.slot}}</text>
				 <text class="available" style="margin-left: 100rpx; font-size: 30rpx;color: #ff7043;">剩余 {{item.available}}</text>
			</view>
			<view class="right">
				<view class="button" @click="goSubmit(item.id)">
					预约
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {baseUrl} from '../../common/js/utils.js'
	export default {
		data() {
			return {
				docId:'',		//接收上一个页面传过来的医生的id 
				futureDays:[]	// 存储所有可以预约的信息
			};
		},
		onLoad(option){
			this.docId= option.docId;
			console.log("传过来的医生id ",option.docId);
			this.getAppointment(option.docId);
		},
		methods:{
			getAppointment(docId){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/appointment/list`,
					method:"GET",
					header:{
						'authorization' : token,
					},
					data:{
						docId : docId
					},
					success: (res) => {
						// 成功
						if(res.data.code == 1){
							console.log(res.data);
							this.futureDays=res.data.data;
						}
					}
				})
			},
			goSubmit(slotId){
				let token =uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/appointment/add`,
					method:"POST",
					header:{
						'authorization' : token,
					},
					data:{
						slotId : slotId,
						doctorId : this.docId,
						status: 1
					},
					success: (res) => {
						if(res.data.code == 1 ){
							console.log(res.data);
						}
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.row{
		width: 690rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
		display: flex;
		.left{
			line-height: 80rpx;
			height: 80rpx;
			width: 530rpx;
			box-sizing: border-box;
			padding-left: 20rpx;
		}
		.right{
			width: 170rpx;
			.button{
				margin-top: 10rpx;
				background-color: #6bce72;
				height: 60rpx;
				width: 150rpx;
				border-radius: 20rpx;
				border: 2rpx solid #6bce72;
				line-height: 60rpx;
				color: #eee;
				font-size: 35rpx;
				text-align: center;
			}
		}
	}
</style>
