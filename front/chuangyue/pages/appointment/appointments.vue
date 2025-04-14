<template>
	<view class="container">
		<!-- 页面标题 -->
		<view class="page-header">
			<text class="title">我的预约</text>
		</view>
		
		<!-- 预约为空时显示 -->
		<view class="empty-container" v-if="appointments.length === 0">
			<image class="empty-image" src="/static/icons/empty_order.png" mode="aspectFit"></image>
			<text class="empty-text">暂无预约记录</text>
		</view>
		
		<!-- 预约列表 -->
		<view class="appointment-list" v-else>
			<view class="appointment-card" v-for="(item, index) in appointments" :key="index">
				<view class="doctor-info">
					<text class="doctor-name">{{item.doctorName}}</text>
					<text class="status-tag" :class="getStatusClass(item.status)">{{item.status}}</text>
				</view>
				
				<view class="appointment-info">
					<view class="info-item">
						<text class="label">预约日期：</text>
						<text class="value">{{item.date}} {{item.slot}}</text>
					</view>
					<view class="info-item">
						<text class="label">医生电话：</text>
						<text class="value">{{item.doctorPhone}}</text>
					</view>
				</view>
				
				<view class="appointment-footer">
					<button class="btn-contact" @click="callDoctor(item.doctorPhone)">联系医生</button>
					<button class="btn-cancel" v-if="item.status === '待就诊'" @click="cancelAppointment(item)">取消预约</button>
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
				appointments: [] // 存储所有预约数据
			};
		},
		onLoad() {
			this.getAllAppointments();
		},
		onPullDownRefresh() {
			this.getAllAppointments();
			setTimeout(() => {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			// 获取所有预约数据
			getAllAppointments() {
				let token = uni.getStorageSync('authorization');
				uni.showLoading({
					title: '加载中...'
				});
				
				uni.request({
					url: `${baseUrl}/api/user/appointment/all`,
					method: 'GET',
					header: {
						'authorization': token
					},
					success: (res) => {
						uni.hideLoading();
						if (res.data.code === 1) {
							this.appointments = res.data.data;
							console.log('所有预约数据:', this.appointments);
						} else {
							uni.showToast({
								title: '获取预约失败',
								icon: 'none',
								duration: 2000
							});
						}
					},
					fail: (err) => {
						uni.hideLoading();
						uni.showToast({
							title: '网络错误',
							icon: 'none',
							duration: 2000
						});
					}
				});
			},
			
			// 获取状态样式类
			getStatusClass(status) {
				switch(status) {
					case '待就诊':
						return 'status-waiting';
					case '已完成':
						return 'status-completed';
					case '已取消':
						return 'status-cancelled';
					default:
						return '';
				}
			},
			
			// 拨打医生电话
			callDoctor(phone) {
				uni.makePhoneCall({
					phoneNumber: phone,
					success: () => {
						console.log('拨打电话成功');
					},
					fail: (err) => {
						console.log('拨打电话失败', err);
					}
				});
			},
			
			// 取消预约
			cancelAppointment(item) {
				uni.showModal({
					title: '提示',
					content: '确定要取消该预约吗？',
					success: (res) => {
						if (res.confirm) {
							// 这里可以添加取消预约的API调用
							uni.showToast({
								title: '取消预约功能开发中',
								icon: 'none',
								duration: 2000
							});
						}
					}
				});
			}
		}
	}
</script>

<style lang="scss">
	.container {
		padding: 20rpx;
		background-color: #f8f8f8;
		min-height: 100vh;
	}
	
	.page-header {
		padding: 30rpx 0;
		margin-bottom: 30rpx;
		border-bottom: 1px solid #eaeaea;
		
		.title {
			font-size: 38rpx;
			font-weight: bold;
			color: #333;
			position: relative;
			padding-left: 20rpx;
			
			&::before {
				content: '';
				position: absolute;
				left: 0;
				top: 50%;
				transform: translateY(-50%);
				width: 8rpx;
				height: 36rpx;
				background-color: #7ca0ec;
				border-radius: 4rpx;
			}
		}
	}
	
	.empty-container {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding: 150rpx 0;
		
		.empty-image {
			width: 240rpx;
			height: 240rpx;
			margin-bottom: 40rpx;
			opacity: 0.7;
		}
		
		.empty-text {
			font-size: 32rpx;
			color: #999;
			letter-spacing: 2rpx;
		}
	}
	
	.appointment-list {
		.appointment-card {
			background-color: #fff;
			border-radius: 16rpx;
			padding: 36rpx;
			margin-bottom: 30rpx;
			box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.06);
			transition: all 0.3s ease;
			
			&:active {
				transform: scale(0.98);
			}
			
			.doctor-info {
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin-bottom: 30rpx;
				padding-bottom: 20rpx;
				border-bottom: 1px dashed #eaeaea;
				
				.doctor-name {
					font-size: 34rpx;
					font-weight: bold;
					color: #333;
					position: relative;
					padding-left: 20rpx;
					
					&::before {
						content: '';
						position: absolute;
						left: 0;
						top: 50%;
						transform: translateY(-50%);
						width: 6rpx;
						height: 28rpx;
						background-color: #7ca0ec;
						border-radius: 3rpx;
					}
				}
				
				.status-tag {
					padding: 8rpx 24rpx;
					border-radius: 30rpx;
					font-size: 26rpx;
					font-weight: 500;
					box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.1);
					
					&.status-waiting {
						background-color: #e6f7ff;
						color: #1890ff;
						border: 1px solid rgba(24, 144, 255, 0.2);
					}
					
					&.status-completed {
						background-color: #f6ffed;
						color: #52c41a;
						border: 1px solid rgba(82, 196, 26, 0.2);
					}
					
					&.status-cancelled {
						background-color: #fff2f0;
						color: #ff4d4f;
						border: 1px solid rgba(255, 77, 79, 0.2);
					}
				}
			}
			
			.appointment-info {
				margin-bottom: 30rpx;
				background-color: #f9f9f9;
				border-radius: 12rpx;
				padding: 20rpx;
				
				.info-item {
					display: flex;
					margin-bottom: 16rpx;
					
					&:last-child {
						margin-bottom: 0;
					}
					
					.label {
						color: #666;
						font-size: 28rpx;
						width: 180rpx;
						position: relative;
						padding-left: 10rpx;
						
						&::before {
							content: '•';
							position: absolute;
							left: -6rpx;
							color: #7ca0ec;
						}
					}
					
					.value {
						color: #333;
						font-size: 28rpx;
						flex: 1;
						font-weight: 500;
					}
				}
			}
			
			.appointment-footer {
				display: flex;
				justify-content: flex-end;
				
				button {
					margin-left: 20rpx;
					font-size: 28rpx;
					padding: 12rpx 36rpx;
					border-radius: 40rpx;
					box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
					transition: all 0.3s ease;
					
					&:active {
						transform: translateY(2rpx);
						box-shadow: 0 2rpx 6rpx rgba(0, 0, 0, 0.1);
					}
					
					&.btn-contact {
						background: linear-gradient(135deg, #7ca0ec, #5a8ade);
						color: #fff;
						border: none;
					}
					
					&.btn-cancel {
						background-color: #fff;
						color: #ff4d4f;
						border: 1rpx solid #ff4d4f;
						
						&:active {
							background-color: #fff2f0;
						}
					}
				}
			}
		}
	}
</style>
