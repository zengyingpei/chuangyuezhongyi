<template>
	<view class="doc_container">
		<view class="doc_title">精选医生</view>
		
		
		<swiper class="doc_swiper" :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000">
			<swiper-item>
				<view class="doc_swiper-item" >
					<view class="doc_content">
						<view class="doc_card">
							<view class="doc_left_img">
								<image class="doc_images" src="../../static/logo.png" mode=""></image>
							</view>
							<view class="doc_right_info">
								<view class="doc_info_top">
									<text class="doc_name">{{doc1.username}}</text>
									<text class="doc_description">{{doc1.po}}</text>
								</view>
								<view class="doc_info_bom">
									<view class="doc_info_bom_up">{{doc1.address}}</view>
									<view class="doc_info_bom_down">{{doc1.good}}</view>
								</view>
							</view>
						</view>
						
						
						<view class="doc_card">
							<view class="doc_left_img">
								<image class="doc_images" src="../../static/logo.png" mode=""></image>
							</view>
							<view class="doc_right_info">
								<view class="doc_info_top">
									<text class="doc_name">{{doc2.username}}</text>
									<text class="doc_description">{{doc2.po}}</text>
								</view>
								<view class="doc_info_bom">
									<view class="doc_info_bom_up">{{doc2.address}}</view>
									<view class="doc_info_bom_down">{{doc2.good}}</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</swiper-item>
			
			
			<swiper-item>
				<view class="swiper-item" >
					<view class="content">
						<view class="card">
							<view class="left_img">
								<image class="images" src="../../static/logo.png" mode=""></image>
							</view>
							<view class="right_info">
								<view class="info_top">
									<text class="name">{{doc3.username}}</text>
									<text class="description">{{doc3.po}}</text>
								</view>
								<view class="info_bom">
									<view class="info_bom_up">{{doc3.address}}</view>
									<view class="info_bom_down">{{doc3.good}}</view>
								</view>
							</view>
						</view>
						
						
						<view class="card">
							<view class="left_img">
								<image class="images" src="../../static/logo.png" mode=""></image>
							</view>
							<view class="right_info">
								<view class="info_top">
									<text class="name">{{doc4.username}}</text>
									<text class="description">{{doc4.po}}</text>
								</view>
								<view class="info_bom">
									<view class="info_bom_up">{{doc4.address}}</view>
									<view class="info_bom_down">{{doc4.good}}</view>
								</view>
							</view>
						</view>
						
						
						
						
					</view>
				</view>
			</swiper-item>
			
			<swiper-item>
				<view class="swiper-item" >
					<view class="content">
						<view class="card">
							<view class="left_img">
								<image class="images" src="../../static/logo.png" mode=""></image>
							</view>
							<view class="right_info">
								<view class="info_top">
									<text class="name">{{doc5.username}}</text>
									<text class="description">{{doc5.po}}</text>
								</view>
								<view class="info_bom">
									<view class="info_bom_up">{{doc5.address}}</view>
									<view class="info_bom_down">{{doc5.good}}</view>
								</view>
							</view>
						</view>
						
						
						<view class="card">
							<view class="left_img">
								<image class="images" src="../../static/logo.png" mode=""></image>
							</view>
							<view class="right_info">
								<view class="info_top">
									<text class="name">{{doc6.username}}</text>
									<text class="description">{{doc6.po}}</text>
								</view>
								<view class="info_bom">
									<view class="info_bom_up">{{doc6.address}}</view>
									<view class="info_bom_down">{{doc6.good}}</view>
								</view>
							</view>
						</view>
						
						
						
						
					</view>
				</view>
			</swiper-item>
			
			<swiper-item>
				<view class="swiper-item" >
					<view class="content">
						<view class="card">
							<view class="left_img">
								<image class="images" src="../../static/logo.png" mode=""></image>
							</view>
							<view class="right_info">
								<view class="info_top">
									<text class="name">{{doc7.username}}</text>
									<text class="description">{{doc7.po}}</text>
								</view>
								<view class="info_bom">
									<view class="info_bom_up">{{doc7.address}}</view>
									<view class="info_bom_down">{{doc7.good}}></view>
								</view>
							</view>
						</view>
						
						
						<view class="card">
							<view class="left_img">
								<image class="images" src="../../static/logo.png" mode=""></image>
							</view>
							<view class="right_info">
								<view class="info_top">
									<text class="name">{{doc8.username}}</text>
									<text class="description">{{doc8.po}}</text>
								</view>
								<view class="info_bom">
									<view class="info_bom_up">{{doc8.address}}</view>
									<view class="info_bom_down">{{doc8.good}}</view>
								</view>
							</view>
						</view>
						
						
						
						
					</view>
				</view>
			</swiper-item>
			
			<swiper-item>
				<view class="swiper-item" >
					<view class="content">
						<view class="card">
							<view class="left_img">
								<image class="images" src="../../static/logo.png" mode=""></image>
							</view>
							<view class="right_info">
								<view class="info_top">
									<text class="name">{{doc9.username}}</text>
									<text class="description">{{doc9.po}}</text>
								</view>
								<view class="info_bom">
									<view class="info_bom_up">{{doc9.address}}</view>
									<view class="info_bom_down">{{doc9.good}}</view>
								</view>
							</view>
						</view>
						
						
						<view class="card">
							<view class="left_img">
								<image class="images" src="../../static/logo.png" mode=""></image>
							</view>
							<view class="right_info">
								<view class="info_top">
									<text class="name">{{doc10.username}}</text>
									<text class="description">{{doc10.po}}</text>
								</view>
								<view class="info_bom">
									<view class="info_bom_up">{{doc10.address}}</view>
									<view class="info_bom_down">{{doc10.good}}</view>
								</view>
							</view>
						</view>
						
						
						
						
					</view>
				</view>
			</swiper-item>
			
			
		</swiper>
	</view>
	
</template>

<script>
	export default {
		name:"good-doctors",
		data() {
			return {
				doc1: {id:"1",username:"zyp",po:"主任",address:"dizhi",good:"good",photo:""},
				doc2: {id:"2",username:"zyp",po:"主任",address:"dizhi",good:"good",photo:""},
				doc3: {id:"3",username:"zyp",po:"主任",address:"dizhi",good:"good",photo:""},
				doc4: {id:"4",username:"zyp",po:"主任",address:"dizhi",good:"good",photo:""},
				doc5: {id:"5",username:"zyp",po:"主任",address:"dizhi",good:"good",photo:""},
				doc6: {id:"6",username:"zyp",po:"主任",address:"dizhi",good:"good",photo:""},
				doc7: {id:"7",username:"zyp",po:"主任",address:"dizhi",good:"good",photo:""},
				doc8: {id:"8",username:"zyp",po:"主任",address:"dizhi",good:"good",photo:""},
				doc9: {id:"9",username:"zyp",po:"主任",address:"dizhi",good:"good",photo:""},
				doc10: {id:"10",username:"zyp",po:"主任",address:"dizhi",good:"good",photo:""},
			}; 
		},
		
		methods: {
			
		}
	}
</script>

<style lang="scss">
	.doc_container{
		width: 690rpx;
		margin: 20rpx auto;
		border: 1px solid #eee;
		border-radius: 20rpx;
		box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.06);
		.doc_title{
			font-size: 50rpx;
			font-weight: bold;
			padding-top: 5rpx;
			padding-left: 10rpx;
			margin-bottom: 15rpx;
		}
		.doc_swiper{
			height: 520rpx;
		}
		.doc_swiper-item{
			.doc_content{
				// background-color: red;
				display: flex;
				flex-wrap: wrap;
				justify-content: space-between;
				align-content: space-between;
				.doc_card{
					margin: 5rpx 10rpx;
					height: 250rpx;
					width: 660rpx;
					// background-color: green;
					border-radius: 20rpx;
					display: flex;
					box-shadow: 0 0 30rpx rgba(0, 0, 0, 0.1);
					.doc_left_img{
						margin: 20rpx 20rpx;
						width: 200rpx;
						height: 200rpx;
						// border-radius: 50%;
						.doc_images{
							width: 180rpx;
							height: 180rpx;
							border-radius: 50%;
						}
					}
					.doc_right_info{
						.doc_info_top{
							width: 420rpx;
							height: 66rpx;
							overflow: hidden;
							padding-top: 20rpx;
							
							.doc_name{
								font-weight: bold;
								font-size: 50rpx;
								margin-right: 10rpx;
							}
							.doc_description{
								font-size: 28rpx;
							}
						}
						.doc_info_bom{
							margin-top: 5rpx;
							.doc_info_bom_up{
								font-size: 28rpx;
								font-style: italic;
								margin-bottom: 5rpx;
							}
						}
					}
				}
			}
		}
	}
</style>