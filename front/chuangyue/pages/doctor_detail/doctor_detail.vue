<template>
	<view>
		<!-- 医生信息展示 -->
		<view class="doc_card">
			<view class="doc_left_img">
				<image class="doc_images" :src="doctorDetail.avatar" mode=""></image>
				<view class="attention">
					关注
				</view>
			</view>
			<view class="doc_right_info">
				<view class="doc_info_top">
					<view class="doc_name">{{doctorDetail.name}}</view>
					<view class="doc_level">
						{{doctorDetail.level}} |
						<text v-for="(item, index) in doctorDetail.department" :key="index">{{item}}</text>
					</view>
				</view>
				<view class="doc_info_bom">
					<view class="doc_info_bom_up">{{doctorDetail.workplace}}</view>
					<view class="doc_info_bom_down"></view>
				</view>
				<view class="doc_score">
					病友推荐分 : &nbsp;
					<view class="score">
						{{doctorDetail.score}}
						<uni-icons type="hand-up-filled" color="#7ca0ec"></uni-icons>
					</view>
				</view>
			</view>
			
		</view>
		
		<view class="container">
			<view class="row">
				<text class="title">擅 长</text> ：
				{{doctorDetail.specialization}}
			</view>
			<view class="row">
				<text class="title">介 绍</text> ：
				{{doctorDetail.desc}}
			</view>
		</view>
		
		<view class="query_sick">
			<view class="item">
				<view class="item_left">
					<image src="../../static/icons/query.svg"  mode=""></image>
				</view>
				<view class="item_middle">
					<view class="middle_up">
						图文问诊 &nbsp; 
						<text class="price">￥{{doctorDetail.consultCost}}</text>
					</view>
					<view class="middle_down">
						图文多轮沟通
					</view>
				</view>
				<view class="item_right">
					<view class="right_button" @click="showClientSelector()">
						去问诊
					</view>
				</view>
			</view>
			
			<view class="item">
				<view class="item_left">
					<image src="../../static/icons/gh.svg" mode=""></image>
				</view>
				<view class="item_middle">
					<view class="middle_up">
						预约挂号 &nbsp; 
						<text class="price">￥{{doctorDetail.consultCost}}</text>
					</view>
					<view class="middle_down">
						线上预约，方便快捷
					</view>
				</view>
				<view class="item_right">
					<view class="right_button" @click="goToRegistration()">
						去挂号
					</view>
				</view>
			</view>
		</view>
		
		<!-- 添加就诊人选择弹窗 -->
		<uni-popup ref="clientPopup" type="center">
			<view class="client-popup">
				<view class="popup-title">选择就诊人</view>
				
				<!-- 就诊人为空时显示 -->
				<view class="empty-container" v-if="clients.length === 0">
					<text class="empty-text">暂无就诊人信息</text>
					<view class="empty-action">
						<text @click="navigateToAddClient">去添加就诊人</text>
					</view>
				</view>
				
				<!-- 就诊人列表 -->
				<view class="client-list" v-else>
					<view 
						class="client-item" 
						v-for="(item, index) in clients" 
						:key="item.id"
						:class="{ 'selected': selectedClientId === item.id }"
						@click="selectClient(item.id)"
					>
						<view class="client-info">
							<text class="client-name">{{item.name}}</text>
							<text class="client-gender-age">{{item.gender}} · {{item.age}}岁</text>
						</view>
						<view class="client-select" v-if="selectedClientId === item.id">
							<uni-icons type="checkmarkempty" size="20" color="#7ca0ec"></uni-icons>
						</view>
					</view>
				</view>
				
				<view class="popup-actions">
					<button class="cancel-btn" @click="hideClientSelector">取消</button>
					<button class="confirm-btn" @click="goToQuery" :disabled="!selectedClientId">确认问诊</button>
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
				doctorId:null,		//接收上一个页面传过来的医生id
				doctorDetail : null,	//接收从后端获取的医生所有信息
				clients: [],        // 存储所有就诊人数据
				selectedClientId: null // 选中的就诊人ID
			};
		},
		onLoad(option){
			this.doctorId=option.doctorId;
			this.getDatas(this.doctorId);
		},
		methods:{
			getDatas(doctorId){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/doctor/detail`,
					method:"POST",
					data:{
						id: doctorId
					},
					header:{
						'authorization' : token,
					},
					success: (res) => {
						if(res.data.code == 1){
							this.doctorDetail=res.data.data;
							console.log(res.data.data);
						}else{
							uni.showToast({
								duration:1000,
								title:"数据加载失败",
								icon:'error'
							})
						}
					}
				})
			},
			goToRegistration(){
				uni.navigateTo({
					url:`../registration/registration?docId=${this.doctorId}`
				})
			},
			
			// 显示就诊人选择弹窗
			showClientSelector() {
				// 获取就诊人列表
				this.getClientList();
				// 打开弹窗
				this.$refs.clientPopup.open();
			},
			
			// 隐藏就诊人选择弹窗
			hideClientSelector() {
				this.$refs.clientPopup.close();
				// 重置选中状态
				this.selectedClientId = null;
			},
			
			// 选择就诊人
			selectClient(clientId) {
				this.selectedClientId = clientId;
			},
			
			// 获取就诊人列表
			getClientList() {
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
						} else {
							uni.showToast({
								title: res.data.message || '获取就诊人失败',
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
					}
				});
			},
			
			// 跳转到添加就诊人页面
			navigateToAddClient() {
				this.hideClientSelector();
				uni.navigateTo({
					url: '/pages/client/clients'
				});
			},
			
			// 创建聊天并跳转到聊天页面
			goToQuery() {
				if (!this.selectedClientId) {
					uni.showToast({
						title: '请选择就诊人',
						icon: 'none'
					});
					return;
				}
				
				let token = uni.getStorageSync('authorization');
				uni.showLoading({
					title: '正在创建问诊...'
				});
				
				// 创建一个新的聊天关系
				uni.request({
					url: `${baseUrl}/api/user/chat/new`,
					method: "POST",
					data: {
						doctorId: this.doctorId,
						clientId: this.selectedClientId
					},
					header: {
						'authorization': token,
					},
					success: (res) => {
						uni.hideLoading();
						if (res.data.code == 1) {
							let chatLinkId = res.data.data;
							console.log(chatLinkId);
							// 关闭弹窗
							this.hideClientSelector();
							// 跳转到聊天页面
							uni.navigateTo({
								url: `/pages/message/chat?linkId=${chatLinkId}&doctorId=${this.doctorId}`
							});
						} else {
							uni.showToast({
								duration: 1000,
								title: res.data.message || "创建问诊失败",
								icon: 'error'
							});
						}
					},
					fail: () => {
						uni.hideLoading();
						uni.showToast({
							duration: 1000,
							title: "网络请求失败",
							icon: 'error'
						});
					}
				});
			}
		}
	}
</script>

<style lang="scss">
	.doc_card{
		width: 690rpx;
		// height: 200rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
		display: flex;
		.doc_left_img{
			margin: 20rpx 20rpx;
			width: 200rpx;
			// height: 200rpx;
			.doc_images{
				width: 180rpx;
				height: 180rpx;
				border-radius: 50%;
			}
			.attention{
				margin-left: 35rpx;
				border: 2rpx solid #7ca0ec;
				border-radius: 20rpx;
				height: 50rpx;
				width: 100rpx;
				text-align: center;
				color: #7ca0ec;
			}
		}
		.doc_right_info{
			height: 200rpx;
			.doc_info_top{
				width: 420rpx;
				// height: 100rpx;
				// overflow: hidden; 
				padding-top: 20rpx;
				.doc_name{
					font-weight: bold;
					font-size: 50rpx;
					margin-right: 10rpx;
				}
				.doc_level{
					font-size: 28rpx;
				}
			}
			.doc_info_bom{
				margin-top: 10rpx;
				.doc_info_bom_up{
					font-size: 28rpx;
					font-style: italic;
					margin-bottom: 5rpx;
				}
			}
			.doc_score{
				margin-top: 40rpx;
				display: flex;
				line-height: 44rpx;
				.score{
					color: #7ca0ec;
					font-size: 40rpx;
				}
			}
		}
	}
	.container{
		width: 690rpx;
		// height: 200rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
		padding-bottom: 20rpx;
		.row{
			padding-top:20rpx;
			padding-left: 20rpx;
			.title{
				font-weight: bold;
				border-bottom: 5rpx solid #7ca0ec;
			}
		}
	}
	.query_sick{
		width: 690rpx;
		// height: 200rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
		background-color: #e4ebfd;
		display: flex;
		justify-content: center;
		flex-wrap: wrap;
		padding-top: 20rpx;
		.item{
			display: flex;
			width: 650rpx;
			// height: 100rpx;
			// background-color: green;
			border-radius: 20rpx;
			margin-bottom: 20rpx;
			.item_left{
				image{
					height: 90rpx;
					width: 90rpx;
				}
			}
			.item_middle{
				width: 430rpx;
				padding-top: 3rpx;
				padding-left: 6rpx;
				.middle_up{
					font-size: 40rpx;
					.price{
						color: #f87956;
					}
				}
				.middle_down{
					font-size: 25rpx;
					color: #a9a9a9;
				}
			}
			.item_right{
				padding-top: 20rpx;
				.right_button{
					border: 2rpx solid #7ca0ec;
					border-radius: 40rpx;
					height: 50rpx;
					width: 150rpx;
					text-align: center;
					background-color: #7ca0ec;
					color: white;
				}
			}
		}
	}
	
	.evaluate_title{
		font-size: 44rpx;
		padding: 10rpx 0 0 10rpx;
		font-weight: bold;
	}
	
	.evaluate_body{
		
		.evaluate_item{
			width: 680rpx;
			// height: 200rpx;
			margin: 20rpx auto;
			border: 1px solid #eee;
			border-radius: 20rpx;
			box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
			.item_up{
				display: flex;
				.up_left{
					width: 80rpx;
					padding-top: 5rpx;
					padding-left: 5rpx;
					image{
						border-radius: 50%;
						width: 60rpx;
						height: 60rpx;
					}
				}
				.up_middle{
					width: 400rpx;
					font-size: 38rpx;
				}
				.up_right{
					
				}
			}
			.item_middle{
				.evaluate_content{
					width: 630rpx;
					// height: 200rpx;
					margin: 10rpx auto;
					border: 1px solid #eee;
					border-radius: 20rpx;
					box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
					background-color: #fff9f3;
					padding: 10rpx 0 10rpx 5rpx;
				}
			}
			
			.item_bom{
				padding-right: 10rpx;
				padding-bottom: 5rpx;
				text-align: right;
				font-size: 25rpx;
			}
		}
	}
	.client-popup {
	width: 650rpx;
	background-color: #fff;
	border-radius: 20rpx;
	padding: 30rpx;
	box-shadow: 0 0 20rpx rgba(0, 0, 0, 0.2);
	border: 1rpx solid #e0e0e0;
	
	.popup-title {
		font-size: 36rpx;
		font-weight: bold;
		text-align: center;
		margin-bottom: 30rpx;
		color: #333;
		border-bottom: 1px solid #f0f0f0;
		padding-bottom: 20rpx;
	}
	
	.empty-container {
		padding: 40rpx 0;
		text-align: center;
		background-color: #f9f9f9;
		border-radius: 10rpx;
		margin-bottom: 20rpx;
		
		.empty-text {
			font-size: 30rpx;
			color: #999;
			margin-bottom: 20rpx;
			display: block;
		}
		
		.empty-action {
			text {
				color: #7ca0ec;
				font-size: 30rpx;
				padding: 10rpx 20rpx;
				background-color: #f0f7ff;
				border-radius: 30rpx;
			}
		}
	}
	
	.client-list {
		max-height: 600rpx;
		overflow-y: auto;
		margin-bottom: 20rpx;
		border: 1rpx solid #f0f0f0;
		border-radius: 10rpx;
		
		.client-item {
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 20rpx;
			border-bottom: 1px solid #f0f0f0;
			background-color: #ffffff;
			
			&.selected {
				background-color: #f0f7ff;
				border-left: 4rpx solid #7ca0ec;
			}
			
			.client-info {
				.client-name {
					font-size: 32rpx;
					font-weight: bold;
					color: #333;
					margin-right: 20rpx;
				}
				
				.client-gender-age {
					font-size: 28rpx;
					color: #666;
					background-color: #f5f5f5;
					padding: 4rpx 12rpx;
					border-radius: 20rpx;
				}
			}
			
			.client-select {
				width: 40rpx;
				height: 40rpx;
				background-color: #e6efff;
				border-radius: 50%;
				display: flex;
				align-items: center;
				justify-content: center;
			}
		}
	}
	
	.popup-actions {
		display: flex;
		justify-content: space-between;
		margin-top: 30rpx;
		
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
				border: 1rpx solid #e0e0e0;
			}
			
			&.confirm-btn {
				background-color: #7ca0ec;
				color: #fff;
				
				&:disabled {
					background-color: #cccccc;
				}
			}
		}
	}
}
</style>
