<!--
 * @Author: Tashany
 * @Date: 2022-03-07 13:57:59
 * @LastEditors: Tashany
 * @LastEditTime: 2022-05-19 09:29:20
 * @Description: file content
 * @FilePath: \water_admin\src\components\home.vue
-->
<template>
  <div id="home">
    <el-container>
      <el-aside width="200px" id="left_menu">
        <el-col :span="12" class="el-col-12">
          <el-radio-group v-model="isCollapse" style="margin-top: -44px;" id="change_tag">
            <el-radio-button :label="false">展开</el-radio-button>
            <el-radio-button :label="true">收起</el-radio-button>
          </el-radio-group>
          <el-menu default-active="1-4-1" class="el-menu-vertical-demo" @select="handleOpen" router @close="handleClose"
            :collapse="isCollapse" id="change_menu">
            <template v-for="(value,key) in asideInfo">
              <template v-if="value.subs">
                <el-submenu :index="'/'+value.index" :key="key">
                  <template slot="title">
                    <i class="el-icon-location"></i>
                    <span slot="title">{{ value.title }}</span>
                  </template>
                  <template v-for="(info,p) in value.subs">
                    <el-menu-item :index="'/'+info.index" :key="p">
                      {{ info.title }}
                    </el-menu-item>
                  </template>
                </el-submenu>
              </template>
              <template v-else>
                <el-menu-item :index="'/'+value.index" :key="key">
                  <i :class="value.icon"></i>
                  <span slot="title">{{ value.title }}</span>
                </el-menu-item>
              </template>
            </template>
          </el-menu>
        </el-col>
      </el-aside>
      <totalhead></totalhead>
    </el-container>
  </div>
</template>

<script>
  import totalhead from './head.vue';

  export default {
    components: {
      totalhead
    },
    data() {
      return {
        isCollapse: false,
        asideInfo: [{
            title: '首页',
            index: 'home'
          }, {
            title: '个人管理',
            index: 'Individual',
            subs: [{
                title: '查看信息',
                index: 'IndividualMyInfo',
              },
              {
                title: '信息修改',
                index: 'IndividualEditInfo'
              }
            ]
          },
          {
            title: '人员管理',
            index: 'Person',
            subs: [{
                title: '查看管理员',
                index: 'PersonCheckManager'
              },
              // {
              //   title: '查看用户',
              //   index: 'PersonCheckUser'
              // }
            ]
          }, {
            title: '水位警戒管理',
            index: 'dataAlarmManager',
            subs: [
              {
                title: '设置警戒水位',
                index: 'dataAlarmSet'
              },
              {
                title: '设置警戒预警时间',
                index: 'dataAlarmTime'
              },
              {
                title: '更改警戒次数',
                index: 'dataEditAlarmTimes'
              },
              {
                title: '查看当前警戒等级',
                index: 'dataEditNowAlarmLevel'
              }
            ]
          }, {
            title: '大屏数据初始化',
            index: 'init',
            subs: [{
              title: '设置初始化数据',
              index: 'editInitData'
            }]
          }, {
            title: '检测点数量管理',
            index: 'detect',
            subs: [{
              title:'设置检测点数量',
              index: 'detectPoint'
            }]
          }
        ]
      }
    },
    mounted() {
      var p = document.getElementById("change_tag")
      var z = document.getElementById("left_menu")
      var q = document.getElementById("change_menu")
      // 设置侧边框宽度 使其收缩
      z.style.width = p.style.width
      console.log(p.clientWidth);
      console.log(q.clientWidth);
      console.log(z.clientWidth);

    },


    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);

        // this.$router.push({path:keyPath})
      },
      handleClose(key, keyPath) {


        console.log(key, keyPath);
      }
    }
  }
</script>

<style lang="less">
  @import "../CSS/HelloWord.css";
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
</style>