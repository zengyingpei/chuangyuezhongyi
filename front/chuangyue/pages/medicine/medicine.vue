<template>
	<view>
		
		<view class="buy_car">
			<image src="../../static/icons/buy_car.svg" @click="goShoppingCart()"></image>
			<view class="buy_car_text" @click="goShoppingCart()">我的购物车</view>
		</view>
		<view class="">
			<view class="search">
				<input class="search_box" type="text" placeholder="输入药品名" @input="onInput" :value="searchName"/>
				<view class="icon">
					<image src="../../static/icons/search.svg" @click="onSearch"></image>
				</view>
			</view>
		</view>
		
		<view class="container">
			<view class="title">
				商品
			</view>
			<view class="body">
				<view class="item" v-for="(item, index) in arr" :key="item.id">
					<view class="item_img">
						<image :src="item.image" mode=""></image>
					</view>
					<view class="description">{{item.name}}</view>
					<view class="item_bom">
						<view class="price">￥{{item.price}}</view>
						<view class="add">
							
							<image v-if="item.number===0" src="../../static/icons/no.svg" @click="showNoStorage()"></image>
							<image v-else src="../../static/icons/add.svg" @click="addToShoppingCart(item.id)" ></image>
						</view>
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
				arr:[],			//存储yao
				searchName:''	//搜索框的内容
			}
		},
		onLoad(option){
			this.getDatas();
		},
		methods: {
			getDatas(){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/medicine/list1`,
					method:"GET",
					header:{
						'authorization' : token,
					},
					success: (res) => {
						if(res.data.code == 1){
							console.log(res.data);
							this.arr = res.data.data;
						}
					}
				})
			},
			showNoStorage(){
				uni.showToast({
					duration:1000,
					title:"抱歉，当前无货",
					icon:"error"
				})
			},
			goShoppingCart(){
				uni.navigateTo({
					url: '/pages/shopping_cart/shopping_cart'
				})
			},
			addToShoppingCart(medicine_id){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/shoppingcart/addNew`,
					method:"POST",
					header:{
						'authorization' : token,
					},
					data:{
						medicineId : medicine_id,
					},
					success: (res) => {
						if(res.data.code == 1 ){
							console.log(res.data);
							uni.showToast({
								duration:1000,
								title:"添加成功",
								icon:"success"
							})
						}else{
							uni.showToast({
								duration:1000,
								title:"不能再多了，库存不足了",
								icon:"error"
							})
						}
					}
				})
			},
			// 搜索框实时更新
			onInput(e){
				this.searchName = e.detail.value;
				console.log(this.searchName);
			},
			// 搜索指定内容
			onSearch(){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/medicine/list1`,
					method:"GET",
					header:{
						'authorization' : token,
					},
					data:{
						name: this.searchName
					},
					success: (res) => {
						if(res.data.code == 1){
							console.log(res.data);
							this.arr = res.data.data;
						}
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	.buy_car{
		display: flex;
		justify-content: center;
		margin-bottom: 10rpx;
		image{
			width: 70rpx;
			height: 70rpx;
		}
		.buy_car_text{
			padding-top: 10rpx;
			font-size: 44rpx;
		}
	}
	
	.search{
		// width: 690rpx;
		border-radius: 20rpx;
		// border: 1rpx solid black;
		display: flex;
		justify-content: center;
		
		.search_box{
			border: 1rpx solid #6ea3e5;
			border-right-width: 0;
			border-top-left-radius: 20rpx;
			border-bottom-left-radius: 20rpx;
			padding-left: 10rpx;
			width: 75%;
			height: 80rpx;
			font-size: 35rpx;
			font-weight: lighter;
		}
		.icon{
			width: 80rpx;
			height: 100%;
			border-left-width: 0;
			border: 1rpx solid #6ea3e5;
			image{
				width: 80rpx;
				height: 70rpx;
			}
		}
	}
	
	.container{
		margin: 20rpx auto;
		width: 690rpx;
		border: 1rpx solid #eee;
		border-radius: 20rpx;
		.title{
			padding: 10rpx 10rpx;
			font-size: 50rpx;
			font-weight: bold;
			font-family: 黑体;
		}
		.body{
			display: flex;
			flex-wrap: wrap;
			flex-direction: row;
			.item{
				width: 33%;
				// height: 300rpx;
				.item_img{
					width: 220rpx;
					height: 220rpx;
					image{
						width: 220rpx;
						height: 220rpx;
					}
				}
				.description{
					width: 220rpx;
					// background-color: red;
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
				}
				.item_bom{
					display: flex;
					flex-wrap: wrap;
					justify-content: space-between;
					width: 220rpx;
					height: 60rpx;
					padding-top: 5rpx;
					.price{
						color: red;
					}
					.add{
						width: 38rpx;
						height: 38rpx;
						padding-top: 5rpx;
						padding-right: 5rpx;
						image{
							width: 38rpx;
							height: 38rpx;
						}
					}
				}
			}
		}
	}
</style>
