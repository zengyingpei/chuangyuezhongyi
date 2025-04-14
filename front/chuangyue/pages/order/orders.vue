<template>
	<view class="container">
		<!-- 页面标题 -->
		<view class="page-header">
			<text class="title">全部订单</text>
		</view>
		
		<!-- 订单为空时显示 -->
		<view class="empty-container" v-if="orders.length === 0">
			<image class="empty-image" src="/static/icons/empty_order.png" mode="aspectFit"></image>
			<text class="empty-text">暂无订单</text>
		</view>
		
		<!-- 订单列表 -->
		<view class="order-list" v-else>
			<view class="order-card" v-for="(item, index) in orders" :key="item.id">
				<view class="order-header">
					<view class="order-no">
						<text class="label">订单号：</text>
						<text class="value">{{ item.orderNo }}</text>
					</view>
					<view class="order-status">
						<text class="status-tag" :class="getStatusClass(item)">{{ getStatusText(item) }}</text>
					</view>
				</view>
				
				<view class="order-info">
					<view class="info-item">
						<text class="label">下单时间：</text>
						<text class="value">{{ item.orderTime }}</text>
					</view>
					<view class="info-item">
						<text class="label">支付状态：</text>
						<text class="value">{{ item.payStatus === 1 ? '已支付' : '未支付' }}</text>
					</view>
					<view class="info-item">
						<text class="label">配送状态：</text>
						<text class="value">{{ item.deliveryStatus === 1 ? '已发货' : '未发货' }}</text>
					</view>
				</view>
				
				<view class="order-footer">
					<view class="amount">
						<text class="label">订单金额：</text>
						<text class="price">¥{{ item.amount }}</text>
					</view>
					<view class="actions">
						<button class="btn-detail" @click="viewOrderDetail(item)">查看详情</button>
						<button class="btn-contact" @click="contactService(item.id)">联系客服</button>
					</view>
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
				orders: [] // 存储所有订单数据
			};
		},
		onLoad() {
			this.getAllOrders();
		},
		onPullDownRefresh() {
			this.getAllOrders();
			setTimeout(() => {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			// 获取所有订单数据
			getAllOrders() {
				let token = uni.getStorageSync('authorization');
				uni.showLoading({
					title: '加载中...'
				});
				
				uni.request({
					url: `${baseUrl}/api/user/order/all`,
					method: 'GET',
					header: {
						'authorization': token
					},
					success: (res) => {
						uni.hideLoading();
						if (res.data.code === 1) {
							this.orders = res.data.data;
							console.log('所有订单数据:', this.orders);
						} else {
							uni.showToast({
								title: '获取订单失败',
								icon: 'none',
								duration: 2000
							});
						}
					},
					fail: (err) => {
						uni.hideLoading();
						uni.showToast({
							title: '网络请求失败',
							icon: 'none',
							duration: 2000
						});
						console.error('请求失败:', err);
					}
				});
			},
			
			// 获取订单状态文本
			getStatusText(item) {
				if (item.payStatus === 0) {
					return '待付款';
				} else if (item.deliveryStatus === 0) {
					return '待发货';
				} else {
					return '待收货';
				}
			},
			
			// 获取订单状态样式类
			getStatusClass(item) {
				if (item.payStatus === 0) {
					return 'status-nopay';
				} else if (item.deliveryStatus === 0) {
					return 'status-nodelivery';
				} else {
					return 'status-noreceive';
				}
			},
			
			// 查看订单详情
			viewOrderDetail(order) {
				// 将订单对象转为 JSON 字符串并编码
				const orderInfo = encodeURIComponent(JSON.stringify(order));
				
				// 如果有地址信息和订单商品信息，也需要传递
				// 这里假设我们已经有了这些信息，实际使用时可能需要额外获取
				const addressInfo = encodeURIComponent(JSON.stringify({
					receiverName: '收货人姓名',
					receiverPhone: '收货人电话',
					province: '省份',
					city: '城市',
					district: '区县',
					detailAddress: '详细地址'
				}));
				
				const orderItems = encodeURIComponent(JSON.stringify([
					{
						productName: '商品名称',
						productImage: '/static/icons/default_product.png',
						price: order.amount,
						quantity: 1
					}
				]));
				
				uni.navigateTo({
					url: `/pages/order/order_detail?orderInfo=${orderInfo}&addressInfo=${addressInfo}&orderItems=${orderItems}`
				});
			},
			
			// 联系客服
			contactService(orderId) {
				uni.showToast({
					title: '正在连接客服...',
					icon: 'none',
					duration: 2000
				});
				// 这里可以实现联系客服的逻辑
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
	
	.empty-container {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding: 100rpx 0;
		
		.empty-image {
			width: 200rpx;
			height: 200rpx;
			margin-bottom: 30rpx;
		}
		
		.empty-text {
			font-size: 30rpx;
			color: #999;
		}
	}
	
	.order-list {
		.order-card {
			background-color: #fff;
			border-radius: 12rpx;
			margin-bottom: 20rpx;
			padding: 30rpx;
			box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
			
			.order-header {
				display: flex;
				justify-content: space-between;
				align-items: center;
				padding-bottom: 20rpx;
				border-bottom: 1px solid #f0f0f0;
				
				.order-no {
					.label {
						font-size: 28rpx;
						color: #666;
					}
					
					.value {
						font-size: 28rpx;
						color: #333;
					}
				}
				
				.status-tag {
					font-size: 24rpx;
					padding: 6rpx 16rpx;
					border-radius: 20rpx;
					color: #fff;
					
					&.status-nopay {
						background-color: #ff7043;
					}
					
					&.status-nodelivery {
						background-color: #7ca0ec;
					}
					
					&.status-noreceive {
						background-color: #4caf50;
					}
				}
			}
			
			.order-info {
				padding: 20rpx 0;
				
				.info-item {
					display: flex;
					margin-bottom: 10rpx;
					
					.label {
						font-size: 28rpx;
						color: #666;
						width: 160rpx;
					}
					
					.value {
						font-size: 28rpx;
						color: #333;
						flex: 1;
					}
				}
			}
			
			.order-footer {
				display: flex;
				justify-content: space-between;
				align-items: center;
				padding-top: 20rpx;
				border-top: 1px solid #f0f0f0;
				
				.amount {
					.label {
						font-size: 28rpx;
						color: #666;
					}
					
					.price {
						font-size: 32rpx;
						color: #ff7043;
						font-weight: bold;
					}
				}
				
				.actions {
					display: flex;
					
					.btn-detail, .btn-contact {
						font-size: 24rpx;
						padding: 10rpx 20rpx;
						margin-left: 20rpx;
						background-color: #fff;
						border: 1px solid #ddd;
						border-radius: 30rpx;
						color: #666;
					}
					
					.btn-detail {
						border-color: #7ca0ec;
						color: #7ca0ec;
					}
				}
			}
		}
	}
</style>