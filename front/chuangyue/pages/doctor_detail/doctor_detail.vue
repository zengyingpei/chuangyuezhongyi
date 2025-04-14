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
					<view class="right_button" @click="goToQuery()">
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
		
		
	</view>
</template>

<script>
	import {baseUrl} from '../../common/js/utils.js'
	export default {
		data() {
			return {
				doctorId:null,		//接收上一个页面传过来的医生id
				doctorDetail : null	//接收从后端获取的医生所有信息
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
			goToQuery(){
				console.log("问诊");
				let token=uni.getStorageSync('authorization');
				// 跳转之前创建一个新的聊天关系
				uni.request({
					url: `${baseUrl}/api/user/chat/new`,
					method:"POST",
					data:{
						doctorId : this.doctorId
					},
					header:{
						'authorization' : token,
					},
					success: (res) => {
						if(res.data.code == 1){
							let chatLinkId = res.data.data;
							console.log(chatLinkId);
							uni.navigateTo({
								url: `/pages/message/chat?linkId=${chatLinkId}&doctorId=${this.doctorId}`
							})
						}else{
							uni.showToast({
								duration:1000,
								title:"数据加载失败",
								icon:'error'
							})
						}
					}
				})
				
				
				
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
</style>
