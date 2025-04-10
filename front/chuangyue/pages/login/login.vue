<template>
	<view class="containar">
		<view class="avatarUrl">
			<button type="balanced" open-type="chooseAvatar" @chooseavatar="onChooseavatar">
				<image :src="avatarUrl" class="refreshIcon"></image>
			</button>
		</view>
		<view class="userName">
			<text>昵称：</text>
			<input type="nickname" class="weui-input" :value="userName" @blur="bindblur" placeholder="请输入昵称"
				@input="bindinput" />
		</view>

		<view class="btn">
			<view class="btn-sub" @click="onSubmit">保存</view>
		</view>
		
		
	</view>
</template>
<script>
	import {baseUrl} from '../../common/js/utils.js'
	import store from '@/store/index.js'
	
	export default {
		data() {
			return {
				avatarUrl: '',
				userName: '',
				code: ''
			};
		},
		onLoad(option) {},
		methods: {
			
			bindblur(e) {
				this.userName = e.detail.value; // 获取微信昵称
				
			},
			bindinput(e) {
				this.userName = e.detail.value; // 获取微信昵称
			},
			onChooseavatar(e) {
				let self = this;
				let {
					avatarUrl
				} = e.detail;
				this.avatarUrl = avatarUrl
			},
			
			onSubmit() {
				let that=this;
				uni.login({
					success: (res) => {
						that.code=res.code;
						console.log("res ", that.code );
						uni.request({
							url: `${baseUrl}/api/user/user/login`,
							method: 'POST',
							data:{
								code : that.code
							},
							success: (res) => {
								if(res.data.code == 1){		//如果返回的数据code为1标识成功
									console.log("成功 ",res.data)
									// vuex状态管理
									store.commit('updateToken',res.data.data.token);
									store.commit('updateId', res.data.data.id);
									store.commit('updateAvatar',this.avatarUrl);
									store.commit('updateUsername',this.userName);
									store.commit('updateIsLogin',1)
									
									//将token存到本地
									uni.setStorageSync('authorization', res.data.data.token);
									uni.showToast({
										duration:1000,
										icon:'success',
										title:"登录成功"
									})
									uni.switchTab({
										url:'/pages/index/index'
									})
								}else{						//失败
									console.log("失败 ", res.data.message)
								}
							},
							fail: (res) => {
								console.log("登录失败",res.errMsg);
							}
						})
					}
				});
				
			},
		}
	}
</script>
<style lang="scss" scoped>
	.containar {
		.avatarUrl {
			padding: 80rpx 0 40rpx;
			background: #fff;

			button {
				background: #fff;
				line-height: 80rpx;
				height: auto;
				border: none !important;
				width: auto;
				// padding: 20rpx 30rpx;
				margin: 0;
				display: flex;
				border: none;
				justify-content: center;
				align-items: center;

				&::after {
					border: none;
				}

				.refreshIcon {
					width: 160rpx;
					height: 160rpx;
					border-radius: 50%;
					background-color: #ccc;
				}

				.jt {
					width: 14rpx;
					height: 28rpx;
				}
			}
		}

		.userName {
			background: #fff;
			padding: 20rpx 30rpx 80rpx;
			display: flex;
			align-items: center;
			justify-content: center;

			.weui-input {
				padding-left: 60rpx;
			}
		}

		.btn {
			width: 100%;
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
