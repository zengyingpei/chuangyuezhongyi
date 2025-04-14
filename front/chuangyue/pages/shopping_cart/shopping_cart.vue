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
				<view class="total-price" v-if="getSelectedCount() > 0">
					合计: <text class="price-value">￥{{getTotalPrice()}}</text>
				</view>
			</view>
			<view class="bom_right">
				<view class="pay" @click="showAddressSelector">
					去付款({{getSelectedCount()}})
				</view>
			</view>
		</view>
		
		<!-- 地址选择弹窗 -->
		<uni-popup ref="addressPopup" type="center">
			<view class="address-popup">
				<view class="popup-header">
					<text class="popup-title">选择收货地址</text>
					<text class="close-btn" @click="hideAddressSelector">×</text>
				</view>
				
				<!-- 地址列表 -->
				<scroll-view scroll-y="true" class="address-list">
					<view class="empty-address" v-if="addressList.length === 0">
						<text>暂无收货地址，请先添加</text>
						<button class="add-address-btn" @click="goToAddAddress">添加地址</button>
					</view>
					
					<view 
						class="address-item" 
						v-for="(address, index) in addressList" 
						:key="address.id"
						:class="{ 'selected': selectedAddressId === address.id }"
						@click="selectAddress(address.id)"
					>
						<view class="address-info">
							<view class="address-header">
								<text class="name">{{address.receiverName}}</text>
								<text class="phone">{{address.receiverPhone}}</text>
								<text class="default-tag" v-if="address.isDefault === 1">默认</text>
							</view>
							<view class="address-detail">
								{{address.province}}{{address.city}}{{address.district}}{{address.detail}}
							</view>
						</view>
						<view class="address-select">
							<image 
								class="select-icon" 
								:src="selectedAddressId === address.id ? '../../static/icons/select.svg' : '../../static/icons/no_select.svg'"
							></image>
						</view>
					</view>
				</scroll-view>
				
				<!-- 底部按钮 -->
				<view class="popup-footer">
					<button class="cancel-btn" @click="hideAddressSelector">取消</button>
					<button class="confirm-btn" @click="submitOrder" :disabled="!selectedAddressId">结算</button>
				</view>
			</view>
		</uni-popup>
		
		<!-- 加载中弹窗 -->
		<uni-popup ref="loadingPopup" type="center" :mask-click="false">
			<view class="loading-popup">
				<view class="spinner"></view>
				<text>订单提交中...</text>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	import {baseUrl} from '../../common/js/utils.js'
	export default {
		data() {
			return {
				arr:[],
				assist:[],	//辅助数组，初始化长度为this.arr.length的 0 数组。表示没有被选中，1表示被选中
				addressList: [], // 存储收货地址列表
				selectedAddressId: null, // 选中的地址ID
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
			},
			
			// 获取选中商品数量
			getSelectedCount() {
				let count = 0;
				for(let i=0; i < this.assist.length; i++){
					if(this.assist[i] === 1){
						count++;
					}
				}
				return count;
			},
			
			// 计算选中商品总价
			getTotalPrice() {
				let total = 0;
				for(let i=0; i < this.assist.length; i++){
					if(this.assist[i] === 1){
						total += this.arr[i].price * this.arr[i].number;
					}
				}
				return total.toFixed(2);
			},
			
			// 显示地址选择器
			showAddressSelector() {
				// 检查是否有选中的商品
				if(this.getSelectedCount() === 0) {
					uni.showToast({
						title: '请先选择商品',
						icon: 'none',
						duration: 2000
					});
					return;
				}
				
				// 获取地址列表
				this.getAddressList();
				
				// 显示弹窗
				this.$refs.addressPopup.open();
			},
			
			// 隐藏地址选择器
			hideAddressSelector() {
				this.$refs.addressPopup.close();
			},
			
			// 获取地址列表
			getAddressList() {
				let token = uni.getStorageSync('authorization');
				uni.showLoading({
					title: '加载地址中...'
				});
				
				uni.request({
					url: `${baseUrl}/api/user/address/list`,
					method: 'GET',
					header: {
						'authorization': token
					},
					success: (res) => {
						uni.hideLoading();
						if(res.data.code === 1) {
							this.addressList = res.data.data;
							console.log('地址列表:', this.addressList);
							
							// 如果有默认地址，自动选中
							const defaultAddress = this.addressList.find(addr => addr.isDefault === 1);
							if(defaultAddress) {
								this.selectedAddressId = defaultAddress.id;
							} else if(this.addressList.length > 0) {
								// 如果没有默认地址但有地址，选中第一个
								this.selectedAddressId = this.addressList[0].id;
							} else {
								this.selectedAddressId = null;
							}
						} else {
							uni.showToast({
								title: '获取地址失败',
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
			
			// 选择地址
			selectAddress(addressId) {
				this.selectedAddressId = addressId;
			},
			
			// 跳转到添加地址页面
			goToAddAddress() {
				uni.navigateTo({
					url: '/pages/address/add_address',
					events: {
						// 监听添加地址成功事件
						addressAdded: () => {
							// 重新获取地址列表
							this.getAddressList();
						}
					}
				});
			},
			
			// 提交订单
			submitOrder() {
				// 检查是否选择了地址
				if(!this.selectedAddressId) {
					uni.showToast({
						title: '请选择收货地址',
						icon: 'none',
						duration: 2000
					});
					return;
				}
				
				// 获取选中的商品ID列表
				const selectedIds = [];
				for(let i=0; i < this.assist.length; i++){
					if(this.assist[i] === 1){
						selectedIds.push(this.arr[i].id);
					}
				}
				
				if(selectedIds.length === 0) {
					uni.showToast({
						title: '请选择商品',
						icon: 'none',
						duration: 2000
					});
					return;
				}
				
				// 显示加载中弹窗
				this.$refs.loadingPopup.open();
				
				// 发送提交订单请求
				let token = uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/order/submit`,
					method: 'POST',
					header: {
						'authorization': token,
						'Content-Type': 'application/json'
					},
					data: {
						ids: selectedIds,
						addressId: this.selectedAddressId
					},
					success: (res) => {
						this.$refs.loadingPopup.close();
						
						if(res.data.code === 1) {
							// 订单提交成功
							uni.showToast({
								title: '订单提交成功',
								icon: 'success',
								duration: 2000
							});
							
							// 关闭地址选择弹窗
							this.hideAddressSelector();
							
							// 刷新购物车数据
							this.freshDatas();
							
							// 重置选中状态
							this.assist = new Array(this.arr.length).fill(0);
							
							// 可以选择跳转到订单页面
							setTimeout(() => {
								uni.navigateTo({
									url: '/pages/order/orders'
								});
							}, 1500);
						} else {
							uni.showToast({
								title: res.data.message || '订单提交失败',
								icon: 'none',
								duration: 2000
							});
						}
					},
					fail: (err) => {
						this.$refs.loadingPopup.close();
						uni.showToast({
							title: '网络错误',
							icon: 'none',
							duration: 2000
						});
					}
				});
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
		background-color: #fff;
		position: fixed;
		bottom: 0;
		width: 750rpx;
		height: 100rpx;
		display: flex;
		align-items: center;
		box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.05);
		.bom_left{
			box-sizing: border-box;
			width: 500rpx;
			padding-left: 30rpx;
			display: flex;
			align-items: center;
			.all{
				width: 100rpx;
				padding-top: 5rpx;
				font-size: 28rpx;
			}
			.total-price {
				margin-left: 20rpx;
				font-size: 28rpx;
				color: #333;
				.price-value {
					color: #ff4d4f;
					font-weight: bold;
					font-size: 32rpx;
				}
			}
		}
		.bom_right{
			.pay{
				color: #fff;
				text-align: center;
				line-height: 70rpx;
				height: 70rpx;
				width: 200rpx;
				background-color:#6ea3e5;
				border-radius: 35rpx;
				box-shadow: 0 4rpx 10rpx rgba(110, 163, 229, 0.3);
				font-size: 28rpx;
			}
		}
	}
	
	/* 地址选择弹窗样式 */
	.address-popup {
		width: 650rpx;
		background-color: #fff;
		border-radius: 20rpx;
		overflow: hidden;
		
		.popup-header {
			padding: 30rpx;
			display: flex;
			justify-content: space-between;
			align-items: center;
			border-bottom: 1px solid #f0f0f0;
			
			.popup-title {
				font-size: 32rpx;
				font-weight: bold;
				color: #333;
			}
			
			.close-btn {
				font-size: 40rpx;
				color: #999;
				padding: 0 10rpx;
			}
		}
		
		.address-list {
			max-height: 600rpx;
			padding: 0 20rpx;
			
			.empty-address {
				padding: 60rpx 0;
				text-align: center;
				color: #999;
				font-size: 28rpx;
				
				.add-address-btn {
					margin-top: 30rpx;
					width: 240rpx;
					height: 70rpx;
					line-height: 70rpx;
					font-size: 28rpx;
					background-color: #6ea3e5;
					color: #fff;
					border-radius: 35rpx;
				}
			}
			
			.address-item {
				display: flex;
				justify-content: space-between;
				align-items: center;
				padding: 30rpx 20rpx;
				border-bottom: 1px solid #f0f0f0;
				
				&.selected {
					background-color: #f6f9fe;
				}
				
				.address-info {
					flex: 1;
					
					.address-header {
						margin-bottom: 10rpx;
						
						.name {
							font-size: 30rpx;
							font-weight: bold;
							color: #333;
							margin-right: 20rpx;
						}
						
						.phone {
							font-size: 28rpx;
							color: #666;
							margin-right: 20rpx;
						}
						
						.default-tag {
							font-size: 22rpx;
							color: #6ea3e5;
							background-color: #f0f7ff;
							padding: 4rpx 12rpx;
							border-radius: 4rpx;
							border: 1px solid #d4e6fc;
						}
					}
					
					.address-detail {
						font-size: 28rpx;
						color: #666;
						line-height: 1.4;
					}
				}
				
				.address-select {
					width: 60rpx;
					display: flex;
					justify-content: center;
					align-items: center;
					
					.select-icon {
						width: 40rpx;
						height: 40rpx;
					}
				}
			}
		}
		
		.popup-footer {
			display: flex;
			padding: 20rpx;
			border-top: 1px solid #f0f0f0;
			
			button {
				flex: 1;
				height: 80rpx;
				line-height: 80rpx;
				font-size: 30rpx;
				margin: 0 10rpx;
				
				&.cancel-btn {
					background-color: #f5f5f5;
					color: #666;
				}
				
				&.confirm-btn {
					background-color: #6ea3e5;
					color: #fff;
					
					&:disabled {
						background-color: #cccccc;
						color: #ffffff;
					}
				}
			}
		}
	}
	
	/* 加载中弹窗样式 */
	.loading-popup {
		width: 240rpx;
		height: 240rpx;
		background-color: rgba(0, 0, 0, 0.7);
		border-radius: 20rpx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		
		text {
			color: #fff;
			font-size: 28rpx;
			margin-top: 20rpx;
		}
		
		.spinner {
			width: 60rpx;
			height: 60rpx;
			border: 4rpx solid rgba(255, 255, 255, 0.3);
			border-radius: 50%;
			border-top-color: #fff;
			animation: spin 1s ease-in-out infinite;
		}
		
		@keyframes spin {
			to {
				transform: rotate(360deg);
			}
		}
	}
</style>