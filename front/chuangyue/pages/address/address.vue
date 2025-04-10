<template>
	<view>
		<view class="btn">
			<view class="btn-sub" @click="onAdd">新增地址</view>
		</view>
		
		<view class="card" v-for="(item,index) in arr" :key="item.id">
			<view class="left">
				<view class="simple">
					{{item.province}}{{item.city}}{{item.district}}
				</view>
				<view class="detail">
					{{item.detail}}
				</view>
				<view class="bottom">
					<view class="name">{{item.receiverName}}</view>
					<view class="phone">{{item.receiverPhone}}</view>
					<view class="default" v-if="item.isDefault==1">默认</view>
				</view>
			</view>
			<view class="right">
				<image class="edit" src="../../static/icons/edit.svg" @click="onUpdate(item.id)"></image>
			</view>
		</view>
	</view>
</template>

<script>
	import {baseUrl} from '../../common/js/utils.js'
	
	export default {
		data() {
			return {
				arr:[]		//存放所有的地址信息
			};
		},
		onLoad(option){
			this.getDatas()
		},
		onShow(){
			this.getDatas()
		},
		methods:{
			getDatas(){
				let token = uni.getStorageSync('authorization')
				uni.request({
					url: `${baseUrl}/api/user/address/list`,
					method:"GET",
					header:{
						'authorization' : token,
					},
					success: (res) => {
						if(res.data.code==1){
							console.log(res.data);
							this.arr = res.data.data
						}
					}
				})
			},
			// 去更新地址信息
			onUpdate(addressId){
				uni.navigateTo({
					url:`/pages/address/update_address?addressId=${addressId}`
				})
			},
			// 新增地址
			onAdd(){
				uni.navigateTo({
					url:'/pages/address/add_address'
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.btn {
		width: 100%;
		margin-bottom: 30rpx;
		.btn-sub {
			width: 670rpx;
			margin: 10rpx auto 0;
			height: 60rpx;
			background: #6ea3e5;
			border-radius: 45rpx;
			line-height: 60rpx;
			text-align: center;
			font-size: 34rpx;
			color: #fff;
		}
	}
	
	
	.card{
		display: flex;
		width: 690rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
		padding-top: 10rpx;
		.left{
			width: 90%;
			box-sizing: border-box;
			padding-left: 10rpx;
			padding-bottom: 10rpx;
			// background-color: rebeccapurple;
			.simple{
				font-size: 30rpx;
			}
			.detail{
				padding-top: 10rpx;
				padding-bottom: 10rpx;
				font-size: 44rpx;
			}
			.bottom{
				font-size: 28rpx;
				display: flex;
				.name{
					margin-right: 20rpx;
				}
				.phone{
					margin-right: 15rpx;	
				}
				.default{
					width: 80rpx;
					text-align: center;
					background-color: #6ea3e5;
					color: #eee;
					border-radius: 20rpx;
				}
			}
		}
		.right{
			width: 10%;
			display: flex;
			align-items: center;
			justify-content: center;
			// background-color: brown;
			.edit{
				
				width: 50rpx;
				height: 50rpx;
			}
		}
	}
</style>
