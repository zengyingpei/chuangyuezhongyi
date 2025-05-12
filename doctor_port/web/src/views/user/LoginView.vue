<template>
  <ContentField>
    <div class="login-container">
      <div class="login-card">
        <h2 class="login-title">医生端登录</h2>
        <form @submit.prevent="login">
          <div class="form-group">
            <label for="username" class="form-label">账号</label>
            <input
              v-model="name"
              type="text"
              class="form-control"
              id="username"
              placeholder="请输入手机号"
            />
          </div>
          <div class="form-group">
            <label for="password" class="form-label">密码</label>
            <input
              v-model="password"
              type="password"
              class="form-control"
              id="password"
              placeholder="请输入密码"
            />
          </div>
          <div class="error-message" v-if="error_message">{{ error_message }}</div>
          <button type="submit" class="login-button">登录</button>
        </form>
      </div>
    </div>
  </ContentField>
</template>

<script>
import ContentField from "@/components/ContentField.vue";
import { useStore } from "vuex";
import { ref } from "vue";
import router from "@/router/index";
import $ from "jquery";

export default {
  name: "UserAccountLoginView",
  components: {
    ContentField,
  },
  setup: () => {
    const store = useStore();

    let name = ref("");
    let password = ref("");
    let error_message = ref("");

    let jwt_token = localStorage.getItem("token");
    if (jwt_token) {
      store.commit("updateToken", jwt_token);
      store.dispatch("getinfo", {
        success: () => {
          router.push({ name: "home" });
        },
        error: () => {},
      });
    }

    const login = () => {
      error_message.value = "";
      $.ajax({
        url: "http://localhost:8080/api/doctor/doctor/login",
        type: "post",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
          phone: name.value,
          password: password.value,
        }),
        success: (res) => {
          if (res.code == 1) {
            console.log(res);
            localStorage.setItem("token", res.data.token); //存在本地缓存中
            store.commit("updateToken", res.data.token); //更新全局的vuex中的token
            store.commit("updateUser", {
              id: res.data.id,
              photo: res.data.avatar,
              name: res.data.name,
              is_login: true,
            });
            router.push({ name: "home" }); //
          } else {
            error_message.value = res.message;
          }
        },
      });
    };

    return {
      name,
      password,
      error_message,
      login,
    };
  },
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
}

.login-card {
  width: 100%;
  max-width: 400px;
  padding: 30px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
}

.login-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.login-title {
  text-align: center;
  color: #4a90e2;
  margin-bottom: 25px;
  font-weight: 600;
}

.form-group {
  margin-bottom: 20px;
}

.form-label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #555;
}

.form-control {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
  transition: border-color 0.3s, box-shadow 0.3s;
}

.form-control:focus {
  border-color: #4a90e2;
  box-shadow: 0 0 0 3px rgba(74, 144, 226, 0.2);
  outline: none;
}

.error-message {
  color: #f44336;
  font-size: 14px;
  margin-bottom: 15px;
  padding: 8px 12px;
  background-color: #fdeaea;
  border-radius: 4px;
  border-left: 3px solid #f44336;
}

.login-button {
  width: 100%;
  padding: 12px;
  background-color: #4a90e2;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.3s;
}

.login-button:hover {
  background-color: #3a7bc8;
}

.login-button:active {
  background-color: #2d6cb5;
}
</style>
