<template>
	<view>
		<view class="container">
			<view class="name" style="display: flex;">
				<label  class="mylabel">收货人:</label>
				<input class="myinput" type="text" placeholder="请填写收款人的姓名" :value="form_data.receiverName" @input="nameChange"/>
			</view>
			<view class="phone" style="display: flex;">
				<label class="mylabel">联系电话:</label>
				<input class="myinput" type="tel" placeholder="请填写收款人的电话" :value="form_data.receiverPhone" @input="phoneChange"/>
			</view>
			<view class="province" style="display: flex;">
				<label class="mylabel">所在省:</label>
				<input class="myinput" type="text" placeholder="XX省" :value="form_data.province" @input="provinceChange"/>
			</view>
			<view class="city" style="display: flex;">
				<label class="mylabel">所在城市:</label>
				<input class="myinput" type="text" placeholder="XX市" :value="form_data.city" @input="cityChange"/>
			</view>
			<view class="district" style="display: flex;">
				<label class="mylabel" >县/区:</label>
				<input class="myinput" type="text" placeholder="XX区/XX县" :value="form_data.district" @input="districtChange"/>
			</view>
			<view class="detail" style="display: flex;">
				<label class="mylabel">详细地址:</label>
				<textarea  style="width: 450rpx;height: 150rpx;" :value="form_data.detail" @input="detailChange"/>
			</view>
		</view>
		
		<view class="container">
			<view class="default">
				<view class="left">设置成默认地址</view>
				<view class="right">
					<switch color="#6ea3e5" style="transform:scale(0.8)" @change="defauleChange"/>
				</view>
			</view>
		</view>
		
		<view class="btn">
			<view class="btn-sub" @click="onSubmit">保存</view>
		</view>
	</view>
</template>

<script>
	import {baseUrl} from '../../common/js/utils.js'
	export default {
		data() {
			return {
 				form_data:{			// 保存表单数据
					receiverName:"",
					receiverPhone:"",
					province:"",
					city:"",
					district:"",
					detail:"",
					isDefault:0
				},
			};
		},
		onLoad(option){
			
		},
		methods:{
			
			// 新增
			onSubmit(){
				console.log(this.form_data);
				let token = uni.getStorageSync('authorization')
				uni.request({
					url: `${baseUrl}/api/user/address/add`,
					method:"POST",
					header:{
						'authorization' : token,
					},
					data:{
						receiverName:this.form_data.receiverName,
						receiverPhone: this.form_data.receiverPhone,
						province : this.form_data.province,
						city : this.form_data.city,
						district : this.form_data.district,
						detail: this.form_data.detail,
						isDefault : this.form_data.isDefault
					},
					success: (res) => {
						if(res.data.code==1){
							console.log(res.data);
							uni.navigateBack()
						}
					}
				})
			},
			
			nameChange(e){
				this.form_data.receiverName = e.detail.value;
			},
			phoneChange(e){
				this.form_data.receiverPhone = e.detail.value;
			},
			provinceChange(e){
				this.form_data.province = e.detail.value;
			},
			cityChange(e){
				this.form_data.city = e.detail.value;
			},
			districtChange(e){
				this.form_data.district = e.detail.value;
			},
			detailChange(e){
				this.form_data.detail = e.detail.value;
			},
			defauleChange(e){
				if(e.detail.value){
					this.form_data.isDefault = 1;
				}else{
					this.form_data.isDefault = 0;
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.container{
		width: 690rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
		padding: 20rpx 10rpx;
		.mylabel{
			// background-color: paleturquoise;
			width: 200rpx;
			font-size: 35rpx;
		}
		.myinput{
			height: 70rpx;
		}
		.name{
			border-bottom: 1rpx solid #e1e1e1;
			margin-bottom: 20rpx;
			height: 70rpx;
			line-height: 70rpx;
		}
		.phone{
			border-bottom: 1rpx solid #e1e1e1;
			margin-bottom: 20rpx;
			height: 70rpx;
			line-height: 70rpx;
		}
		.province{
			border-bottom: 1rpx solid #e1e1e1;
			margin-bottom: 20rpx;
			height: 70rpx;
			line-height: 70rpx;
		}
		.city{
			border-bottom: 1rpx solid #e1e1e1;
			margin-bottom: 20rpx;
			height: 70rpx;
			line-height: 70rpx;
		}
		.district{
			border-bottom: 1rpx solid #e1e1e1;
			margin-bottom: 20rpx;
			height: 70rpx;
			line-height: 70rpx;
		}
		.detail{
			
		}
		.default{
			font-size: 35rpx;
			display: flex;
			.left{
				margin-right: 300rpx;
			}
			.right{
				
			}
		}
	}
	
	.btn {
		width: 100%;
		margin-bottom: 30rpx;
		.btn-sub {
			width: 670rpx;
			margin: 80rpx auto 0;
			height: 90rpx;
			background: #6ea3e5;
			border-radius: 45rpx;
			line-height: 90rpx;
			text-align: center;
			font-size: 36rpx;
			color: #fff;
		}
	}
</style>
