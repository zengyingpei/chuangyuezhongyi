<template>
	<view>
		<view class="row" v-for="(item, index) in futureDays" :key="item.id">
			<view class="left">
				<text class="date">{{item.date}}</text> &nbsp;
				 <text class="time">{{item.slot}}</text>
				 <text class="available" style="margin-left: 100rpx; font-size: 30rpx;color: #ff7043;">剩余 {{item.surplus}}</text>
			</view>
			<view class="right">
				<!-- 修改按钮部分 -->
				<view class="button"
					  :class="{'button-disabled': item.surplus <= 0}"
					  @click="goSubmit(item.id)"
					  :disabled="item.surplus <= 0">
					{{ item.surplus > 0 ? '预约' : '已满' }}
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
				docId:'',		//接收上一个页面传过来的医生的id 
				futureDays:[]	// 存储所有可以预约的信息
			};
		},
		onLoad(option){
			this.docId= option.docId;
			console.log("传过来的医生id ",option.docId);
			this.getAppointment(option.docId);
		},
		methods:{
			getAppointment(docId){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/appointment/list`,
					method:"GET",
					header:{
						'authorization' : token,
					},
					data:{
						docId : docId
					},
					success: (res) => {
						// 成功
						if(res.data.code == 1){
							console.log(res.data);
							this.futureDays=res.data.data;
						}
					}
				})
			},
			goSubmit(scheduleId){
				// 首先检查当前项的剩余人数是否大于0
				const currentItem = this.futureDays.find(item => item.id === scheduleId);
				if (!currentItem || currentItem.surplus <= 0) {
					uni.showToast({
						title: '该时段已约满',
						icon: 'none',
						duration: 1500
					});
					return; // 如果已满，则不发送请求
				}

				let token =uni.getStorageSync('authorization');
				uni.showLoading({ // 添加loading提示
					title: '正在预约...'
				});
				uni.request({
					url: `${baseUrl}/api/user/appointment/add`,
					method:"POST",
					header:{
						'authorization' : token,
					},
					data:{
						scheduleId : scheduleId,
						doctorId : this.docId,
						status: 1 // status: 1 可能表示预约状态，根据后端定义确认
					},
					success: (res) => {
						uni.hideLoading(); // 隐藏loading
						if(res.data.code == 1 ){
							console.log(res.data);
							// 预约成功提示
							uni.showToast({
								title: '预约成功',
								icon: 'success',
								duration: 2000
							});

							// 更新当前预约项的剩余人数
							const index = this.futureDays.findIndex(item => item.id === scheduleId);
							if (index !== -1) {
								// 使用Vue.$set或直接修改确保响应式更新
								this.$set(this.futureDays[index], 'surplus', this.futureDays[index].surplus - 1);
							}
						} else {
							// 预约失败提示
							uni.showToast({
								title: res.data.message || '预约失败，请重试',
								icon: 'none',
								duration: 2000
							});
						}
					},
					fail: (err) => { // 添加fail回调处理网络错误
						uni.hideLoading();
						uni.showToast({
							title: '网络请求失败',
							icon: 'none',
							duration: 2000
						});
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.row{
		width: 690rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
		display: flex;
		.left{
			line-height: 80rpx;
			height: 80rpx;
			width: 530rpx;
			box-sizing: border-box;
			padding-left: 20rpx;
		}
		.right{
			width: 170rpx;
			.button{
				margin-top: 10rpx;
				background-color: #6bce72;
				height: 60rpx;
				width: 150rpx;
				border-radius: 20rpx;
				border: 2rpx solid #6bce72;
				line-height: 60rpx;
				color: #fff; // 修改文字颜色为白色，更清晰
				font-size: 35rpx;
				text-align: center;
				transition: background-color 0.3s, border-color 0.3s; // 添加过渡效果

				// 添加禁用状态样式
				&.button-disabled {
					background-color: #cccccc; // 灰色背景
					border-color: #cccccc; // 灰色边框
					color: #999999; // 深灰色文字
					cursor: not-allowed; // 鼠标样式改为不可用
				}

				// 可以添加一个激活状态的样式（可选）
				&:active:not(.button-disabled) {
					background-color: #58b45f;
					border-color: #58b45f;
				}
			}
		}
	}
</style>
