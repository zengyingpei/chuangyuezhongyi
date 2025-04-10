<template>
	<view>
		<view class="buy_car">
			<image src="../../static/icons/buy_car.svg" @click="goShoppingCart()" ></image>
			<view class="buy_car_text" @click="goShoppingCart()">我的购物车</view>
		</view>
		
		<!-- 搜索 -->
		<view class="">
			<view class="search">
				<input class="search_box" type="text" placeholder="输入名称"  @input="onInput" :value="searchName"/>
				<view class="icon">
					<image src="../../static/icons/search.svg" @click="onSearch"></image>
				</view>
			</view>
		</view>
		
		<!-- 滚动条 -->
		<view class="container">
			<view class="box">
				<scroll-view class="my_scroll" scroll-x="true" >
					<view class="sort" v-for="(item, index) in sorts" :key="item.id" @click="onSelect(item.id)" :style="{color : isSelected(item.id) ? '#7ca0ec':'black'}">
						{{item.name}}
					</view>
				</scroll-view>
			</view>
		</view>
		
		
		<view class="container1">
			<view class="title">
				
			</view>
			<view class="body">
				
				<!-- 养生品展示 -->
				<view class="item" v-for="(item, index) in arr" :key="item.id">
					<view class="item_img">
						<image :src="item.image" ></image>
					</view>
					<view class="description">{{item.name}}</view>
					<view class="item_bom">
						<view class="price">￥{{item.price}}</view>
						<view class="add">
							<image v-if="item.number === 0" src="../../static/icons/no.svg"  @click="showNoStorage()"></image>
							<image v-else src="../../static/icons/add.svg" @click="addToShoppingCart(item.id)"></image>
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
				sorts:[],			// 存储分类
				arr:[],				// 存储养生品信息
				searchName:'',      // 存储搜索框内容
				nowSelectedSort:-1	//开始时展示的是id为-1的分类，表示 “全部”
			}
		},
		onLoad(option){
			this.getDatas()
		},
		methods: {
			// 初始化数据
			getDatas(){
				let token = uni.getStorageSync('authorization')
				uni.request({
					url: `${baseUrl}/api/user/medicine/list2`,
					method:"GET",
					header:{
						'authorization' : token,
					},
					success: (res) => {
						if(res.data.code == 1){
							console.log(res.data);
							this.sorts = res.data.data.categories;
							this.sorts.unshift({
								id: -1,
								name:"全部"								
							});
							this.arr = res.data.data.medicines;
						}
					},
					fail: (err) => {
						uni.showToast({
							duration:1000,
							title:"数据获取失败",
							icon:"error"
						})
					}
				})
			},
			// 进入购物车
			goShoppingCart(){
				uni.navigateTo({
					url: '/pages/shopping_cart/shopping_cart'
				})
			},
			// 添加购物车
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
						}
					}
				})
			},
			//展示无货
			showNoStorage(){
				uni.showToast({
					duration:1000,
					title:"抱歉，当前无货",
					icon:"error"
				})
			},
			// 根据分类筛选
			onSelect(categoryId){
				let token = uni.getStorageSync('authorization')
				uni.request({
					url: `${baseUrl}/api/user/medicine/list2`,
					method:"GET",
					header:{
						'authorization' : token,
					},
					data:{
						categoryId: categoryId
					},
					success: (res) => {
						if(res.data.code == 1){
							console.log(res.data);
							this.arr = res.data.data.medicines;
						}
					},
					fail: (err) => {
						uni.showToast({
							duration:1000,
							title:"数据获取失败",
							icon:"error"
						})
					}
				});
				this.updateSelected(categoryId);
			},
			// 搜索框实时更新事件
			onInput(e){
				this.searchName = e.detail.value;
				console.log(this.searchName);
			},
			// 根据搜索内容，筛选养生品
			onSearch(){
				console.log(this.searchName);
				let token = uni.getStorageSync('authorization')
				uni.request({
					url: `${baseUrl}/api/user/medicine/list2`,
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
							this.arr = res.data.data.medicines;
						}
					},
					fail: (err) => {
						uni.showToast({
							duration:1000,
							title:"数据获取失败",
							icon:"error"
						})
					}
				})
			},
			updateSelected(idx){
				this.nowSelectedSort = idx;
			},
			isSelected(idx){
				return this.nowSelectedSort == idx;
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
		width: 690rpx;
		margin: 20rpx auto;
		border: 1rpx solid #eee;
		border-top-left-radius: 20rpx;
		border-top-right-radius: 20rpx;
		box-shadow: 0 0 40rpx rgba(0, 0, 0, 0.06);
		overflow: hidden;
		.box{
			.my_scroll{
				white-space: nowrap;
				width: 685rpx;
				::-webkit-scrollbar {
				    display: none;
				    width: 0;
				    height: 0;
				  }
				.sort{
					font-size: 40rpx;
					display: inline-block;
					width: 180rpx;
					height: 70rpx;
					padding: 10rpx 10rpx;
					text-align: center;
				}
			}
			
		}
	}
	
	.container1{
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
				width: 50%;
				// padding-left: 1rpx;
				// height: 300rpx;
				.item_img{
					width: 340rpx;
					height: 340rpx;
					image{
						width: 340rpx;
						height: 340rpx;
					}
				}
				.description{
					width: 340rpx;
					// background-color: red;
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
				}
				.item_bom{
					display: flex;
					flex-wrap: wrap;
					justify-content: space-between;
					width: 340rpx;
					height: 60rpx;
					padding-top: 5rpx;
					.price{
						color: red;
					}
					.add{
						width: 38rpx;
						height: 38rpx;
						padding-top: 5rpx;
						padding-right: 10rpx;
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
