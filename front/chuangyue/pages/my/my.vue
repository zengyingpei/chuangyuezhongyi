<template>
	<view class="user_layout">
		<view class="user_info">
			<view class="info_up">
				<view class="info_image" @click="goToLogin">
					<image class="images" v-if="isLogin==1" :src="avatar" mode=""></image>
					<image class="images" v-else src="/static/logo.png" mode=""></image>
				</view>
				
				<view v-if="isLogin == 1" class="info_phone">{{username}}</view>
				<view v-else class="info_phone" style="font-size: 38rpx;padding-top: 25rpx;">点击头像去登录</view>
				
				<view class="update_message" @click="goToUpdateMessage">
					修改账号信息
				</view>
			</view>
			<view class="info_down">
				<view class="info_down_content">
					<view class="content_text">VIP 创悦会员</view>
					<view class="open">立即开通</view>
				</view>
			</view>
			
			
		</view>
		
		<view class="section_v1">
			<view class="section_header">
				<text class="title">我的订单</text>
				<text class="all" @click="goToAllOrders">查看全部订单></text>
			</view>
			<view class="section_body">
				<view class="section_body_item" v-for="item in section1" :key="item.id" @click="goToOrder(item.id)">
					<view class="section_body_item_img"><image class="images" :src="item.img"></image></view>
					<view class="section_body_item_title">{{item.title}}</view>
				</view>
			</view>
		</view>
		
		<view class="section_v2">
			<view class="section_header">
				<text class="title">我的医疗</text>
			</view>
			<view class="section_body">
				<view class="section_body_item" v-for="item in section2" :key="item.id" @click="goToSection2(item.id)">
					<view class="section_body_item_img"><image class="images" :src="item.img"></image></view>
					<view class="section_body_item_title">{{item.title}}</view>
				</view>
			</view>
		</view>
		
	
		<view class="section_v2">
			<view class="section_header">
				我的工具
			</view>
			<view class="section_body">
				<view class="section_body_item" v-for="item in section3" :key="item.id" @click="goToAbout(item.id)">
					<view class="section_body_item_img"><image class="images" :src="item.img"></image></view>
					<view class="section_body_item_title">{{item.title}}</view>
				</view>
			</view>
		</view>
		
	</view>
	
</template>

<script >
	import store from '@/store/index.js'
	export default {
		computed:{
			username(){
				return this.$store.state.username
			},
			avatar(){
				return this.$store.state.avatar
			},
			isLogin(){
				return this.$store.state.is_login
			}
		},
		
		data() {
			return {
				section1:[
					{id:1, img:'../../static/icons/bag.svg', title:'待付款'},
					{id:2, img:'../../static/icons/clock2.svg', title:'待发货'},
					{id:3, img:'../../static/icons/car.svg', title:'待收货'},
					{id:4, img:'../../static/icons/pingjia.svg', title:'待评价'},
					{id:5, img:'../../static/icons/tuikuang.svg', title:'退款/售后'}
					
				],
				section2:[
					{id:1, img:'../../static/icons/Geotag.svg', title:'收货地址'},
					{id:2, img:'../../static/icons/capsules.svg', title:'我的处方'},
					{id:3, img:'../../static/icons/yuyue.svg', title:'我的预约'},
					{id:4, img:'../../static/icons/messge.svg', title:'我的评价'},
					{id:5, img:'../../static/icons/hospital.svg', title:'就诊人管理'},
					{id:6, img:'../../static/icons/bl.svg', title:'我的病例'},
					{id:7, img:'../../static/icons/bell.svg', title:'就诊提醒'},
					{id:8, img:'../../static/icons/record.svg', title:'就诊记录'}
				],
				section3:[
					{id:1, img:'../../static/icons/sc.svg', title:'我的收藏'},
					{id:2, img:'../../static/icons/VIP.svg', title:'我的会员'},
					{id:3, img:'../../static/icons/youhuiyuan.svg', title:'优惠券'},
					{id:4, img:'../../static/icons/qingdan.svg', title:'常见问题'},
					{id:5, img:'../../static/icons/person.svg', title:'在线客服'},
					{id:6, img:'../../static/icons/about.svg', title:'关于'}
				]
			}
		},
		methods: {
			goToAbout(item_id){
				if(item_id == 6){
					uni.navigateTo({
						url:'/pages/about/about'
					})
				}
				
			},
			goToLogin(){
				uni.navigateTo({
					url: '/pages/login/login'
				})
			},
			goToUpdateMessage(){
				uni.navigateTo({
					url:'/pages/my/update_user_message'
				})
			},
			goToSection2(item_id){
				if(item_id == 1){
					uni.navigateTo({
						url: '/pages/address/address'
					})
				}
			},
			// 添加跳转到全部订单页面的方法
			goToAllOrders(){
				uni.navigateTo({
					url: '/pages/order/orders'
				});
			},
			// 添加订单页面跳转方法
			goToOrder(item_id){
				switch(item_id){
					case 1: // 待付款
						uni.navigateTo({
							url: '/pages/order/nopay'
						});
						break;
					case 2: // 待发货
						uni.navigateTo({
							url: '/pages/order/nodelivery'
						});
						break;
					case 3: // 待收货
						uni.navigateTo({
							url: '/pages/order/noreceive'
						});
						break;
					case 4: // 待评价
						// 如果有待评价页面，可以添加对应跳转
						uni.showToast({
							title: '功能开发中',
							icon: 'none'
						});
						break;
					case 5: // 退款/售后
						// 如果有退款/售后页面，可以添加对应跳转
						uni.showToast({
							title: '功能开发中',
							icon: 'none'
						});
						break;
					default:
						break;
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.user_layout{
		.user_info{
			
			align-items: center;
			justify-content: center;
			flex-direction: column;
			
			.info_up{
				padding: 20rpx 40rpx;
				display: flex;
				.info_image{
					width: 120rpx;
					height: 120rpx;
					
					.images{
						width: 120rpx;
						height: 120rpx;
						border-radius: 50%;
					}
				}
				.info_phone{
					padding: 20rpx 20rpx;
					font-size: 50rpx;
					width:500rpx;
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
				}
				.update_message{
					
					margin-top: 25rpx;
					padding-top: 10rpx;
					border: 1px solid #eee;
					height: 55rpx;
					width: 320rpx;
					text-align: center;
					font-size: 30rpx;
					border-radius: 15rpx;
				}
			}
			.info_down{
				display: flex;
				justify-content: center;
				border-bottom: 1rpx solid #06121e;
				.info_down_content{
					background-color: #cdf1de;
					width: 450rpx;
					height: 60rpx;
					border: 1px solid #eee;
					border-top-left-radius: 20rpx;
					border-top-right-radius: 20rpx;
					display: flex;
					.content_text{
						padding-top: 10rpx;
						padding-left: 20rpx;
						font-size: 37rpx;
						font-family: 楷体;
						font-weight: bold;
					}
					.open{
						border-radius: 20rpx;
						height: 45rpx;
						width: 150rpx;
						text-align: center;
						margin-top: 8rpx;
						margin-left: 50rpx;
						border: 1rpx solid #06121e;
						font-weight: bold;
					}
				}
			}
			// .photo{
			// 	width: 160rpx;
			// 	height: 160rpx;
			// 	border-radius: 50%;
			// 	overflow: hidden;
			// 	image{
			// 		width: 100%;
			// 		height: 100%;
			// 	}
			// }
			// .name{
			// 	font-size: 44rpx;
			// 	color: #333;
			// 	padding: 20rpx 0 5rpx;
			// }
			// .address{
			// 	font-size: 28rpx;
			// 	color: #aaa;
			// }
		}
		.section_v1{
			width: 690rpx;
			margin: 20rpx auto;
			border: 1px solid #eee;
			border-radius: 20rpx;
			box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
			.section_header{
				display: flex;
				justify-content: space-between;
				align-content: space-between;
				.title{
					padding-left: 20rpx;
					padding-top: 5rpx;
					font-size: 44rpx;
					font-family: 黑体;			////////////////////////////////////////////
				}
				.all{
					font-size: 28rpx;
					padding-top: 20rpx;
					padding-right: 20rpx;
				}
			}
			.section_body{
				display: flex;
				
				flex-wrap: wrap;
				flex-direction: row;
				// justify-content: space-between;
				// align-content: center;
				
				// background: #f7f7f7;
				padding: 5rpx 0;
				.section_body_item{
					width: 20%;						//控制一行显示多少个元素
					height: 200rpx;
					text-align: center;
					// border:1rpx solid #ccc;		//边框
					box-sizing:border-box;	
					.section_body_item_img{
						width: 96rpx;
						height: 96rpx;
						margin-top: 30rpx;
						margin-left: 23rpx;
						.images{
							width: 96rpx;
							height: 96rpx
						}
					}
					.section_body_item_title{
						width: 100%;
						height: 34rpx;
						line-height: 34rpx;
						color: #595959;
						font-size: 30rpx;
						font-weight: bold;
						margin-top: 20rpx
					}
				}
			}
		}
		.section_v2{
			width: 690rpx;
			margin: 20rpx auto;
			border: 1px solid #eee;
			border-radius: 20rpx;
			box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
			
			.section_header{
				padding-left: 20rpx;
				padding-top: 5rpx;
				font-size: 44rpx;
				font-family: 黑体;
			}
			.section_body{
				display: flex;
				
				flex-wrap: wrap;
				flex-direction: row;
				// justify-content: space-between;
				// align-content: center;
				
				// background: #f7f7f7;
				padding: 5rpx 0;
				.section_body_item{
					width: 25%;						//控制一行显示多少个元素
					height: 200rpx;
					text-align: center;
					// border:1rpx solid #ccc;		//边框
					box-sizing:border-box;	
					
					.section_body_item_img{
						width: 96rpx;				//控制图片大小
						height: 96rpx;
						margin-top: 30rpx;			//控制图片的上下位置
						margin-left: 38rpx;			//控制图片的左右位置
						.images{
							width: 96rpx;			//控制图片大小
							height: 96rpx
						}
					}
					.section_body_item_title{
						width: 100%;
						height: 34rpx;
						line-height: 34rpx;
						color: #595959;
						font-size: 30rpx;
						font-weight: bold;
						margin-top: 20rpx
					}
				}
			}
		}
	}
</style>
