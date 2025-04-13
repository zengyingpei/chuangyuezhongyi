const BASE_URL="http://localhost:8080";

export function get(
		url,
		data={},
		method="GET",
		header={}
	){	

	url =BASE_URL+url;	
	
	return new Promise((resolve,reject)=>{		
		uni.request({
			url,
			data,
			method,
			header,
			success:res=>{		
				if(res.data.errCode===0){
					resolve(res.data)
				}else{
					console.log(res)
					uni.showToast({
						title:res.data.errMsg,
						icon:"none"
					})
					reject(res.data)
				}							
				
			},
			fail:err=>{
				
				console.log(err)
				uni.showToast({
					title: err.errMsg,
					icon: "none",
					});
					reject(err)
			}
		})
	})
}
export function post(
		url,
		data={},
		method="POST",
		header={}
	){	

	url =BASE_URL+url;	
	
	return new Promise((resolve,reject)=>{		
		uni.request({
			url,
			data,
			method,
			header,
			success:res=>{		
				if(res.data.errCode===0){
					resolve(res.data)
				}else{
					uni.showToast({
						title:res.data.message,
						icon:"none"
					})
					reject(res.data)
					
				}							
				
			},
			fail:err=>{
				reject(err)
				console.log(err)
				uni.showToast({
					title: err.errMsg,
					icon: "none",
					});
			}
		})
	})
}
