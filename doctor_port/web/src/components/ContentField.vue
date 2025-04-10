<template>
    <div class="container-fluid content-field">
        <div class="card">
            <div class="card-body">
                <slot></slot>
            </div>
        </div>
    </div>
</template>


<script>
    export default{
        name: "ContentField"
    }
</script>


<style scoped>
    div.content-field{
        margin-top: 1.5vh;
    }
</style>