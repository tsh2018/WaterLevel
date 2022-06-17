<!--
 * @Author: Tashany
 * @Date: 2022-03-08 21:54:12
 * @LastEditors: Tashany
 * @LastEditTime: 2022-04-01 21:23:54
 * @Description: file content
 * @FilePath: \water_admin\src\components\myInfo.vue
-->
<template>
  <div id="myinfo">
    <div>
      <el-image style="width: 170px; height: 170px; margin-top:40px" :src="url" :preview-src-list="srcList"></el-image>
    </div>
    <el-descriptions :column="1" border>
      <el-descriptions-item label-class-name="my-label" content-class-name="my-content">
        <template slot="label">
          <i class="el-icon-user"></i>
          管理员编号
        </template>
        {{ loginUser.magId}}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label" content-class-name="my-content">
        <template slot="label">
          <i class="el-icon-user"></i>
          管理员名
        </template>
        {{ loginUser.magName}}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label" content-class-name="my-content">
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          手机号
        </template>
        {{ loginUser.magNum}}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label" content-class-name="my-content">
        <template slot="label">
          <i class="el-icon-mobile-Email"></i>
          邮箱号
        </template>
        {{ loginUser.magEmail}}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label" content-class-name="my-content">
        <template slot="label">
          <i class="el-icon-office-building"></i>
          联系地址
        </template>
        {{ loginUser.magAddress}}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label" content-class-name="my-content">
        <template slot="label">
          <i class="el-icon-tickets"></i>
          备注
        </template>
        {{ loginUser.magContent}}
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
  import {
    EventBus
  } from '../store/Bus.js'
import {reqgetinfo} from '@/api/user.js'
  export default {
    data() {
      return {
        loginUser: {
          magId: "",
          magName: "",
          magPassward: "",
          magEmail: "",
          magNum: "",
          magImg: "",
          magRoot: '',
          magsAdress: "",
          magRemark: "",
        },
        url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        srcList: [
          'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg',
          'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg'
        ]
      }
    },
    methods: {
      async getData(name){
        try{
          console.log(name)
          const res  = await reqgetinfo(name)
          this.loginUser = res
          this.url = res.magImg
          console.log(res)
        }catch(e){          
        }
      }
    },
    mounted() {
      //向组件head.vue传送面包屑导航内容
      EventBus.$emit("pathInfo", [{
            title: '首页',
            index: ''
          },
          {
            title: '个人管理',
            index: 'IndividualMyInfo'
          },
          {
            title: '查看信息',
            index: 'IndividualMyInfo',
          },
        ]);
        this.getData(localStorage.getItem("magName"))
    },
   
  }
</script>

<style>
  .el-descriptions__header {
    width: 50%;
    margin: 30px auto;
    line-height: 50px;
  }

  .el-descriptions__body {
    width: 50%;
    margin: 0 auto;
  }

  .my-label {
    border: 2px rgb(162 223 212) solid !important;
    color: rgb(255, 255, 255) !important;
    background: rgb(113, 201, 206) !important;
  }

  .my-content {
    border: 2px rgb(162 223 212) solid !important;
    background: rgb(203, 241, 245);
  }
</style>