<template>
	<view>
		<view class="header">
			<view class="header_left">
				科室
			</view>
			<view class="header_right">
				疾病
			</view>
		</view>
		
		<view class="body">
			<view class="body_left">
				<scroll-view scroll-y="true" class="my_scroll">
					<view v-for="(item,index) in departments" :key="item.id" class="row" @click="getSickness(item.id)" :style="{color : isSelected(item.id) ? '#7ca0ec':'black'}">
						{{item.name}}
					</view>
				</scroll-view>
			</view>
			<view class="body_right">
				<scroll-view scroll-y="true" class="my_scroll">
					<view v-for="(item,index) in sickness" :key="item.id" class="row" @click="getDoctors(item.id,item.name)">
						{{item.name}}
					</view>
				</scroll-view>
			</view>
		</view>
	</view>
</template>

<script>
	import {baseUrl} from '../../common/js/utils.js'
	export default {
		data() {
			return {
				departments:[],
				sickness:[],
				nowSelectedDept:null		//用于存储当前被选中的科室索引
			};
		},
		onLoad(option)  {		//页面加载的时候执行一些函数
			this.getDatas();
			
		},
		methods:{
			getDatas (){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/department`,
					method:'GET',
					header: {
						authorization : token
					},
					success: (res) => {
						// console.log(res.data);
						if(res.data.code==1){
							this.departments=res.data.data;
							console.log(this.departments);
						}else{
							uni.showToast({
								duration:1000,
								title:"数据加载失败",
								icon:"error"
							})
						}
					}
				});
			},
			getSickness(deptId){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/sickness`,
					method:'GET',
					header: {
						authorization : token
					},
					data:{
						deptId: deptId
					},
					success: (res) => {
						if(res.data.code==1){
							this.sickness=res.data.data;
						}else{
							uni.showToast({
								duration:1000,
								title:"数据加载失败",
								icon:"error"
							})
						}
					}
				});
				this.handSelect(deptId);		//更新当前被点击的科室
			},
			handSelect(idx){
				this.nowSelectedDept=idx;
			},
			isSelected(idx){
				return this.nowSelectedDept == idx;
			},
			
			//跳转到新页面展示对应的医生
			getDoctors(sicknessId,sicknessName){
				uni.navigateTo({
					url: `/pages/doctor_list/doctor_list?sicknessId=${sicknessId}&sicknessName=${sicknessName}`
				})
			}
		}
	
	}
</script>

<style lang="scss">
	.header{
		width: 100%;
		// background-color: red;
		border-bottom: 3rpx solid black;
		display: flex;
		font-size: 44rpx;
		line-height: 100%;
		.header_left{
			padding-top: 20rpx;
			width: 50%;
			height: 60rpx;
			text-align: center;
		}
		.header_right{
			padding-top: 20rpx;
			width: 50%;
			height: 60rpx;
			text-align: center;
		}
	}
	.body{
		display: flex;
		text-align: center;
		font-size: 38rpx;
		
		.body_left{
			// background-color: green;
			width: 50%;
			.my_scroll{
				height: 1450rpx;
				.row{
					height: 100rpx;
					border: 2rpx solid #f8f9fb;
					line-height: 100rpx;
				}
			}
		}
		.body_right{
			width: 50%;
			.my_scroll{
				height: 1450rpx;
				.row{
					height: 100rpx;
					border: 2rpx solid #f8f9fb;
					line-height: 100rpx;
				}
			}
		}
	}
</style>
