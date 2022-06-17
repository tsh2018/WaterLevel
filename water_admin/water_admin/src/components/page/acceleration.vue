<!--
 * @Author: Tashany
 * @Date: 2022-04-13 17:19:24
 * @LastEditors: Tashany
 * @LastEditTime: 2022-04-26 15:24:23
 * @Description: file content
 * @FilePath: \water_admin\src\components\page\acceleration.vue
-->
<template>
  <div class="com-container">
      <div class="title" :style="comStyle">
        <span>▎水位加速度变化</span>
        <span class="iconfont title-icon"></span>
      </div>
      <div class="com-chart" ref="waterAcceleration"> 
      </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        chartInstance: null,
        // 返回服务器的数据
        allData: null,
        currentPage: 1, //当前页数
        totalPage: 0, //总页数
        timerId: null, // 定时器标识,
        showChoice: false,
        type: 'line',
        titleFontSize: null,//指明标题字体大小
        xData:null,
        yData:null
      }
    },
     created() {
      // 再组件创建完成时创建回调函数
      this.$socket.registerCallBack('acceleration',this.getData)
    },
    mounted() {
      this.initChart()
      // this.getData()
      window.addEventListener('resize', this.screenAdapter)
      this.screenAdapter()
      // getConnect()
    },
    destroyed() {
      window.removeEventListener('resize', this.screenAdapter)
       // 销毁时取消回调函数
      this.$socket.unRegisterCallBack(this.getData)
    },
    computed: {
      // 设置给标题的样式
      comStyle() {
        return {
          fontSize: this.titleFontSize + 'px'
        }

      }
    },
    methods: {
      // 初始化echartInstance对象
      initChart() {
        this.chartInstance = this.$echarts.init(this.$refs.waterAcceleration, 'chalk')
        const initOption = {
          // 设置直接坐标系坐标轴大写
          grid: {
            left: '3%',
            top: '35%',
            right: '4%',
            bottom: '1%',
            containLabel: true
          },
          xAxis: {
            // 取消间隙
            type: 'category',
            // boundaryGap: false
          },
          yAxis: {

          },
          tooltip: {
            trigger: 'axis'
          }
        }
        this.chartInstance.setOption(initOption)
      },
      // 获取服务器数据
      getData(ret) {
        console.log(333)
        this.allData = JSON.parse(ret)
        this.updateChart()
      },
      // 更新图表
      updateChart() {
         this.yData = this.allData.waterAcceleration.map(item =>{
          return item
        })
         this.xData = this.allData.measureTime.map(item => {
          return item.split(" ")[1]
        })
        /*数组排序*/
        this.xData.sort();
        const option = {
          //图例名
          legend: {
            data: ['水位加速度']
          },
          xAxis: {
            type: 'category',
            data: this.xData,
            axisLabel: {
              interval: 0,
              fontSize:'10'
            }
          },
          yAxis: {
            type: 'value',
            areaStyle: {}
          },

          series: [{
            type: this.type,
            data: this.yData,
            smooth: true,
            showSymbol: true,
            symbol: 'circle', //设定为实心点
            symbolSize: 5, //设定实心点的大小
            markPoint: { //显示一定区域内的最大值和最小值
              data: [{
                  type: 'max',
                  name: '最大值'
                },
                {
                  type: 'min',
                  name: '最小值'
                }
              ]
            },
            areaStyle: {
              // 从上往下的渐变颜色
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#00E2FF'
                },
                {
                  offset: 1,
                  color: '#0063BF'
                }
              ])
            },

          }],
        }
        this.chartInstance.setOption(option)
      },
      // 屏幕大小适配 图标自适应
      screenAdapter() {
        // 得到图标容器宽度-》获取字体大小
        this.titleFontSize = (this.$refs.waterAcceleration.offsetWidth / 100) * 3.6
        const adapterOption = {
          legend: {
            itemWidth: this.titleFontSize,
            itemHeight: this.titleFontSize,
            itemGap: this.titleFontSize,
            textStyle: {
              fontSize: this.titleFontSize / 2
            }
          }
        }
        this.chartInstance.setOption(adapterOption)
        this.chartInstance.resize()
      }
    }
  }

  
</script>

<style lang="less" scoped>
  .title {
    position: absolute;
    left: 20px;
    top: 20px;
    z-index: 10;
    color: white;

    .title-icon {
      margin-left: 10px;
      cursor: pointer;
    }
  }
</style>