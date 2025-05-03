<template>
	<view>
		<view class="chat">
			<view class="chat_content">
				<!-- 问诊结束时显示电子病历卡片 -->
				<view class="case-card" v-if="state == 1 && caseData">
					<view class="case-card-header">
						<uni-icons type="info-filled" size="20" color="#7ca0ec"></uni-icons>
						<text class="card-title">医生已为您生成电子病历</text>
					</view>
					<view class="case-card-content">
						<view class="case-info-item">
							<text class="label">患者姓名:</text>
							<text class="value">{{caseData.client.name}}</text>
						</view>
						<view class="case-info-item">
							<text class="label">诊断结果:</text>
							<text class="value diagnosis">{{caseData.diagnosis}}</text>
						</view>
						<view class="case-info-item">
							<text class="label">就诊日期:</text>
							<text class="value">{{caseData.visitDate}}</text>
						</view>
					</view>
					<view class="case-card-footer">
						<button class="view-case-btn" @click="viewCaseDetail">查看完整病历</button>
					</view>
				</view>
				
				<view class="chat_item" v-for="(item, index) in contents" :key="index">
					<view class="chat_item_time_left" v-if="item.sender == doctorId">
						{{item.time}}
					</view>
					
					<view class="chat_item_time_right" v-else>
						{{item.time}}
					</view>
					
					<view class="chat_item_box_left" v-if="item.sender == doctorId">
						<view class="chat_item_content_left">
							{{item.content}}
						</view>
					</view>
					
					<view class="chat_item_box_right" v-else>
						<view class="chat_item_content_right">
							{{item.content}}
						</view>
					</view>
				</view>
			</view>
			
			<!-- 问诊已结束时禁用输入框 -->
			<view class="chat_op" v-if="state != 1">
				<view class="chat_input">
					<input class="input" type="text" v-model="input_msg" @input="onWrite"/>
				</view>
				<view class="chat_button" @click="send">
					发送
				</view>
			</view>
			
			<!-- 问诊已结束时显示提示 -->
			<view class="chat_ended" v-else>
				<text>此次问诊已结束，无法继续发送消息</text>
			</view>
		</view>
	</view>
</template>

<script>
	import {baseUrl} from '../../common/js/utils.js'
	import {wsUrl} from '../../common/js/utils.js'
	import store from '@/store/index.js';	
	
	export default {
		data() {
			return {
				linkId: null, 	// 存储当前聊天的id
				doctorId: null,	// 存储医生的id
				state: null,    // 存储当前问诊关系的状态
				input_msg: "",	// 输入框的内容	
				contents:[],	// 聊天数据
				websocket:null,	// ws服务
				time:'',		// 当前时间
				caseData: null  // 电子病历数据
			};
		},
				
		// 钩子函数
		onLoad(option){
			this.linkId = option.linkId;
			this.doctorId = option.doctorId;
			this.state = option.state;
			this.getHistory(option.linkId);
			
			// 如果问诊已结束，获取电子病历数据
			if(this.state == 1) {
				this.getCaseData(option.linkId);
			} else {
				this.initWebSocket();
			}
		},
		
		
		onUnload(){
			if(this.websocket) {
				uni.closeSocket({
					success: () => {
						console.log("连接关闭");
					}
				});
			}
		},
		
		methods:{
			// 获取历史聊天记录
			getHistory(linkId){
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/chat/one`,
					method:'GET',
					header:{
						'authorization' : token,
					},
					data:{
						chatlinkId : linkId
					},
					success: (res) => {
						if(res.data.code==1){
							console.log(res.data);
							// 将获取到的数据存下
							this.contents = res.data.data;
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
			
			// 获取电子病历数据
			getCaseData(chatLinkId) {
				let token = uni.getStorageSync('authorization');
				uni.showLoading({
					title: '加载病历数据...'
				});
				
				uni.request({
					url: `${baseUrl}/api/user/case/chatlink`,
					method: 'GET',
					header: {
						'authorization': token
					},
					data: {
						chatLinkId: chatLinkId
					},
					success: (res) => {
						uni.hideLoading();
						if(res.data.code == 1) {
							console.log('病历数据:', res.data.data);
							this.caseData = res.data.data;
						} else {
							uni.showToast({
								title: '获取病历失败',
								icon: 'none',
								duration: 2000
							});
						}
					},
					fail: () => {
						uni.hideLoading();
						uni.showToast({
							title: '网络请求失败',
							icon: 'none',
							duration: 2000
						});
					}
				});
			},
			
			// 查看病历详情
			viewCaseDetail() {
				if(!this.caseData) return;
				
				// 将病历数据编码后传递给病历详情页
				const caseDataStr = encodeURIComponent(JSON.stringify(this.caseData));
				uni.navigateTo({
					url: `/pages/cases/case_detail?caseData=${caseDataStr}`
				});
			},
			
			// 更新输入框的内容
			onWrite(e){
				this.input_msg = e.detail.value;
				console.log(this.input_msg);
			},
			
			// 初始化连接websocket
			initWebSocket(){
				
				let sid = "user"  + "|"+ this.$store.state.id;
				console.log("sid=", sid);
				
				this.websocket = uni.connectSocket({
					url: `${wsUrl}/ws/inquiry/`+ sid,
					success:()=>{
						console.log("连接成功");
					}
				});
				
				// 建立连接的回调函数
				this.websocket.onOpen((res)=>{
					console.log("连接成功", res);
				});
				
				// 接收到消息的回调函数
				this.websocket.onMessage((res)=>{
					console.log(res);
					this.getTime();
					this.contents.push({
						receiver: this.$store.state.id,
						sender : this.doctorId,
						content: res.data,
						time: this.time
					});
				});
				
			},
			
			// 发送消息
			send(){
				let msg = this.input_msg;
				if(msg==="") return;
				console.log(msg);
				this.websocket.send({
					data:  `${this.doctorId}` + "|" + msg,
					success:()=>{
						this.getTime();
						this.contents.push({
							receiver: this.doctorId,
							sender: this.$store.state.id,
							content: msg,
							time : this.time
						});
						
						this.input_msg = "";
					}
				});
				
				// 将消息，存储到数据库中，方面后面查询
				let token=uni.getStorageSync('authorization');
				uni.request({
					url: `${baseUrl}/api/user/chat/add`,
					method:'POST',
					header:{
						'authorization' : token,
					},
					data:{
						linkId : this.linkId,
						receiver: this.doctorId,
						content: msg,
						state:0
					},
					success: (res) => {
						if(res.data.code==1){
							console.log(res.data);
							
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
			
			// 获取当前时间的函数
			getTime(){
				var date = new Date();
				//年 getFullYear()：四位数字返回年份
				var year = date.getFullYear(); //getFullYear()代替getYear()
				//月 getMonth()：0 ~ 11
				var month = date.getMonth() + 1;
				//日 getDate()：(1 ~ 31)
				var day = date.getDate();
				//时 getHours()：(0 ~ 23)
				var hour = date.getHours();
				//分 getMinutes()： (0 ~ 59)
				var minute = date.getMinutes();
				//秒 getSeconds()：(0 ~ 59)
				var second = date.getSeconds();
				var time = year + '-' + this.addZero(month) + '-' + this.addZero(day) + ' ' + this.addZero(hour) + ":" +this.addZero(minute) + ':' + this.addZero(second);
					this.time = time
			},
			//小于10的拼接上0字符串
			addZero(s) {
				return s < 10 ? ('0' + s) : s;
			},			
		}
	}
</script>

<style lang="scss" scoped>
	.chat{
		.chat_content{
			height: calc(100vh - 100rpx);
			overflow-y: auto;
			padding-bottom: 120rpx;
			box-sizing: border-box;
			// background-color: rebeccapurple;
			
			// 电子病历卡片样式
			.case-card {
				width: 690rpx;
				margin: 20rpx auto;
				background-color: #f8f9fe;
				border-radius: 16rpx;
				box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.08);
				overflow: hidden;
				border: 1rpx solid #e0e7ff;
				
				.case-card-header {
					background-color: #e6efff;
					padding: 20rpx;
					display: flex;
					align-items: center;
					
					.card-title {
						font-size: 30rpx;
						font-weight: bold;
						color: #333;
						margin-left: 10rpx;
					}
				}
				
				.case-card-content {
					padding: 20rpx;
					
					.case-info-item {
						margin-bottom: 16rpx;
						display: flex;
						
						.label {
							font-size: 28rpx;
							color: #666;
							width: 160rpx;
							flex-shrink: 0;
						}
						
						.value {
							font-size: 28rpx;
							color: #333;
							flex: 1;
							
							&.diagnosis {
								color: #ff7043;
								font-weight: 500;
							}
						}
					}
				}
				
				.case-card-footer {
					padding: 0 20rpx 20rpx;
					display: flex;
					justify-content: flex-end;
					
					.view-case-btn {
						background-color: #7ca0ec;
						color: #fff;
						font-size: 28rpx;
						padding: 10rpx 30rpx;
						border-radius: 30rpx;
						border: none;
						line-height: 1.5;
					}
				}
			}
			
			.chat_item{
				width: 690rpx;
				margin: 20rpx;
				// display: flex;
				.chat_item_time_left{
					font-size: 20rpx;
					color: #6d746e;
					// width: max-content;
					// text-align: right;
					
				}
				.chat_item_box_left{
					.chat_item_content_left{
						font-size: 38rpx;
						border-radius: 20rpx;
						border: 1rpx solid white;
						box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
						width: max-content;
						padding: 5rpx 10rpx;
					}
				}
				
				
				.chat_item_time_right{
					font-size: 20rpx;
					color: #6d746e;
					// width: max-content;
					text-align: right;
					
				}
				.chat_item_box_right{
					display: flex;
					justify-content: flex-end;
					
				}
				
				.chat_item_content_right{
					font-size: 38rpx;
					border-radius: 20rpx;
					border: 1rpx solid white;
					box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
					width: max-content;
					background-color:#6ea3e5 ;
					padding: 5rpx 10rpx;
				}
			}
		}
		
		.chat_op{
			height: 100rpx;
			border-top: 1rpx solid #e0e0e0;
			display: flex;
			align-items: center;
			background-color: #f9f9f9;
			padding: 0 20rpx;
			position: fixed;
			bottom: 0;
			left: 0;
			right: 0;
			z-index: 10;
			
			.chat_input{
				flex: 1;
				box-sizing: border-box;
				margin: 10rpx 20rpx 10rpx 0;
				
				.input{
					width: 100%;
					height: 70rpx;
					border: 2rpx solid #e0e0e0;
					border-radius: 35rpx;
					padding: 0 30rpx;
					font-size: 28rpx;
					background-color: #fff;
					box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.05);
					transition: all 0.3s;
					
					&:focus {
						border-color: #7ca0ec;
						box-shadow: 0 2rpx 12rpx rgba(124, 160, 236, 0.2);
					}
				}
			}
			
			.chat_button{
				width: 120rpx;
				height: 70rpx;
				background: linear-gradient(135deg, #7ca0ec, #6ea3e5);
				color: white;
				text-align: center;
				line-height: 70rpx;
				border-radius: 35rpx;
				font-size: 28rpx;
				font-weight: 500;
				box-shadow: 0 4rpx 12rpx rgba(110, 163, 229, 0.3);
				transition: all 0.2s;
				
				&:active {
					transform: scale(0.95);
					box-shadow: 0 2rpx 6rpx rgba(110, 163, 229, 0.2);
				}
			}
		}
		
		// 问诊结束提示样式
		.chat_ended {
			height: 100rpx;
			display: flex;
			justify-content: center;
			align-items: center;
			border-top: 1rpx solid #e0e0e0;
			background-color: #f5f5f5;
			position: fixed;
			bottom: 0;
			left: 0;
			right: 0;
			z-index: 10;
			
			text {
				font-size: 28rpx;
				color: #999;
			}
		}
	}
</style>
