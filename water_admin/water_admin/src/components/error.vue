<!--
 * @Author: Tashany
 * @Date: 2022-03-18 16:07:16
 * @LastEditors: Tashany
 * @LastEditTime: 2022-03-18 16:14:19
 * @Description: file content
 * @FilePath: \water_admin\src\components\error.vue
-->
<template>
  
</template>

<script>
export default {
    name: 'Error',
    created(){
        let _this = this
        this.$alert('登录信息失效','提示',{
            confirmButtonText:'确定'
        }).then((response)=>{
            localStorage.removeItem("mag")
            _this.$router.replace({path:'/login'})
        })
    }
}
</script>

<style>

</style>