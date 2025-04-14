<template>
	<view class="container">
		<!-- 页面标题 -->
		<view class="page-header">
			<text class="title">就诊人管理</text>
			<button class="add-btn" @click="showAddForm">添加就诊人</button>
		</view>
		
		<!-- 就诊人为空时显示 -->
		<view class="empty-container" v-if="clients.length === 0">
			<image class="empty-image" src="/static/icons/empty_order.png" mode="aspectFit"></image>
			<text class="empty-text">暂无就诊人信息</text>
		</view>
		
		<!-- 就诊人列表 -->
		<view class="client-list" v-else>
			<view class="client-card" v-for="(item, index) in clients" :key="item.id">
				<view class="client-header">
					<view class="client-name">
						<text class="name">{{item.name}}</text>
						<text class="gender-tag" :class="item.gender === '男' ? 'male' : 'female'">{{item.gender}}</text>
					</view>
					<view class="client-actions">
						<button class="delete-btn" @click="deleteClient(item.id)">删除</button>
					</view>
				</view>
				
				<view class="client-info">
					<view class="info-item">
						<text class="label">年龄：</text>
						<text class="value">{{item.age}}岁</text>
					</view>
					<view class="info-item">
						<text class="label">手机号：</text>
						<text class="value">{{item.phone}}</text>
					</view>
					<view class="info-item">
						<text class="label">身份证：</text>
						<text class="value">{{formatIdNumber(item.idNumber)}}</text>
					</view>
				</view>
			</view>
		</view>
		
		<!-- 添加就诊人表单弹窗 -->
		<uni-popup ref="popup" type="center">
			<view class="popup-content">
				<view class="popup-title">添加就诊人</view>
				
				<view class="form-item">
					<text class="form-label">姓名</text>
					<input class="form-input" type="text" v-model="formData.name" placeholder="请输入姓名" />
				</view>
				
				<view class="form-item">
					<text class="form-label">手机号</text>
					<input class="form-input" type="number" v-model="formData.phone" placeholder="请输入手机号" maxlength="11" />
				</view>
				
				<view class="form-item">
					<text class="form-label">年龄</text>
					<input class="form-input" type="number" v-model="formData.age" placeholder="请输入年龄" />
				</view>
				
				<view class="form-item">
					<text class="form-label">性别</text>
					<view class="gender-selector">
						<view class="gender-option" :class="{ active: formData.gender === 0 }" @click="formData.gender = 0">
							男
						</view>
						<view class="gender-option" :class="{ active: formData.gender === 1 }" @click="formData.gender = 1">
							女
						</view>
					</view>
				</view>
				
				<view class="form-item">
					<text class="form-label">身份证号</text>
					<input class="form-input" type="text" v-model="formData.idNumber" placeholder="请输入身份证号" maxlength="18" />
				</view>
				
				<view class="popup-buttons">
					<button class="cancel-btn" @click="hideAddForm">取消</button>
					<button class="confirm-btn" @click="addClient">确认</button>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	import {baseUrl} from '../../common/js/utils.js'
	
	export default {
		data() {
			return {
				clients: [], // 存储所有就诊人数据
				formData: {
					name: '',
					phone: '',
					age: '',
					gender: 0, // 0表示男，1表示女
					idNumber: ''
				}
			};
		},
		onLoad() {
			this.getAllClients();
		},
		onPullDownRefresh() {
			this.getAllClients();
			setTimeout(() => {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			// 获取所有就诊人数据
			getAllClients() {
				let token = uni.getStorageSync('authorization');
				uni.showLoading({
					title: '加载中...'
				});
				
				uni.request({
					url: `${baseUrl}/api/user/client/all`,
					method: 'GET',
					header: {
						'authorization': token
					},
					success: (res) => {
						uni.hideLoading();
						if (res.data.code === 1) {
							this.clients = res.data.data;
							console.log('所有就诊人数据:', this.clients);
						} else {
							uni.showToast({
								title: '获取就诊人失败',
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
			
			// 显示添加就诊人表单
			showAddForm() {
				// 重置表单数据
				this.formData = {
					name: '',
					phone: '',
					age: '',
					gender: 0,
					idNumber: ''
				};
				this.$refs.popup.open();
			},
			
			// 隐藏添加就诊人表单
			hideAddForm() {
				this.$refs.popup.close();
			},
			
			// 添加就诊人
			addClient() {
				// 表单验证
				if (!this.formData.name) {
					uni.showToast({
						title: '请输入姓名',
						icon: 'none'
					});
					return;
				}
				
				if (!this.formData.phone || !/^1\d{10}$/.test(this.formData.phone)) {
					uni.showToast({
						title: '请输入正确的手机号',
						icon: 'none'
					});
					return;
				}
				
				if (!this.formData.age || this.formData.age <= 0) {
					uni.showToast({
						title: '请输入正确的年龄',
						icon: 'none'
					});
					return;
				}
				
				if (!this.formData.idNumber || !/^\d{17}[\dXx]$/.test(this.formData.idNumber)) {
					uni.showToast({
						title: '请输入正确的身份证号',
						icon: 'none'
					});
					return;
				}
				
				let token = uni.getStorageSync('authorization');
				uni.showLoading({
					title: '添加中...'
				});
				
				uni.request({
					url: `${baseUrl}/api/user/client/add`,
					method: 'POST',
					header: {
						'authorization': token,
						'Content-Type': 'application/json'
					},
					data: this.formData,
					success: (res) => {
						uni.hideLoading();
						if (res.data.code === 1) {
							uni.showToast({
								title: '添加成功',
								icon: 'success',
								duration: 2000
							});
							this.hideAddForm();
							this.getAllClients(); // 刷新列表
						} else {
							uni.showToast({
								title: res.data.message || '添加失败',
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
			
			// 删除就诊人
			deleteClient(id) {
				uni.showModal({
					title: '提示',
					content: '确定要删除该就诊人信息吗？',
					success: (res) => {
						if (res.confirm) {
							let token = uni.getStorageSync('authorization');
							uni.showLoading({
								title: '删除中...'
							});
							
							uni.request({
								url: `${baseUrl}/api/user/client/delete`,
								method: 'POST',
								header: {
									'authorization': token
								},
								data: {
									id: id
								},
								success: (res) => {
									uni.hideLoading();
									if (res.data.code === 1) {
										uni.showToast({
											title: '删除成功',
											icon: 'success',
											duration: 2000
										});
										this.getAllClients(); // 刷新列表
									} else {
										uni.showToast({
											title: res.data.message || '删除失败',
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
						}
					}
				});
			},
			
			// 格式化身份证号（中间部分用*代替）
			formatIdNumber(idNumber) {
				if (idNumber && idNumber.length >= 18) {
					return idNumber.substring(0, 6) + '********' + idNumber.substring(14);
				}
				return idNumber;
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
		display: flex;
		justify-content: space-between;
		align-items: center;
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
		
		.add-btn {
			background: linear-gradient(135deg, #7ca0ec, #5a8ade);
			color: #fff;
			font-size: 28rpx;
			padding: 10rpx 30rpx;
			border-radius: 40rpx;
			box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
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
	
	.client-list {
		.client-card {
			background-color: #fff;
			border-radius: 16rpx;
			padding: 36rpx;
			margin-bottom: 30rpx;
			box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.06);
			
			.client-header {
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin-bottom: 30rpx;
				padding-bottom: 20rpx;
				border-bottom: 1px dashed #eaeaea;
				
				.client-name {
					display: flex;
					align-items: center;
					
					.name {
						font-size: 34rpx;
						font-weight: bold;
						color: #333;
						margin-right: 20rpx;
					}
					
					.gender-tag {
						padding: 6rpx 20rpx;
						border-radius: 30rpx;
						font-size: 24rpx;
						
						&.male {
							background-color: #e6f7ff;
							color: #1890ff;
							border: 1px solid rgba(24, 144, 255, 0.2);
						}
						
						&.female {
							background-color: #fff0f6;
							color: #eb2f96;
							border: 1px solid rgba(235, 47, 150, 0.2);
						}
					}
				}
				
				.client-actions {
					.delete-btn {
						background-color: #fff;
						color: #ff4d4f;
						border: 1rpx solid #ff4d4f;
						font-size: 24rpx;
						padding: 6rpx 20rpx;
						border-radius: 30rpx;
						
						&:active {
							background-color: #fff2f0;
						}
					}
				}
			}
			
			.client-info {
				.info-item {
					display: flex;
					margin-bottom: 16rpx;
					
					&:last-child {
						margin-bottom: 0;
					}
					
					.label {
						color: #666;
						font-size: 28rpx;
						width: 140rpx;
					}
					
					.value {
						color: #333;
						font-size: 28rpx;
						flex: 1;
					}
				}
			}
		}
	}
	
	.popup-content {
		width: 650rpx;
		background-color: #fff;
		border-radius: 20rpx;
		padding: 40rpx;
		
		.popup-title {
			font-size: 36rpx;
			font-weight: bold;
			text-align: center;
			margin-bottom: 40rpx;
			color: #333;
		}
		
		.form-item {
			margin-bottom: 30rpx;
			
			.form-label {
				display: block;
				font-size: 28rpx;
				color: #666;
				margin-bottom: 10rpx;
			}
			
			.form-input {
				width: 100%;
				height: 80rpx;
				border: 1px solid #eaeaea;
				border-radius: 10rpx;
				padding: 0 20rpx;
				font-size: 28rpx;
				box-sizing: border-box;
			}
			
			.gender-selector {
				display: flex;
				
				.gender-option {
					flex: 1;
					height: 80rpx;
					line-height: 80rpx;
					text-align: center;
					border: 1px solid #eaeaea;
					border-radius: 10rpx;
					margin-right: 20rpx;
					font-size: 28rpx;
					color: #666;
					
					&:last-child {
						margin-right: 0;
					}
					
					&.active {
						background-color: #7ca0ec;
						color: #fff;
						border-color: #7ca0ec;
					}
				}
			}
		}
		
		.popup-buttons {
			display: flex;
			justify-content: space-between;
			margin-top: 40rpx;
			
			button {
				width: 45%;
				height: 80rpx;
				line-height: 80rpx;
				text-align: center;
				border-radius: 40rpx;
				font-size: 30rpx;
				
				&.cancel-btn {
					background-color: #f5f5f5;
					color: #666;
				}
				
				&.confirm-btn {
					background: linear-gradient(135deg, #7ca0ec, #5a8ade);
					color: #fff;
				}
			}
		}
	}
</style>
