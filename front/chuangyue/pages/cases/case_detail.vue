<template>
	<view class="container">
		<!-- 页面标题 -->
		<view class="page-header">
			<text class="title">病历详情</text>
		</view>
		
		<!-- 病历详情内容 -->
		<view class="case-detail" v-if="caseData">
			<!-- 患者信息卡片 -->
			<view class="detail-card">
				<view class="card-title">
					<uni-icons type="person" size="18" color="#7ca0ec"></uni-icons>
					<text>患者信息</text>
				</view>
				
				<view class="info-grid">
					<view class="info-item">
						<text class="label">姓名</text>
						<text class="value">{{caseData.client.name}}</text>
					</view>
					<view class="info-item">
						<text class="label">性别</text>
						<text class="value">{{caseData.client.gender === 0 ? '男' : '女'}}</text>
					</view>
					<view class="info-item">
						<text class="label">年龄</text>
						<text class="value">{{caseData.client.age}}岁</text>
					</view>
					<view class="info-item">
						<text class="label">电话</text>
						<text class="value">{{caseData.client.phone}}</text>
					</view>
				</view>
			</view>
			
			<!-- 就诊信息卡片 -->
			<view class="detail-card">
				<view class="card-title">
					<uni-icons type="calendar" size="18" color="#7ca0ec"></uni-icons>
					<text>就诊信息</text>
				</view>
				
				<view class="info-list">
					<view class="info-row">
						<text class="label">就诊日期</text>
						<text class="value">{{caseData.visitDate}}</text>
					</view>
					<view class="info-row">
						<text class="label">主诊医生</text>
						<text class="value">{{caseData.doctor.name}} | {{caseData.doctor.level}}</text>
					</view>
					<view class="info-row">
						<text class="label">就诊医院</text>
						<text class="value">{{caseData.doctor.workplace}}</text>
					</view>
				</view>
			</view>
			
			<!-- 诊断信息卡片 -->
			<view class="detail-card">
				<view class="card-title">
					<uni-icons type="info" size="18" color="#7ca0ec"></uni-icons>
					<text>诊断信息</text>
				</view>
				
				<view class="info-list">
					<view class="info-row">
						<text class="label">症状描述</text>
						<text class="value multi-line">{{caseData.symptoms}}</text>
					</view>
					<view class="info-row">
						<text class="label">诊断结果</text>
						<text class="value diagnosis multi-line">{{caseData.diagnosis}}</text>
					</view>
					<view class="info-row">
						<text class="label">治疗方案</text>
						<text class="value multi-line">{{caseData.treatment}}</text>
					</view>
				</view>
			</view>
			
			<!-- 医生信息卡片 -->
			<view class="detail-card">
				<view class="card-title">
					<uni-icons type="staff" size="18" color="#7ca0ec"></uni-icons>
					<text>医生信息</text>
				</view>
				
				<view class="doctor-info">
					<image class="doctor-avatar" :src="caseData.doctor.avatar" mode="aspectFill"></image>
					<view class="doctor-detail">
						<view class="doctor-name">{{caseData.doctor.name}}</view>
						<view class="doctor-title">{{caseData.doctor.level}}</view>
						<view class="doctor-hospital">{{caseData.doctor.workplace}}</view>
						<view class="doctor-desc">{{caseData.doctor.desc}}</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				caseData: null
			};
		},
		onLoad(option) {
			// 解析传递过来的病历数据
			if (option.caseData) {
				try {
					this.caseData = JSON.parse(decodeURIComponent(option.caseData));
					console.log('病历详情数据:', this.caseData);
				} catch (e) {
					console.error('解析病历数据失败:', e);
					uni.showToast({
						title: '数据解析失败',
						icon: 'none',
						duration: 2000
					});
				}
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
	
	.case-detail {
		.detail-card {
			background-color: #fff;
			border-radius: 16rpx;
			padding: 30rpx;
			margin-bottom: 30rpx;
			box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.06);
			
			.card-title {
				display: flex;
				align-items: center;
				margin-bottom: 24rpx;
				padding-bottom: 16rpx;
				border-bottom: 1px solid #f0f0f0;
				
				text {
					font-size: 32rpx;
					font-weight: bold;
					color: #333;
					margin-left: 10rpx;
				}
			}
			
			.info-grid {
				display: grid;
				grid-template-columns: repeat(2, 1fr);
				grid-gap: 20rpx;
				
				.info-item {
					.label {
						font-size: 28rpx;
						color: #666;
						margin-bottom: 8rpx;
						display: block;
					}
					
					.value {
						font-size: 30rpx;
						color: #333;
						font-weight: 500;
					}
				}
			}
			
			.info-list {
				.info-row {
					margin-bottom: 20rpx;
					
					&:last-child {
						margin-bottom: 0;
					}
					
					.label {
						font-size: 28rpx;
						color: #666;
						margin-bottom: 8rpx;
						display: block;
					}
					
					.value {
						font-size: 30rpx;
						color: #333;
						
						&.multi-line {
							line-height: 1.6;
						}
						
						&.diagnosis {
							color: #ff7043;
							font-weight: 500;
						}
					}
				}
			}
			
			.doctor-info {
				display: flex;
				
				.doctor-avatar {
					width: 120rpx;
					height: 120rpx;
					border-radius: 60rpx;
					margin-right: 24rpx;
					flex-shrink: 0;
				}
				
				.doctor-detail {
					flex: 1;
					
					.doctor-name {
						font-size: 32rpx;
						font-weight: bold;
						color: #333;
						margin-bottom: 8rpx;
					}
					
					.doctor-title {
						font-size: 28rpx;
						color: #7ca0ec;
						margin-bottom: 8rpx;
					}
					
					.doctor-hospital {
						font-size: 26rpx;
						color: #666;
						margin-bottom: 16rpx;
					}
					
					.doctor-desc {
						font-size: 26rpx;
						color: #999;
						line-height: 1.6;
						display: -webkit-box;
						-webkit-box-orient: vertical;
						-webkit-line-clamp: 3;
						overflow: hidden;
					}
				}
			}
		}
	}
</style>
