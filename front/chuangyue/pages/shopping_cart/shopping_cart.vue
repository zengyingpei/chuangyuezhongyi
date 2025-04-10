<template>
	<view >
		<view class="content">
			<!-- 每件商品 -->
			<view class="box" v-for="(item,index) in arr" :key="item.id">
				<view class="up">
					<image class="delete" src="../../static/icons/no.svg" @click="toDelete(item.id)"></image>
				</view>
				<view class="down">
					<view class="left">
						<image class="img1" src="../../static/icons/no_select.svg" v-if="assist[index]===0" @click="onUpdate(index)"></image>
						<image class="img1" src="../../static/icons/select.svg" v-else @click="onUpdate(index)"></image>
					</view>
					<view class="middle">
						<image class="img2" :src="item.image" mode=""></image>
					</view>
					<view class="right">
						<view class="title">
							{{item.name}}
						</view>
						<view class="options">
							<view class="price">￥{{item.price}}</view>
							<view class="button">
								<image class="sub" src="../../static/icons/sub.svg" @click="subOne(item.id)"></image>
								<text class="number">{{item.number}}</text>
								<image class="add" src="../../static/icons/add.svg" @click="addOne(item.id)"></image>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		
		<view class="bom">
			<view class="bom_left">
				<view class="all" @click="selectAll()">
					全选
				</view>
			</view>
			<view class="bom_right">
				<view class="pay">
					去付款
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
				arr:[],
				assist:[]	//辅助数组，初始化长度为this.arr.length的 0 数组。表示没有被选中，1表示被选中
			};
		},
		onLoad(option){
			this.getDatas();
		},
		methods:{
			// 初始化辅助数组
			initAssist(){
				this.assist = new Array (this.arr.length).fill(0);
			},
			// 初始化购物车信息
			getDatas(){
				let token = uni.getStorageSync('authorization')
				uni.request({
					url: `${baseUrl}/api/user/shoppingcart/list`,
					method:"GET",
					header:{
						'authorization' : token,
					},
					success: (res) => {
						if(res.data.code == 1){
							console.log(res.data);
							this.arr = res.data.data;
							this.assist = new Array(this.arr.length).fill(0);
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
			// 刷新购物车信息
			freshDatas(){
				let token = uni.getStorageSync('authorization')
				uni.request({
					url: `${baseUrl}/api/user/shoppingcart/list`,
					method:"GET",
					header:{
						'authorization' : token,
					},
					success: (res) => {
						if(res.data.code == 1){
							console.log(res.data);
							this.arr = res.data.data;
						}
					},
					fail: (err) => {
						uni.showToast({
							duration:1000,
							title:"资源刷新失败",
							icon:"error"
						})
					}
				})
			},
			// 购买数 加一
			addOne(shoppingcartId){
				let token = uni.getStorageSync('authorization')
				uni.request({
					url: `${baseUrl}/api/user/shoppingcart/addOne`,
					method:"POST",
					header:{
						'authorization' : token,
					},
					data:{
						id:shoppingcartId,
						num:1
					},
					success: (res) => {
						if(res.data.code == 1 ){
							console.log(res.data);
							this.freshDatas();
						}else{
							console.log(res.data);
							uni.showToast({
								duration:1000,
								title:"不能再多了,库存不足了",
								icon:"error"
							})
						}
					}
				})
			},
			// 购买数 减一
			subOne(shoppingcartId){
				let token = uni.getStorageSync('authorization')
				uni.request({
					url: `${baseUrl}/api/user/shoppingcart/addOne`,
					method:"POST",
					header:{
						'authorization' : token,
					},
					data:{
						id:shoppingcartId,
						num:-1
					},
					success: (res) => {
						if(res.data.code == 1 ){
							console.log(res.data);
							this.freshDatas();
						}
					}
				})
			},
			// 从购物车删除
			toDelete(id){
				let token = uni.getStorageSync('authorization')
				uni.request({
					url: `${baseUrl}/api/user/shoppingcart/delete`,
					method:"POST",
					header:{
						'authorization' : token,
					},
					data:{
						id : id
					},
					success: (res) => {
						if(res.data.code == 1 ){
							console.log(res.data);
							this.freshDatas()
						}
					}
				})
			},
			// 修改选中状态
			onUpdate(index){
				if(this.assist[index]===0){
					this.assist[index] = 1;
				}else{
					this.assist[index] = 0;
				}
			},
			// 全选
			selectAll(){
				// 全部改为选中（状态为1）
				for(let i=0; i < this.assist.length; i++){
					this.assist[i] = 1;
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.box{
		width: 690rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
		.up{
			text-align: right;
			.delete{
				margin-top: 5rpx;
				margin-right: 10rpx;
				width: 40rpx;
				height: 40rpx;
			}
		}
		.down{
			display: flex;
			.left{
				width: 50rpx;
				.img1{
					margin-left: 5rpx;
					margin-top: 60rpx;
					width: 40rpx;
					height: 40rpx;
				}
			}
			.middle{
				width: 170rpx;
				.img2{
					margin: 10rpx 5rpx;
					width: 150rpx;
					height: 150rpx;
				}
			}
			.right{
				.title{
					margin: 10rpx 5rpx;
					height: 100rpx;
					font-size: 40rpx;
					overflow: hidden;
					
				}
				.options{
					display: flex;
					.price{
						color: red;
						width: 300rpx;
					}
					.button{
						.sub{
							width: 30rpx;
							height: 30rpx;
							
						}
						.number{
							font-size: 35rpx;
							margin-left: 5rpx;
							margin-right: 5rpx;
						}
						.add{
							margin-top: 2rpx;
							width: 30rpx;
							height: 30rpx;
						}
					}
				}
			}
		}
	}
	.bom{
		z-index: 10;
		// background-color: aqua;
		position: fixed;
		bottom: 0;
		width: 750rpx;
		height: 150rpx;
		display: flex;
		.bom_left{
			box-sizing: border-box;
			width: 300rpx;
			padding-left: 30rpx;
			.all{
				width: 100rpx;
				padding-top: 15rpx;
				
			}
		}
		.bom_right{
			padding-left: 250rpx;
			.pay{
				color: #eee;
				text-align: center;
				line-height: 70rpx;
				height: 70rpx;
				width: 150rpx;
				background-color:#6ea3e5 ;
				border: 1px solid #eee;
				border-radius: 20rpx;
				box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
			}
		}
	}
</style>
