<!--
 * @Author: Tashany
 * @Date: 2022-03-08 17:36:28
 * @LastEditors: Tashany
 * @LastEditTime: 2022-03-19 21:49:24
 * @Description: file content
 * @FilePath: \water_admin\src\components\head.vue
-->
<template>
  <el-container>
    <el-header>
      <div class="head">
        <div class="left" >
          <el-breadcrumb separator-class="el-icon-arrow-right" style="line-height:60px">
            <template v-for="info in pathInfo">
              <el-breadcrumb-item  :key="info.title" :to="{ path: '/' + info.index}">{{ info.title }}</el-breadcrumb-item>
            </template>
          </el-breadcrumb>
        </div>
        <div class="right">
            <span style="line-height:60px">Welcome,{{ loginUser.magName}}</span>
          <div class = "right-avatar">
            <el-avatar :src="loginUser.magImg"></el-avatar>
          </div>
            
          <i class="el-icon-share"></i>
        </div>
      </div>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
  import {
    EventBus
  } from '../store/Bus.js'

  export default {
    name: 'Head',
    data() {
      return {
        loginUser: {
          magName: 'Tashany',
          magImg: "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
        },
        pathInfo:[{title:'首页',index:'/'}]
      }
    },
    mounted(){
      EventBus.$on("pathInfo", (msg) => {
            // b组件接受 a发送来的消息
            this.pathInfo = msg;
        });
      // 从localStorage中获取基本信息
      this.loginUser.magName = localStorage.getItem("magName")
      this.loginUser.magImg = localStorage.getItem("magImg")
      
    }
  }
    
</script>
<style lang = "less" scoped>
.right{
    height:60px;
    display: flex;
    align-items: center;
    .right-avatar{
      margin-right: 10px;
      margin-left: 10px;
      width: 40px;
      height: 40px;
    }
}

</style>