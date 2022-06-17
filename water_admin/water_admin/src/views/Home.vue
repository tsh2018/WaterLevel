<!--
 * @Author: Tashany
 * @Date: 2022-03-07 13:57:59
 * @LastEditors: Tashany
 * @LastEditTime: 2022-03-08 16:42:15
 * @Description: file content
 * @FilePath: \water_admin\src\views\Home.vue
-->
<template>
  <div class="home">
    <home msg="Welcome to Your Vue.js App"></home>
  </div>
</template>

<script>
// @ is an alias to /src
import home from '@/components/home.vue'

export default{
  name: 'Home',
  components: {
    home
  }
}
</script>
