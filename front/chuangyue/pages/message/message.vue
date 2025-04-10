<template>
	<view class="body">
		<!-- <view class="no_message">
			<view class="no_message_img"><image src="../../static/icons/no_message.svg"></image></view>
			<view class="no_message_info">暂无新消息</view>
		</view> -->
		
		<view class="box" v-for="(item, index) in lists" :key="item.id" @click="toChat(item.id, item.doctorId)">
			<view class="photo">
				<image src="../../static/logo.png" class="img"></image>
			</view>
			<view class="name">
				{{item.doctorName}}
			</view>
			<view class="time">
				{{item.createTime}}
			</view>
		</view>
	</view>
</template>

<script>
	import {baseUrl} from '../../common/js/utils.js'
	
	export default {
		data() {
			return {
				lists:[]
			}
		},
		onLoad(option){
			this.getList();
		},
		methods: {
			// 获取列表
			getList(){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/chat/list`,
					method:'GET',
					header:{
						authorization : token
					},
					success: (res) => {
						if(res.data.code==1){
							console.log(res.data);
							this.lists = res.data.data;
						}else{
							uni.showToast({
								duration:1000,
								icon:'error',
								title:"数据获取失败"
							})
						}
					}
				})
			},
			
			// chat
			toChat(linkId, doctorId){
				uni.navigateTo({
					url:`/pages/message/chat?linkId=${linkId}&doctorId=${doctorId}`
				})
			}
		}
	}
</script>

<style lang="scss">
	.body{
		height: 100%;
		display: flex;
		justify-content: center; /* 水平居中 */
		align-items: center; /* 垂直居中 */
		flex-direction: column;
		.no_message{
			margin-top: 500rpx;
			width: 200rpx;
			height: 200rpx;
			display: flex;
			justify-content: center;
			flex-wrap: wrap;
			.no_message_img{
				width: 100rpx;
				height: 100rpx;
				image{
					width: 100rpx;
					height: 100rpx;
				}
			}
			.no_message_info{
				
			}
		}
	}
	
	.box{
		width: 690rpx;
		height: 120rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
		
		display: flex;
		.photo{
			width: 130rpx;
			padding-left: 20rpx;
			padding-top: 10rpx;
			.img{
				width: 100rpx;
				height: 100rpx;
				border-radius: 50rpx;
			}
		}
		.name{
			font-size: 40rpx;
			width: max-content;
			line-height: 110rpx;
			box-sizing: border-box;
		}
		.time{
			font-size: 25rpx;
			margin-left: 170rpx;
			line-height: 110rpx;
			box-sizing: border-box;
		}
		
	}
</style>
