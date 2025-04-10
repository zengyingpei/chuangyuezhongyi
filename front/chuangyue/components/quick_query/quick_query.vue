<template>
	<view class="quick_section">
		<view class="quick_section_item">
			<view class="quick_box">
				<view class="quick_section_item_left">
					<view class="quick_big">专家问诊</view>
					<view class="quick_small">在线问诊</view>
				</view>
				<view class="quick_section_item_right">
					<image class="quick_images" src="../../static/logo.png" mode=""></image>
				</view>
			</view>
			
		</view>
		<view class="quick_section_item">
			<view class="quick_box">
				<view class="quick_section_item_left">
					<view class="quick_big">预约挂号</view>
					<view class="quick_small">线下就诊</view>
				</view>
				<view class="quick_section_item_right">
					<image class="quick_images" src="../../static/logo.png" mode=""></image>
				</view>
			</view>
			
		</view>
		<view class="quick_section_item">
			<view class="quick_box">
				<view class="quick_section_item_left">
					<view class="quick_big">健康测评</view>
					<view class="quick_small">按方开药</view>
				</view>
				<view class="quick_section_item_right">
					<image class="quick_images" src="../../static/logo.png" mode=""></image>
				</view>
			</view>
			
		</view>
		<view class="quick_section_item">
			<view class="quick_box">
				<view class="quick_section_item_left">
					<view class="quick_big">按方开药</view>
					<view class="quick_small">按方开药</view>
				</view>
				<view class="quick_section_item_right">
					<image class="quick_images" src="../../static/logo.png" mode=""></image>
				</view>
			</view>
			
		</view>
	</view>
</template>

<script>
	export default {
		name:"quick-query",
		data() {
			return {
				
			};
		},
		props:{			//父组件传一个info进来，是一个对象
			info:{
				type: String,
				default: "sadas"
			}
		},
		methods:{
			
		}
	}
</script>

<style lang="scss">
	.quick_section{
		width: 690rpx;
		height: 310rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
		display: flex;
		overflow: hidden;
		flex-wrap: wrap;
		flex-direction: row;
		
		
		.quick_section_item{
			width: 50%;						//控制一行显示多少个元素
			height: 150rpx;
			border-radius: 20rpx;
			
			overflow: hidden;
			text-align: center;
			.quick_box{
				display: flex;
				width: 90%;
				height: 150rpx;
				// background-color: #ff3d0b;
				border-radius: 20rpx;
				margin-left: 16rpx;
				.quick_section_item_left{
					padding-left: 15rpx;
					padding-top: 25rpx;
					.quick_big{
						font-size: 44rpx;
						font-weight: bold;
					}
					.quick_small{
						font-size: 30rpx;
						
					}
					
				}
				.quick_section_item_right{
					padding-top: 20rpx;
					padding-left: 5rpx;
					width: 100rpx;
					height: 100rpx;
					.quick_images{
						width: 100rpx;
						height:	100rpx
					}
				}
			}
			
		}
	}
</style>