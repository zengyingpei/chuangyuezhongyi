<template>
	<view class="info-form">
		<view class="form-item">
			<label class="form-label">姓名</label>
			<input class="form-input" type="text" placeholder="请输入姓名"  @change="nameChange" />
		</view>

		<view class="form-item">
			<label class="form-label">电话</label>
			<input class="form-input" type="tel" placeholder="请输入电话号码" @change="phoneChange" />
		</view>

		<view class="form-item gender">
			<label class="form-label">性别</label>
			<radio-group class="form-group" @change="radioChange" > 
				<label class="radio-item">
					<radio value="男"  /> 男
				</label>
				<label class="radio-item">
					<radio value="女" /> 女
				</label>
			</radio-group>
		</view>
		
		<view class="form-item">
			<label class="form-label">身份证号</label>
			<input class="form-input" type="text" placeholder="请输入身份证号" @change="idNumberChange" />
		</view>
		
		<view class="btn">
			<view class="btn-sub" @click="onSubmit">保存</view>
		</view>
	</view>
</template>

<script >
	import {baseUrl} from '../../common/js/utils.js'
	export default {
		data() {
			return {
				name: '',
				phone: '',
				gender: '',
				idNumber: '',
			};
			
		},
		onLoad(option) {},
		methods :{
			onSubmit(){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/user/update`,
					method:'POST',
					header: {
						authorization : token
					},
					data:{
						name : this.name,
						phone: this.phone,
						sex : this.gender,
						idNumber: this.idNumber
					},
					success: (res) => {
						if(res.data.code==1){
							uni.showToast({
								duration:2000,
								title:"修改成功",
								icon:'success'
							})
							
						}else{
							
							uni.showToast({
								title:"修改失败",
								duration:2000,
								icon:'fail'
							})
						}
					},
					fail: (res) => {
						
						uni.showToast({
							title:"修改失败",
							duration:2000,
							icon:'fail'
						})
					}
				})
			},
			radioChange(e){
				this.gender=e.detail.value;
			},
			nameChange(e){
				this.name=e.detail.value;
			},
			phoneChange(e){
				this.phone=e.detail.value;
			},
			idNumberChange(e){
				this.idNumber=e.detail.value;
			}
		}
	};
	
	let name 
</script>

<style lang="scss">
	.info-form {
		width: 690rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);

		.form-item {
			display: flex;
			flex-direction: column;
			margin-bottom: 20rpx;
			padding-left: 20rpx;

			.form-label {
				font-size: 30rpx;
				color: #666;
				margin-top: 30rpx;
				margin-bottom: 10rpx;
				font-size: 30rpx;
			}

			.form-input {
				width: 600rpx;
				height: 60rpx;
				border: 1px solid #ccc;
				padding: 0 20rpx;
				font-size: 30rpx;
			}

			&.gender {
				flex-direction: row;
				
				.form-group{
					margin-top: 25rpx;
					margin-left: 20rpx;
					.radio-item {
						
					}
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
				background: #DF8585;
				border-radius: 45rpx;
				line-height: 90rpx;
				text-align: center;
				font-size: 36rpx;
				color: #fff;
			}
		}
	
	}
</style>

