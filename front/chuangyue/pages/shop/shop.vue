<template>
	<view class="body">
		<view class="box">
			
			<view class="left">
				<view class="left_box" @click="goToBuyMedicine">
					<view class="icon">
						<image src="../../static/icons/buy_medicine.svg" mode=""></image>
					</view>
					<view class="text">购药</view>
				</view>
			</view>
			<view class="right">
				<view class="right_box" @click="goToHeathy">
					<view class="icon">
						<image src="../../static/icons/ys.svg" mode=""></image>
					</view>
					<view class="text">养生</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			goToBuyMedicine(){
				uni.navigateTo({
					url: '../medicine/medicine'
				})
			},
			goToHeathy(){
				uni.navigateTo({
					url:'../keep_medicine/keep_medicine'
				})
			}
			
		}
	}
</script>

<style lang="scss">
	.body{
		display: flex;
		align-items: center;
		
		.box{
			width: 750rpx;
			// background-color: #daebed;
			display: flex;
			height: 300rpx;
			margin-top: 450rpx;
			.left{
				width: 50%;
				height: 100%;
				.left_box{
					margin-left: 35rpx;
					// border: 2rpx solid #2e2e2e;
					border-radius: 30rpx;
					width: 80%;
					height: 100%;
					// background-color: white;
					display: flex;
					justify-content: center;
					flex-wrap: wrap;
					.icon{
						width:200rpx;
						height: 200rpx;
						image{
							width:200rpx;
							height: 200rpx;
						}
					}
					.text{
						text-align: center;
						width: 100%;
						font-size: 50rpx;
					}
				}
			}
			.right{
				width: 50%;
				height: 100%;
				.right_box{
					margin-left: 35rpx;
					// border: 2rpx solid #2e2e2e;
					border-radius: 30rpx;
					width: 80%;
					height: 100%;
					// background-color: white;
					display: flex;
					justify-content: center;
					flex-wrap: wrap;
					.icon{
						width:200rpx;
						height: 200rpx;
						image{
							width:200rpx;
							height: 200rpx;
						}
					}
					.text{
						text-align: center;
						width: 100%;
						font-size: 50rpx;
					}
				}
			}
		}
	}
	
</style>
