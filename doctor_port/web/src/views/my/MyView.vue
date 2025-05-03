<template>
    <div class="body">
        <div class="aside">
            <div class="item">
                <router-link :to="{name:'myInfo'}" style="text-decoration: none">信息管理</router-link>
            </div>
            <div class="item" @click="logout()">退出登录</div>
        </div>
        <div class="main">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
    import { useRoute } from "vue-router";
    import { computed } from "vue";
    import { useStore } from "vuex";

    export default {
        setup:()=> {
        const store = useStore(); //获取全局的store对象
        const route = useRoute(); //获取当前路由只读对象
        let route_name = computed(() => route.name); //通过computed实时计算当前页面路由的字符串

        const logout=()=>{
            store.commit('logout');
            window.location.href = '/';
        }

        return{
            route_name,
            logout
        };
    },
  };
</script>

<style scoped>

.aside{
    width: 10vw;
    background-color: antiquewhite;
}
.main{
    width: 90vw;
}

.item{
    height: 7vh;
    font-size: 2vh;
    line-height: 7vh;
    text-align: center;
}

.item:hover{
    cursor: pointer;
}

</style>
