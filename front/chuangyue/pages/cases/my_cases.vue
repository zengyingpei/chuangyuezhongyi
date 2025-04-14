<template>
	<view class="container">
		<!-- 页面标题 -->
		<view class="page-header">
			<text class="title">我的病历</text>
		</view>
		
		<!-- 病历为空时显示 -->
		<view class="empty-container" v-if="cases.length === 0">
			<image class="empty-image" src="/static/icons/empty_order.png" mode="aspectFit"></image>
			<text class="empty-text">暂无病历记录</text>
		</view>
		
		<!-- 病历列表 -->
		<view class="case-list" v-else>
			<view class="case-card" v-for="(item, index) in cases" :key="item.id" @click="viewCaseDetail(item)">
				<view class="case-header">
					<view class="patient-info">
						<text class="patient-name">{{item.client.name}}</text>
						<text class="gender-tag" :class="item.client.gender === 0 ? 'male' : 'female'">
							{{item.client.gender === 0 ? '男' : '女'}}
						</text>
						<text class="age">{{item.client.age}}岁</text>
					</view>
					<text class="visit-date">就诊日期: {{item.visitDate}}</text>
				</view>
				
				<view class="divider"></view>
				
				<view class="case-info">
					<view class="info-row">
						<text class="label">主诊医生:</text>
						<text class="value">{{item.doctor.name}} | {{item.doctor.level}}</text>
					</view>
					<view class="info-row">
						<text class="label">诊断结果:</text>
						<text class="value diagnosis">{{item.diagnosis}}</text>
					</view>
				</view>
				
				<view class="case-footer">
					<view class="hospital">{{item.doctor.workplace}}</view>
					<view class="view-detail">
						<text>查看详情</text>
						<uni-icons type="right" size="14" color="#7ca0ec"></uni-icons>
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
				cases: [] // 存储所有病历数据
			};
		},
		onLoad() {
			this.getAllCases();
		},
		onPullDownRefresh() {
			this.getAllCases();
			setTimeout(() => {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			// 获取所有病历数据
			getAllCases() {
				let token = uni.getStorageSync('authorization');
				uni.showLoading({
					title: '加载中...'
				});
				
				uni.request({
					url: `${baseUrl}/api/user/case/all`,
					method: 'GET',
					header: {
						'authorization': token
					},
					success: (res) => {
						uni.hideLoading();
						if (res.data.code === 1) {
							this.cases = res.data.data;
							console.log('所有病历数据:', this.cases);
						} else {
							uni.showToast({
								title: '获取病历失败',
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
			
			// 查看病历详情
			viewCaseDetail(caseItem) {
				// 将病历数据转为字符串传递给详情页
				const caseData = encodeURIComponent(JSON.stringify(caseItem));
				uni.navigateTo({
					url: `/pages/cases/case_detail?caseData=${caseData}`
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
	
	.case-list {
		.case-card {
			background-color: #fff;
			border-radius: 16rpx;
			padding: 30rpx;
			margin-bottom: 30rpx;
			box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.06);
			transition: all 0.3s ease;
			
			&:active {
				transform: scale(0.98);
			}
			
			.case-header {
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin-bottom: 20rpx;
				
				.patient-info {
					display: flex;
					align-items: center;
					
					.patient-name {
						font-size: 34rpx;
						font-weight: bold;
						color: #333;
						margin-right: 16rpx;
					}
					
					.gender-tag {
						padding: 4rpx 16rpx;
						border-radius: 30rpx;
						font-size: 22rpx;
						margin-right: 16rpx;
						
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
					
					.age {
						font-size: 26rpx;
						color: #666;
					}
				}
				
				.visit-date {
					font-size: 26rpx;
					color: #999;
					background-color: #f5f5f5;
					padding: 6rpx 16rpx;
					border-radius: 6rpx;
				}
			}
			
			.divider {
				height: 1px;
				background-color: #f0f0f0;
				margin: 20rpx 0;
			}
			
			.case-info {
				margin-bottom: 20rpx;
				
				.info-row {
					display: flex;
					margin-bottom: 16rpx;
					
					&:last-child {
						margin-bottom: 0;
					}
					
					.label {
						color: #666;
						font-size: 28rpx;
						width: 160rpx;
						flex-shrink: 0;
					}
					
					.value {
						color: #333;
						font-size: 28rpx;
						flex: 1;
						
						&.diagnosis {
							color: #ff7043;
							font-weight: 500;
						}
					}
				}
			}
			
			.case-footer {
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin-top: 20rpx;
				
				.hospital {
					font-size: 26rpx;
					color: #999;
					max-width: 70%;
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
				}
				
				.view-detail {
					display: flex;
					align-items: center;
					color: #7ca0ec;
					font-size: 26rpx;
					
					text {
						margin-right: 6rpx;
					}
				}
			}
		}
	}
</style>
