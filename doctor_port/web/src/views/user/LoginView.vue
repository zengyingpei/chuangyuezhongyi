<template>
  <ContentField>
    <div class="row justify-content-md-center">
      <div class="col-3">
        <form @submit.prevent="login">
          <div class="mb-3">
            <label for="username" class="form-lable">账号</label>
            <input
              v-model="name"
              type="text"
              class="form-control"
              id="username"
              placeholder="用户名"
            />
          </div>
          <div class="mb-3">
            <label for="password" class="form-lable">密码</label>
            <input
              v-model="password"
              type="password"
              class="form-control"
              id="password"
              placeholder="密码"
            />
          </div>
          <div class="error_message">{{ error_message }}</div>
          <button type="submit" class="btn btn-primary">登入</button>
          <!-- 类型要改成submit，提交表单-->
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

<style>
button {
  width: 100%;
}

.error_message {
  color: red;
}
</style>
