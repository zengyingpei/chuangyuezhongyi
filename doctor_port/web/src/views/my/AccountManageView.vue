<template>
    <div class="account-manage-container">
        <div class="item">
            <div class="title">
                修改密码
            </div>

            <div class="form-group">
                <label for="password">新密码</label>
                <input id="password" type="text" v-model="password" class="form-input">
            </div>
            
            <div class="form-group">
                <label for="confirm_password">确认密码</label>
                <input id="confirm_password" type="password" v-model="confirm_password" class="form-input">
            </div>

            <div class="submit">
                <button class="submit-button" @click="update_password">
                    提交
                </button>
            </div>
        </div>

        <div class="item">
            <div class="title">
                修改头像
            </div>
            <div class="avatar-upload">
                <input type="file" id="avatar-input" @change="onFileChange" class="file-input" accept="image/*">
                <label for="avatar-input" class="file-label">选择图片</label>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue';
import $ from 'jquery';
import {baseUrl} from '../../common/util.js'

  export default {
    setup:()=> {

        let password = ref('');
        let confirm_password = ref('');
      
        // update pwd
        const update_password=()=>{
            $.ajax({
                url: `${baseUrl}/api/doctor/doctor/updatepassword`,
                type: "POST",
                headers:{
                    authorization: localStorage.getItem("token"),
                },
                dataType: "json",
                contentType: "application/json",
                data:JSON.stringify({
                    password: password.value,
                    confirmPassword: confirm_password.value
                }),
                success:(res)=>{
                    if(res.code==1){
                        console.log(res);
                    }
                }
            })
        }

        // upload avatar
        const onFileChange=(e)=>{
            const file = e.target.files[0];
            if(!file){
                return;
            }
            const formData = new FormData();
            formData.append('file', file);
            $.ajax({
                url:`${baseUrl}/api/doctor/doctor/updateAvatar`,
                type: "POST",
                headers:{
                    authorization: localStorage.getItem("token"),
                },
                contentType: false,
                processData:false,
                data: formData,
                success:(res)=>{
                    if(res.code==1){
                        console.log(res);
                    }
                }
            })
        }
      
        return{
            password,
            confirm_password,
            update_password,
            onFileChange
        };
    },
  };
</script>

<style scoped>
.account-manage-container {
    width: 100%;
    max-width: 100%;  /* 修改这里，移除最大宽度限制 */
    margin: 20px auto;
    padding: 20px;
    box-sizing: border-box;
}

.item {
    width: 100%;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    padding: 20px;
    margin-bottom: 20px;
    box-sizing: border-box;
    border: 1px solid #eaeaea;
}

.title {
    font-size: 18px;
    font-weight: bold;
    color: #333;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
}

.form-group {
    margin-bottom: 15px;
    width: 100%;
}

.form-group label {
    display: block;
    margin-bottom: 8px;
    font-weight: 500;
    color: #555;
}

.form-input {
    width: 70%;  
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 14px;
    box-sizing: border-box;
    transition: border-color 0.3s;
}

.form-input:focus {
    border-color: #4a90e2;
    outline: none;
    box-shadow: 0 0 0 2px rgba(74, 144, 226, 0.2);
}

.submit {
    margin-top: 20px;
    text-align: left;
}

.submit-button {
    background-color: #4a90e2;
    color: white;
    border: none;
    border-radius: 4px;
    padding: 10px 20px;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.3s;
    width: 120px;  
}

.submit-button:hover {
    background-color: #3a7bc8;
}

.avatar-upload {
    display: flex;
    align-items: center;
    margin-top: 10px;
}

.file-input {
    display: none;
}

.file-label {
    display: inline-block;
    background-color: #f0f0f0;
    color: #333;
    padding: 10px 15px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
    border: 1px solid #ddd;
}

.file-label:hover {
    background-color: #e0e0e0;
}

/* 响应式设计 */
@media (max-width: 768px) {
    .account-manage-container {
        padding: 10px;
    }
    
    .item {
        padding: 15px;
    }
    
    .submit {
        text-align: center;
    }
    
    .submit-button {
        width: 100%;
    }
}
</style>
