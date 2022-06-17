<!--
 * @Author: Tashany
 * @Date: 2022-04-06 15:59:45
 * @LastEditors: Tashany
 * @LastEditTime: 2022-05-09 21:28:54
 * @Description: file content
 * @FilePath: \water_admin\src\views\index.vue
-->
<template>
  <div class="screen-container">
    <header class="screen-header">
      <div>
        <img alt="">
      </div>
      <span class="title">水 位 态 势 系 统</span>
      <div class="title-right">
        <img class="qiehuan">
        <a class="datetime" href="http://localhost:8082/home" style="color:white;text-decoration:none">后台管理</a>
      </div>
    </header>
    <dv-decoration-2 style="width:100%;height:5px;" />
    <div class="screen-body">
      <section class="screen-left">
        <dv-border-box-8 :reverse="true" id="left-top">
          <!-- 销量趋势图表 -->
          <speed ></speed>
        </dv-border-box-8>
        <dv-decoration-4 :reverse="true" style="width:100%;height:5px;" :color="[ 'rgb(47,111,136)']" />
        <dv-border-box-8 id="left-center">
          <!-- 销量趋势图表 -->
          <acceleration :list = list></acceleration>
        </dv-border-box-8>
        <dv-decoration-4 :reverse="true" style="width:100%;height:5px;" :color="[ 'rgb(47,111,136)']" />
        <div id="left-bottom">
          <alarmInfo :list = list></alarmInfo>
        </div>
        <dv-decoration-4 :reverse="true" style="width:100%;height:5px;" :color="[ 'rgb(47,111,136)']" />
      </section>
      <section class="screen-middle">
        <div id="middle-top">
          <!-- 地图 -->
          <map_ref :list = list></map_ref>
        </div>
        <dv-decoration-4 :reverse="true" style="width:100%;height:5px;" :color="[ 'rgb(47,111,136)']" />
        <div id="middle-bottom">
          <dataInfo :list = list></dataInfo>
        </div>
        <dv-decoration-4 :reverse="true" style="width:100%;height:5px;" :color="[ 'rgb(47,111,136)']" />
      </section>
      <section class="screen-right">
        <div id="right-top">
          <div id="right-top-left">
            <!-- 当前水位高度 -->
            <height :list = list></height>
          </div>
          <div id="right-top-right">
            <!-- 当前水位速度 -->
            <waterSpeed :list = list></waterSpeed>
          </div>
        </div>
        <dv-decoration-4 :reverse="true" style="width:100%;height:5px;" :color="[ 'rgb(47,111,136)']" />
        <div id="right-center">
          <!-- 实际水位 VS 预测水位 -->
          <contrast :list = list></contrast>
        </div>
        <dv-decoration-4 :reverse="true" style="width:100%;height:5px;" :color="[ 'rgb(47,111,136)']" />
        <div id="right-bottom">
          <!-- 警戒时间预计图 -->
          <alarmTime :list = list></alarmTime>
        </div>
        <dv-decoration-4 :reverse="true" style="width:100%;height:5px;" :color="[ 'rgb(47,111,136)']" />
      </section>
    </div>
  </div>
</template>

<script>
  import speed from '@/components/page/speed.vue'
  import acceleration from '@/components/page/acceleration.vue'
  import alarmInfo from '@/components/page/alarmInfo.vue'
  import dataInfo from '@/components/page/dataInfo.vue'
  import height from '@/components/page/height.vue'
  import waterSpeed from '@/components/page/waterSpeed.vue'
  import contrast from '@/components/page/contrast'
  import alarmTime from '@/components/page/alarmTime'
  import map_ref from '@/components/page/map'
  export default {

    components: {
      speed,
      acceleration,
      alarmInfo,
      dataInfo,
      height,
      waterSpeed,
      contrast,
      alarmTime,
      map_ref
    },
    data(){
      return{
        list:'hss',
      }},
    
    mounted(){
      //this.getConnect();
     
    },
    
    methods: {
      getConnect() {
        var ws = new WebSocket('ws://localhost:8081/test') //创建对象，进行连接

        ws.onopen = function () { //连接成功回调方法
          ws.send("连接成功....."); 
          console.log("连接成功.....")
        };
        ws.onclose = () => {
          console.log("连接失败.....")
        }
        ws.onmessage = function (e) {
          console.log('接收到来自服务器的消息：');
          // var temp = JSON.parse(e.data)
          this.$store.commit("setList", e.data);   
          //完成通信后关闭WebSocket连接
          // ws.close();
        //console.log(temp)
          // return temp;
        }
      }
    }

  }
</script>

<style lang="less" scoped>
  // 全屏样式的定义
  .fullscreen {
    position: fixed !important;
    top: 0 !important;
    left: 0 !important;
    width: 100% !important;
    height: 100% !important;
    margin: 0 !important;
    z-index: 100;
  }

  .screen-container {
    width: 100%;
    height: 100%;
    padding: 0 20px;
    background-color: #161522;
    color: #fff;
    box-sizing: border-box;
  }

  .screen-header {
    width: 100%;
    height: 64px;
    font-size: 20px;
    position: relative;

    >div {
      img {
        width: 100%;
      }
    }

    .title {
      position: absolute;
      left: 50%;
      top: 50%;
      font-size: 20px;
      transform: translate(-50%, -50%);
    }

    .title-right {
      display: flex;
      align-items: center;
      position: absolute;
      right: 0px;
      top: 50%;
      transform: translateY(-80%);
    }

    .qiehuan {
      width: 28px;
      height: 10%;
      cursor: pointer;
    }

    .datetime {
      font-size: 15px;
      margin-left: 10px;
    }

    .logo {
      position: absolute;
      left: 0px;
      top: 50%;
      transform: translateY(-80%);

      img {
        height: 35px;
        width: 128px;
      }
    }
  }

  .screen-body {
    width: 100%;
    height: 90%;
    display: flex;
    margin-top: 10px;

    .screen-left {
      height: 100%;
      width: 27.6%;

      #left-top {
        height: 26%;
        position: relative;
        border-left: 2px solid rgb(109, 37, 8);
        border-right: 2px solid rgb(109, 37, 8);
        border-top: 1px solid rgba(83, 82, 82, 0.588);
        border-bottom: 1px solid rgba(83, 82, 82, 0.588);
      }

      #left-center {
        height: 26%;
        position: relative;
        margin-top: 15px;
        border-bottom: 1px solid rgba(83, 82, 82, 0.588);
        border-left: 2px solid rgb(109, 37, 8);
        border-right: 2px solid rgb(109, 37, 8);
        border-top: 1px solid rgba(83, 82, 82, 0.588);
        border-bottom: 1px solid rgba(83, 82, 82, 0.588);
      }

      #left-bottom {
        height: 38%;
        margin-top: 15px;
        position: relative;
        border-bottom: 1px solid rgba(83, 82, 82, 0.588);
        border-left: 2px solid rgb(109, 37, 8);
        border-right: 2px solid rgb(109, 37, 8);
        border-top: 1px solid rgba(83, 82, 82, 0.588);
        border-bottom: 1px solid rgba(83, 82, 82, 0.588);
      }
    }

    .screen-middle {
      height: 100%;
      width: 41.5%;
      margin-left: 1.6%;
      margin-right: 1.6%;

      #middle-top {
        width: 100%;
        height: 56%;
        position: relative;
      }

      #middle-bottom {
        margin-top: 25px;
        width: 100%;
        height: 28%;
        margin: 5px 5px;
        position: relative;
      }
    }

    .screen-right {
      height: 90%;
      width: 27.6%;

      #right-top {
        height: 26%;
        position: relative;
        margin-top: 15px;

        #right-top-left {
          height: 100%;
          width: 50%;
          position: relative;
          float: left;
        }

        #right-top-right {
          height: 100%;
          width: 50%;
          position: relative;
          float: left;
        }
      }

      #right-center {
        height: 26%;
        position: relative;
        margin-top: 15px;

      }

      #right-bottom {
        height: 38%;
        margin-top: 15px;
        position: relative;

      }
    }
  }

  .resize {
    position: absolute;
    right: 20px;
    top: 20px;
    cursor: pointer;
  }
</style>