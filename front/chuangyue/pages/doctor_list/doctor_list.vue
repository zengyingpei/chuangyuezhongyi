<template>
	<view class="">
		<view class="search-container">
			<input class="search-input" type="text" placeholder="请输入关键词" @input="onInput" @confirm="onConfirm" />
			<button class="search-btn" @tap="onSearch">搜索</button>
		</view>
		
		<view class="section">
			<view class="section_left">
				<view class="section_title">当前：</view>
				<view class="section_content" @click="goToBack">{{sicknessName}}<uni-icons type="down" ></uni-icons></view>
			</view>
			<view class="section_right">
				<view class="section_title">价格：</view>
				<view class="section_content">全部<uni-icons type="down" ></uni-icons></view>
			</view>
		</view>
		
		<view class="doc_card" v-for="(item, index) in doctors" :key="item.id" @click="goDetail(item.id)">
			<view class="doc_left_img">
				<image class="doc_images" :src="item.avatar" mode=""></image>
			</view>
			<view class="doc_right_info">
				<view class="doc_info_top">
					<text class="doc_name">{{item.name}}</text>
					<text class="doc_description">{{item.level}}</text>
				</view>
				<view class="doc_info_bom">
					<view class="doc_info_bom_up">{{item.workplace}}</view>
					<view class="doc_info_bom_down ellipsis-2">{{item.desc}}</view>
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
				searchValue: '',
				sicknessId:null,		//当前展示的是什么疾病(id)
				sicknessName:null,		//当前展示的是什么疾病(name)	
				doctors:null,   		//获取该疾病对应的所有医生
				
			};
		},
		onLoad(option){
			this.sicknessId=option.sicknessId;
			this.sicknessName=option.sicknessName;
			// console.log("load()执行");
			// console.log(option.sicknessId);
			this.getDatas(this.sicknessId);			//获取医生数据
		},
		methods: {
			onInput(value) {
				this.searchValue = value;
			},
			onConfirm() {
				this.onSearch(this.searchValue);
			},
			onSearch(value) {
				console.log('搜索的内容：', value);
				// 在这里处理搜索逻辑
			},
			
			// 下面是获取医生数据
			getDatas(sicknessId){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/doctor`,
					method:'POST',
					header:{
						'authorization' : token,
					},
					data:{
						page: 1,
						pageSize: 2,
						sicknessId: sicknessId
					},
					success: (res) => {
						if(res.data.code==1){
							this.doctors=res.data.data.items;
							// console.log(res.data);
						}else{
							uni.showToast({
								duration:1000,
								icon:'error',
								title:"数据加载失败"
							})
						}	
					}
				})
			},
			
			
			//下面是处理重新选择疾病分类
			goToBack(){
				uni.navigateBack();
			},
			
			
			// 进入医生详情页
			goDetail(doctorId){
				uni.navigateTo({
					url: `/pages/doctor_detail/doctor_detail?doctorId=${doctorId}`
				})
			}
		}
		
	};
</script>

<style lang="scss">
	.search-container {
		display: flex;
		align-items: center;
		padding: 20rpx;
		background-color: #f5f5f5;

		.search-input {
			flex: 1;
			height: 60rpx;
			padding: 0 20rpx;
			font-size: 30rpx;
			border-radius: 30rpx;
			border: 1rpx solid #ccc;
		}

		.search-btn {
			height: 60rpx;
			min-width: 80rpx;
			line-height: 60rpx;
			text-align: center;
			border-radius: 30rpx;
			background-color: #007aff;
			color: #fff;
			margin-left: 20rpx;
			border: none;
			outline: none;
		}
	}
	.section{
		display: flex;
		background-color: #f5f5f5;
		width: 100%;
		border-bottom: 2rpx solid #cccccc;
		.section_left{
			width: 50%;
			display: flex;
			justify-content: center;
			// margin-bottom: 20rpx;
			.section_title{
				// background-color: yellow;
				height: 60rpx;
			}
			.section_content{
				color: #7ca0ec;
				// background-color: green;
			}
		}
		.section_right{
			width: 50%;
			display: flex;
			justify-content: center;
			// margin-bottom: 20rpx;
			.section_title{
				// background-color: yellow;
			}
			.section_content{
				color: #7ca0ec;
				// background-color: green;
			}
		}
	}
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
			height: 200rpx;
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
					// 添加多行文本溢出省略效果
					display: -webkit-box;
					-webkit-box-orient: vertical;
					-webkit-line-clamp: 2; // 限制为两行
					overflow: hidden;
					text-overflow: ellipsis;
					word-break: break-all;
					font-size: 26rpx;
					color: #666;
					line-height: 1.4;
				}
			}
		}
	}
</style>

