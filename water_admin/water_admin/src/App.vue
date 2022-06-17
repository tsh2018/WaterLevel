<!--
 * @Author: Tashany
 * @Date: 2022-03-07 13:57:59
 * @LastEditors: Tashany
 * @LastEditTime: 2022-03-08 18:09:48
 * @Description: file content
 * @FilePath: \water_admin\src\App.vue
-->
<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<style lang="less">
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  #nav {

    a {
      font-weight: bold;
      color: #2c3e50;

      &.router-link-exact-active {
        color: #42b983;
      }
    }
  }
</style>