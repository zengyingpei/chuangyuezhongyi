<template>
    <div>
        <div class="item">
            <div class="title">
                修改密码
            </div>

            <label for="password">新密码</label><br>
            <input id="password" type="text" v-model="password">
            <br><br>
            <label for="confirm_password">确认密码</label><br>
            <input id="confirm_password" type="password" v-model="confirm_password">

            <br><br>

            <div class="submit">
                <button class="submit_button" @click="update_password">
                    提交
                </button>
            </div>
        </div>

        <div class="item">
            <div class="title">
                修改头像
            </div>

            
            <input type="file" @change="onFileChange">

            <br><br>

            <!-- <div class="submit">
                <button class="submit_button" @click="update_avatar">
                    提交
                </button>
            </div> -->
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

.item{
    padding-left: 2vw;
    padding-bottom: 3vh;
}

.title{
    font-size: 3vh;
    margin-bottom: 2vh;
}



</style>
