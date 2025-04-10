<template>
	<view>
		<view class="chat">
			<view class="chat_content">
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
			<view class="chat_op">
				<view class="chat_input">
					<input class="input" type="text" v-model="input_msg" @input="onWrite"/>
				</view>
				<view class="chat_button" @click="send">
					发送
				</view>
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
				input_msg: "",	// 输入框的内容	
				contents:[],	// 聊天数据
				websocket:null,	// ws服务
				time:''			// 当前时间
			};
		},
				
		// 钩子函数
		onLoad(option){
			this.linkId = option.linkId;
			this.doctorId = option.doctorId;
			this.getHistory(option.linkId);
			this.initWebSocket();
		},
		
		
		onUnload(){
			uni.closeSocket({
				success: () => {
					console.log("连接关闭");
				}
			})		
		},
		
		// onShow(option){
		// 	this.linkId = option.linkId;
		// 	this.doctorId = option.doctorId;
		// 	this.getHistory(option.linkId);
		// },
		
		
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
			height: 1300rpx;
			overflow: auto;
			// background-color: rebeccapurple;
			
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
			// background-color: #6ea3e5;
			border-top: 1rpx solid #7d837e;
			display: flex;
			.chat_input{
				box-sizing: border-box;
				width: 500rpx;
				margin-left: 20rpx;
				margin-top: 20rpx;
				.input{
					width: 500rpx;
					border: 2rpx solid #608bae;
					height: 50rpx;
				}
			}
			.chat_button{
				border-radius: 20rpx;
				background-color: #6ea3e5;
				// border: 1rpx solid #282c35;
				width: 100rpx;
				height: 60rpx;
				color: white;
				text-align: center;
				line-height: 65rpx;
				margin-left: 30rpx;
				margin-top: 15rpx;
			}
		}
	}

</style>
