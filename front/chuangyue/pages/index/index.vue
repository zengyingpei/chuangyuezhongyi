<template>
	<view class="section">
		<view class="left" @click="goToFind">
			<view class="text1">
				按疾病找
			</view>
			<view class="text2">
				覆盖多种疾病
			</view>
			<view class="goto_find">
				去查找
			</view>
		</view>
		<view class="right">
			<scroll-view class="my_scroll" scroll-x="true" >
				<view class="block_item" v-for="(item, index) in list1" :key="item.id">
					<view class="block_item_up">
						{{list1[index].name}}
					</view>
					<view class="block_item_down">
						{{list2[index].name}}
					</view>
				</view>
			</scroll-view>
		</view>
	</view>
	
	<!-- <quick_query></quick_query> -->
	<view class="quick_section">
		<view class="quick_section_item">
			<view class="quick_box">
				<view class="quick_section_item_left">
					<view class="quick_big">专家问诊</view>
					<view class="quick_small">在线问诊</view>
				</view>
				<view class="quick_section_item_right">
					<image class="quick_images" src="../../static/icons/query.svg" mode=""></image>
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
					<image class="quick_images" src="../../static/icons/gh.svg" mode=""></image>
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
					<image class="quick_images" src="../../static/icons/jk.svg" mode=""></image>
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
					<image class="quick_images" src="../../static/icons/medicine.svg" mode=""></image>
				</view>
			</view>
		</view>
	</view>
	
	<!-- <good-doctors></good-doctors> -->
	<view class="doc_container">
		<view class="doc_title">精选医生</view>
		
		
		<swiper class="doc_swiper" :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000">
			<swiper-item v-for="(item, index) in arr1" :key="item.id">
				<view class="doc_swiper-item" >
					<view class="doc_content">
						<view class="doc_card" @click="goDetail(arr1[index].id)">
							<view class="doc_left_img">
								<image class="doc_images" :src="arr1[index].avatar" mode=""></image>
							</view>
							<view class="doc_right_info">
								<view class="doc_info_top">
									<text class="doc_name">{{arr1[index].name}}</text>
									<text class="doc_description">{{arr1[index].level}}</text>
								</view>
								<view class="doc_info_bom">
									<view class="doc_info_bom_up">{{arr1[index].workplace}}</view>
									<view class="doc_info_bom_down">{{arr1[index].desc}}</view>
								</view>
							</view>
						</view>
						
						
						<view class="doc_card" @click="goDetail(arr2[index].id)">
							<view class="doc_left_img">
								<image class="doc_images" :src="arr2[index].avatar" mode=""></image>
							</view>
							<view class="doc_right_info">
								<view class="doc_info_top">
									<text class="doc_name">{{arr2[index].name}}</text>
									<text class="doc_description">{{arr2[index].level}}</text>
								</view>
								<view class="doc_info_bom">
									<view class="doc_info_bom_up">{{arr2[index].workplace}}</view>
									<view class="doc_info_bom_down">{{arr2[index].desc}}</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</swiper-item>
		</swiper>
	</view>
	
	
	
	<view class="consult_container">
		<view class="consult_title">
			养生资讯
		</view>
		<view class="consult_content">
			<view class="consult_card" v-for="(item, index) in consults" :key="item.id"  >
				<view class="consult_card_left" @click="handleClick(item.id)">
					<view class="consult_card_left_top">
						{{item.title}}
					</view>
					<view class="consult_card_left_bom">
						{{item.from}}
					</view>
				</view>
				
				<view class="consult_card_right">
					<view class="consult_card_right_img">
						<image class="consult_images" :src="item.img_url" mode=""></image>
					</view>
				</view>
			</view>
		</view>
		<view class="more">
			<view class="more_body">
				查看全部 >
			</view>
		</view>
	</view>
	
</template>

<script>
	import {baseUrl} from '../../common/js/utils.js'

	
	export default {
		
		data() {
			return {
				arr1:null,
				arr2:null,
				list1:[
					{id:1, name:"失眠"},
					{id:2, name:"月经不调"},
					{id:3, name:"痘痘"},
					{id:4, name:"早些"},
					{id:5, name:"客户尽快"},
					{id:6, name:"3为"},
					{id:7, name:"阿三大王"},
					{id:8, name:"撒旦撒"},
				],
				list2:[
					{id:1, name:"皮炎"},
					{id:2, name:"荨麻疹"},
					{id:3, name:"偏头痛"},
					{id:4, name:"dd"},
					{id:5, name:"ee"},
					{id:6, name:"ff"},
					{id:7, name:"gg"},
					{id:8, name:"hh"},
				],
				// consults:[
				// 	{id:1, title:"晨起后的黄金时间别浪费，改掉4个坏习惯，更能健康长寿",from:"每日健康",img_url:"../../static/imgs/women.jpg"},
				// 	{id:2, title:"“三高”必喝茶饮:降压、降糖还能滋养肝肾",from:"每日健康",img_url:"../../static/imgs/tea.jpg"},
				// 	{id:3, title:"“饭量决定寿命”，吃得越多“走”得越早，是真的吗?",from:"每日健康",img_url:"../../static/imgs/ta.jpg"},
				// 	{id:4, title:"“入夏无病三分虚”，做好这些防护措施，让夏天不再难熬",from:"每日健康",img_url:"../../static/imgs/flower.jpg"},
				// 	{id:5, title:"食物分阴阳，吃对才健康，一招教您辨别食物的阴阳属性",from:"每日健康",img_url:"../../static/imgs/阴阳.jpg"},
				// 	{id:6, title:"一年四季都“怕冷”，这类人要如何养?",from:"每日健康",img_url:"../../static/imgs/阳虚.jpg"},
				// ]
				consults:[]
				
				
			}
		},
		onLoad (option){
			this.getDatas();//首页动态资源加载
			this.getConsults()
		},
		onShow(){
			this.getDatas();
			this.getConsults()// 重新打开页面的时候，也加载数据
		},
		methods: {
			goToFind(){
				uni.navigateTo({
					url:'/pages/select_find/select_find'
				})
			},
			getDatas(){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/doctor/select_doc`,
					method:'GET',
					header:{
						authorization : token
					},
					success: (res) => {
						if(res.data.code==1){
							console.log(res.data.data);
							
							this.arr1=res.data.data.slice(0,5);
							this.arr2=res.data.data.slice(5,10);
						}else{
								uni.showToast({
									duration:1000,
								icon:'error',
								title:"数据获取失败"
							})
						}
					}
				})
			},
			getConsults(){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/consults/list`,
					method:'GET',
					header:{
						authorization : token
					},
					success: (res) => {
						if(res.data.code==1){
							console.log('res',res)
							this.consults=res.data.data;
						}else{
								uni.showToast({
									duration:1000,
								icon:'error',
								title:"数据获取失败"
							})
						}
					}
				})
			},
			// 进入医生详情页
			goDetail(doctorId){
				uni.navigateTo({
					url: `/pages/doctor_detail/doctor_detail?doctorId=${doctorId}`
				})
			},
			handleClick(id){
				console.log('id=',id)
				uni.navigateTo({
					url:"/pages/index/info?&id="+id
				})
				uni.navigateTo({
					url:"/pages/preview/preview?&id="+id
				})
			}
		}
	}
</script>

<style lang="scss">
	
	.section{
		width: 690rpx;
		height: 200rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
		display: flex;
		overflow: hidden;
		.left{
			flex-basis: 28%;
			background-color: #7ca0ec;
			.text1{
				color: #eee;
				font-weight: bold;
				font-size: 40rpx;
				margin-left: 10rpx;
				margin-top: 10rpx;
			}
			.text2{
				color: #eee;
				font-size: 29rpx;
				margin-left: 10rpx;
				margin-top: 10rpx;
			}
			.goto_find{
				background-color: #eee;
				width: 70%;
				border-radius: 20rpx;
				text-align: center;
				color: #7ca0ec;
				margin-top: 20rpx;
				margin-left: 10rpx;
			}
		}
		.right{
			flex-basis: 72%;
			.my_scroll{
				::-webkit-scrollbar {
				    display: none;
				    width: 0;
				    height: 0;
				  }
				white-space: nowrap;
				width: 496rpx;
				height: 200rpx;
				.block_item{					//控制小块
					height: 200rpx;
					width: 200rpx;				
					// background-color: blue;
					font-size: 38rpx;
					overflow: hidden;
					display: inline-block;		//滚动view内部的元素必须加上这个
					.block_item_up{				//控制小块 上半
						margin-top: 20rpx;
						margin-left: 20rpx;
						height: 65rpx;
						width: 84%;		
						background-color: #fefbf6;
						border-radius: 15rpx;
						display: flex;
						justify-content: center;
						align-items: center;
					}
					.block_item_down{			//控制小块 下半
						margin-top: 30rpx;
						margin-left: 20rpx;
						height: 65rpx;
						width: 84%;		
						background-color: #fefbf6;
						border-radius: 15rpx;
						display: flex;
						justify-content: center;
						align-items: center;
					}
				}
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
				width: 25%;						//控制一行显示多少个元素
				height: 200rpx;
				text-align: center;
				// border:1rpx solid #ccc;		//边框
				box-sizing:border-box;	
				.section_body_item_img{
					width: 96rpx;
					height: 96rpx;
					margin-top: 30rpx;
					margin-left: 40rpx;
					.images{
						width: 96rpx;
						height: 96rpx
					}
				}
				.section_body_item_title{
					width: 100%;
					height: 34rpx;
					line-height: 34rpx;
					color: #06121e;
					font-size: 30rpx;
					font-weight: bold;
					margin-top: 20rpx
				}
			}
		}
	}
	
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
	
	.doc_container{
		width: 690rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
		.doc_title{
			font-size: 50rpx;
			font-weight: bold;
			padding-top: 5rpx;
			padding-left: 10rpx;
			margin-bottom: 15rpx;
		}
		.doc_swiper{
			height: 520rpx;
		}
		.doc_swiper-item{
			.doc_content{
				// background-color: red;
				display: flex;
				flex-wrap: wrap;
				justify-content: space-between;
				align-content: space-between;
				.doc_card{
					margin: 5rpx 10rpx;
					height: 250rpx;
					width: 660rpx;
					// background-color: green;
					border-radius: 20rpx;
					display: flex;
					box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.1);
					.doc_left_img{
						margin: 20rpx 20rpx;
						width: 200rpx;
						height: 200rpx;
						// border-radius: 50%;
						.doc_images{
							width: 180rpx;
							height: 180rpx;
							border-radius: 50%;
						}
					}
					.doc_right_info{
						.doc_info_top{
							width: 420rpx;
							height: 66rpx;
							overflow: hidden;
							padding-top: 20rpx;
							
							.doc_name{
								font-weight: bold;
								font-size: 50rpx;
								margin-right: 10rpx;
							}
							.doc_description{
								font-size: 28rpx;
							}
						}
						.doc_info_bom{
							margin-top: 5rpx;
							.doc_info_bom_up{
								font-size: 28rpx;
								font-style: italic;
								margin-bottom: 5rpx;
							}
							.doc_info_bom_down{
								width: 420rpx;
								overflow: hidden;
								text-overflow: ellipsis;
								white-space: nowrap;
							}
						}
					}
				}
			}
		}
	}
	
	.consult_container{
		width: 690rpx;
		margin: 20rpx auto;
		border: 1rpx solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 40rpx rgba(0, 0, 0, 0.06);
		.consult_title{
			font-size: 50rpx;
			font-weight: bold;
			padding: 5rpx 10rpx;
		}
		.consult_content{
			margin-top: 10rpx;
			.consult_card{
				border-radius: 20rpx;
				border: 1rpx solid #eee;
				box-shadow: 0 0 40rpx rgba(0, 0, 0, 0.06);
				overflow: hidden;
				display: flex;
				height: 250rpx;
				.consult_card_left{
					padding-left: 20rpx;
					
					.consult_card_left_top{
						padding-top: 20rpx;
						height: 180rpx;
						font-size: 38rpx;
					}
					.consult_card_left_bom{
						font-size: 28rpx;
						font-weight: lighter;
					}
				}
				.consult_card_right{
					width: 220rpx;
					padding-top: 20rpx;
					.consult_card_right_img{
						width: 220rpx;
						height: 180rpx;
						.consult_images{
							width: 220rpx;
							height: 180rpx;
						}
					}
				}
			}
		}
		.more{
			padding-top: 20rpx;
			height: 80rpx;
			display: flex;
			justify-content: center;
			.more_body{
				height: 48rpx;
				width: 170rpx;
				padding-top: 5rpx;
				text-align: center;
				border: 2rpx solid #282c35;
				border-radius: 30rpx;
				font-size: 30rpx;
				font-weight: lighter;
			}
		}
	}
</style>
