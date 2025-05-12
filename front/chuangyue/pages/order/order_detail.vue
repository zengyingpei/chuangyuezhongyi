<template>
	<view class="container">
		<!-- 页面标题 -->
		<view class="page-header">
			<text class="title">订单详情</text>
		</view>
		
		<!-- 订单状态 -->
		<view class="status-card">
			<view class="status-icon" :class="getStatusClass(orderInfo)">
				<text class="iconfont" :class="getStatusIcon(orderInfo)"></text>
			</view>
			<view class="status-info">
				<text class="status-text">{{ getStatusText(orderInfo) }}</text>
				<text class="status-desc">{{ getStatusDesc(orderInfo) }}</text>
			</view>
		</view>
		
		<!-- 订单信息 -->
		<view class="info-card">
			<view class="card-title">订单信息</view>
			<view class="info-item">
				<text class="label">订单编号：</text>
				<text class="value">{{ orderInfo.orderNo }}</text>
			</view>
			<view class="info-item">
				<text class="label">下单时间：</text>
				<text class="value">{{ orderInfo.orderTime }}</text>
			</view>
			<!-- 订单信息部分需要修改支付状态显示 -->
			<view class="info-item">
				<text class="label">支付状态：</text>
				<text class="value" :class="getPayStatusClass(orderInfo)">
					{{ orderInfo.payStatus === 1 ? '已支付' : orderInfo.payStatus === 2 ? '已取消' : '未支付' }}
				</text>
			</view>
			
			<!-- 底部操作按钮需要根据订单状态调整显示 -->
			<view class="footer-actions">
				<button class="btn-action btn-contact" @click="contactService">联系客服</button>
				<button class="btn-action btn-primary" v-if="orderInfo.payStatus === 0" @click="payOrder">立即支付</button>
				<button class="btn-action btn-primary" v-if="orderInfo.payStatus === 1 && orderInfo.deliveryStatus === 1" @click="confirmReceive">确认收货</button>
			</view>
			<view class="info-item">
				<text class="label">配送状态：</text>
				<text class="value" :class="orderInfo.deliveryStatus === 1 ? 'text-success' : 'text-warning'">
					{{ orderInfo.deliveryStatus === 1 ? '已发货' : '未发货' }}
				</text>
			</view>
			<view class="info-item">
				<text class="label">订单金额：</text>
				<text class="value price">¥{{ orderInfo.amount }}</text>
			</view>
		</view>
		
		<!-- 收货地址展示 -->
		<view class="address-card" v-if="addressInfo">
			<view class="card-title">收货信息</view>
			<view class="address-content">
				<view class="address-icon">
					<text class="iconfont icon-location"></text>
				</view>
				<view class="address-info">
					<view class="address-header">
						<text class="name">{{ addressInfo.receiverName }}</text>
						<text class="phone">{{ addressInfo.receiverPhone }}</text>
					</view>
					<view class="address-detail">
						{{ addressInfo.province }} {{ addressInfo.city }} {{ addressInfo.district }} {{ addressInfo.detail }}
					</view>
				</view>
			</view>
		</view>
		
		<!-- 底部操作按钮 -->
		<view class="footer-actions">
			<button class="btn-action btn-contact" @click="contactService">联系客服</button>
			<button class="btn-action btn-primary" v-if="orderInfo.payStatus === 0" @click="payOrder">立即支付</button>
			<button class="btn-action btn-primary"  >取消订单</button>
			<button class="btn-action btn-primary" v-if="orderInfo.payStatus === 1 && orderInfo.deliveryStatus === 1" @click="confirmReceive">确认收货</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				orderInfo: {
					id: 0,
					orderNo: '',
					userId: 0,
					addressId: 0,
					orderTime: '',
					payStatus: 0,
					deliveryStatus: 0,
					amount: 0
				},
				addressInfo: null,
				orderItems: []
			};
		},
		onLoad(options) {
			// 从页面参数中获取订单数据
			if (options.orderInfo) {
				try {
					this.orderInfo = JSON.parse(decodeURIComponent(options.orderInfo));
					console.log('接收到的订单信息:', this.orderInfo);
				} catch (e) {
					console.error('解析订单信息失败:', e);
				}
			}
			
			// 从页面参数中获取地址数据
			if (options.addressInfo) {
				try {
					this.addressInfo = JSON.parse(decodeURIComponent(options.addressInfo));
					console.log('接收到的地址信息:', this.addressInfo);
				} catch (e) {
					console.error('解析地址信息失败:', e);
				}
			}
			
			// 从页面参数中获取订单商品数据
			if (options.orderItems) {
				try {
					this.orderItems = JSON.parse(decodeURIComponent(options.orderItems));
				} catch (e) {
					console.error('解析订单商品信息失败:', e);
				}
			}
		},
		methods: {
			// 获取订单状态文本
			getStatusText(order) {
				if (!order) return '';
				
				if (order.payStatus === 2) {
					return '已取消';
				} else if (order.payStatus === 0) {
					return '待付款';
				} else if (order.deliveryStatus === 0) {
					return '待发货';
				} else {
					return '待收货';
				}
			},
			
			// 获取订单状态描述
			getStatusDesc(order) {
				if (!order) return '';
				
				if (order.payStatus === 2) {
					return '订单超时未支付已自动取消';
				} else if (order.payStatus === 0) {
					return '请尽快完成支付';
				} else if (order.deliveryStatus === 0) {
					return '商家正在处理您的订单';
				} else {
					return '商品已发出，请注意查收';
				}
			},
			
			// 获取状态图标
			getStatusIcon(order) {
				if (!order) return '';
				
				if (order.payStatus === 2) {
					return 'icon-close-circle'; // 使用关闭图标表示取消
				} else if (order.payStatus === 0) {
					return 'icon-wallet';
				} else if (order.deliveryStatus === 0) {
					return 'icon-package';
				} else {
					return 'icon-truck';
				}
			},
			
			// 获取状态样式类
			getStatusClass(order) {
				if (!order) return '';
				
				if (order.payStatus === 2) {
					return 'status-canceled';
				} else if (order.payStatus === 0) {
					return 'status-nopay';
				} else if (order.deliveryStatus === 0) {
					return 'status-nodelivery';
				} else {
					return 'status-noreceive';
				}
			},
			
			// 新增：获取支付状态样式类
			getPayStatusClass(order) {
				if (!order) return '';
				
				if (order.payStatus === 1) {
					return 'text-success';
				} else if (order.payStatus === 2) {
					return 'text-canceled';
				} else {
					return 'text-warning';
				}
			},
			
			// 联系客服
			contactService() {
				uni.showToast({
					title: '正在连接客服...',
					icon: 'none',
					duration: 2000
				});
			},
			
			// 支付订单
			payOrder() {
				uni.showToast({
					title: '正在跳转支付...',
					icon: 'none',
					duration: 2000
				});
				// 这里实现支付逻辑
			},
			
			// 确认收货
			confirmReceive() {
				uni.showModal({
					title: '确认收货',
					content: '确认已收到商品吗？',
					success: (res) => {
						if (res.confirm) {
							// 这里实现确认收货的逻辑
							uni.showToast({
								title: '确认收货成功',
								icon: 'success',
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
		background-color: #f5f5f5;
		min-height: 100vh;
	}
	
	.page-header {
		padding: 20rpx 0;
		margin-bottom: 20rpx;
		
		.title {
			font-size: 36rpx;
			font-weight: bold;
			color: #333;
		}
	}
	
	.status-card, .info-card, .address-card, .goods-card, .amount-card {
		background-color: #fff;
		border-radius: 12rpx;
		margin-bottom: 20rpx;
		padding: 30rpx;
		box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
	}
	
	.card-title {
		font-size: 32rpx;
		font-weight: bold;
		color: #333;
		margin-bottom: 20rpx;
		border-bottom: 1px solid #f0f0f0;
		padding-bottom: 15rpx;
	}
	
	.status-card {
		display: flex;
		align-items: center;
		
		.status-icon {
			width: 80rpx;
			height: 80rpx;
			border-radius: 50%;
			display: flex;
			align-items: center;
			justify-content: center;
			margin-right: 20rpx;
			
			&.status-nopay {
				background-color: #ff7043;
			}
			
			&.status-nodelivery {
				background-color: #7ca0ec;
			}
			
			&.status-noreceive {
				background-color: #4caf50;
			}
			
			&.status-canceled {
				background-color: #999999; // 灰色背景表示取消状态
			}
			
			.iconfont {
				color: #fff;
				font-size: 40rpx;
			}
		}
		
		.status-info {
			flex: 1;
			
			.status-text {
				font-size: 32rpx;
				font-weight: bold;
				color: #333;
				display: block;
			}
			
			.status-desc {
				font-size: 26rpx;
				color: #999;
				margin-top: 10rpx;
				display: block;
			}
		}
	}
	
	.info-item {
		display: flex;
		margin-bottom: 15rpx;
		
		.label {
			width: 180rpx;
			font-size: 28rpx;
			color: #666;
		}
		
		.value {
			flex: 1;
			font-size: 28rpx;
			color: #333;
			
			&.text-success {
				color: #4caf50;
			}
			
			&.text-warning {
				color: #ff7043;
			}
			
			&.text-canceled {
				color: #999999; // 灰色文本表示取消状态
			}
			
			&.price {
				color: #ff7043;
				font-weight: bold;
			}
		}
	}
	
	.address-card {
		.address-content {
			display: flex;
			
			.address-icon {
				margin-right: 20rpx;
				
				.iconfont {
					font-size: 40rpx;
					color: #7ca0ec;
				}
			}
			
			.address-info {
				flex: 1;
				
				.address-header {
					margin-bottom: 10rpx;
					
					.name {
						font-size: 30rpx;
						font-weight: bold;
						color: #333;
						margin-right: 20rpx;
					}
					
					.phone {
						font-size: 28rpx;
						color: #666;
					}
				}
				
				.address-detail {
					font-size: 28rpx;
					color: #666;
					line-height: 1.5;
				}
			}
		}
	}
	
	.goods-card {
		.goods-list {
			.goods-item {
				display: flex;
				padding: 20rpx 0;
				border-bottom: 1px solid #f0f0f0;
				
				&:last-child {
					border-bottom: none;
				}
				
				.goods-image {
					width: 160rpx;
					height: 160rpx;
					border-radius: 8rpx;
					margin-right: 20rpx;
					background-color: #f5f5f5;
				}
				
				.goods-info {
					flex: 1;
					display: flex;
					flex-direction: column;
					justify-content: space-between;
					
					.goods-name {
						font-size: 28rpx;
						color: #333;
						margin-bottom: 10rpx;
					}
					
					.goods-spec {
						font-size: 24rpx;
						color: #999;
						margin-bottom: 10rpx;
					}
					
					.goods-price-qty {
						display: flex;
						justify-content: space-between;
						align-items: center;
						
						.goods-price {
							font-size: 30rpx;
							color: #ff7043;
							font-weight: bold;
						}
						
						.goods-qty {
							font-size: 26rpx;
							color: #999;
						}
					}
				}
			}
		}
		
		.empty-goods {
			padding: 40rpx 0;
			text-align: center;
			color: #999;
			font-size: 28rpx;
		}
	}
	
	.amount-card {
		.amount-item {
			display: flex;
			justify-content: space-between;
			margin-bottom: 15rpx;
			
			.label {
				font-size: 28rpx;
				color: #666;
			}
			
			.value {
				font-size: 28rpx;
				color: #333;
				
				&.price {
					color: #ff7043;
					font-weight: bold;
					font-size: 32rpx;
				}
			}
			
			&.total {
				margin-top: 20rpx;
				padding-top: 20rpx;
				border-top: 1px solid #f0f0f0;
				
				.label {
					font-weight: bold;
					color: #333;
				}
			}
		}
	}
	
	.footer-actions {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		background-color: #fff;
		padding: 20rpx;
		display: flex;
		justify-content: flex-end;
		box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.05);
		
		.btn-action {
			margin-left: 20rpx;
			font-size: 28rpx;
			padding: 15rpx 30rpx;
			border-radius: 40rpx;
			
			&.btn-contact {
				background-color: #fff;
				color: #666;
				border: 1px solid #ddd;
			}
			
			&.btn-primary {
				background-color: #ff7043;
				color: #fff;
			}
		}
	}
</style>