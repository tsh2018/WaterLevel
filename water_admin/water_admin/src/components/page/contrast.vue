<!--
 * @Author: Tashany
 * @Date: 2022-04-16 16:12:09
 * @LastEditors: Tashany
 * @LastEditTime: 2022-05-10 17:36:32
 * @Description: file content
 * @FilePath: \water_admin\src\components\page\contrast.vue
-->
<template>
    <div class="com-container">
        <div class="title" :style="comStyle">
            <span>▎实际水位高度 VS 5分钟后预测水位高度 （单位 mm）</span>
            <span class="iconfont title-icon"></span>
        </div>
        <div class="com-chart" ref="contrast">
        </div>
    </div>
</template>

<script>
    import {
        getConnect
    } from '@/api/user.js'
    export default {
        data() {
            return {
                chartInstance: null,
                // 返回服务器的数据
                allData: null,
                type: 'line',
                titleFontSize: null, //指明标题字体大小
                xData: null,
            }
        },
        created() {
            // 再组件创建完成时创建回调函数
            this.$socket.registerCallBack("contrast", this.getData)
        },
        mounted() {
            this.initChart()
            this.getData()
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
                this.chartInstance = this.$echarts.init(this.$refs.contrast, 'chalk')
                const initOption = {
                    // 设置直接坐标系坐标轴大写
                    grid: {
                        left: '5%',
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
                        trigger: 'axis',
                        formatter: '{b0}({a0}): {c0}<br />{b1}({a1}): {c1}%'
                    },
                }
                this.chartInstance.setOption(initOption)
            },
            // 获取服务器数据
            getData(ret) {
                this.allData = JSON.parse(ret)
                this.updateChart()
            },
            // 更新图表
            updateChart() {
                this.xData = this.allData.measureTime.map(item => {
                    return item.split(" ")[1]
                })
                const y1 = this.allData.waterHeight.map(item => {
                    return item
                })
                const y2 = this.allData.predictionHeight.map(item => {
                    return item
                })
                /*数组排序*/
                this.xData.sort();
                const option = {
                    //图例
                    legend: {
                        data: ['实际水位高度', '预测水位高度'],
                        top: '15%',
                        textStyle: {
                            fontSize: 10,

                        },
                    },
                    xAxis: {
                        type: 'category',
                        data: this.xData
                    },
                    yAxis: [{
                        type: 'value',
                        name: '实际水位高度',
                        show: true,
                        smooth: true,
                        showSymbol: true,
                        symbol: 'circle', //设定为实心点
                        symbolSize: 3, //设定实心点的大小
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
                        axisLine: {
                            lineStyle: {
                                color: '#5e859e',
                                width: 2
                            }
                        },
                        nameLocation: 'center',
                        nameTextStyle: {
                            fontSize: 10
                        },
                        nameGap: 25
                    }, {
                        type: 'value',
                        name: '预测水位高度',
                        smooth: true,
                        showSymbol: true,
                        symbol: 'circle', //设定为实心点
                        symbolSize: 5, //设定实心点的大小
                        markPoint: { //显示一定区域内的最大值和最小值
                            data: [{
                                    type: 'average',
                                    name: '平均值'
                                }
                            ]
                        },
                        axisLabel: {
                            show: true,
                            interval: 'auto'
                        },
                        axisLine: {
                            lineStyle: {
                                color: '#5e859e', //纵坐标轴和字体颜色
                                width: 2,
                            }
                        },
                        axisLabel: {
                            formatter: function (value) {
                                // Function formatter
                                return value + ' mm'
                            }
                        },
                        nameLocation: 'center',
                        nameTextStyle: {
                            fontSize: 10
                        },
                        nameGap: 5
                    }],

                    series: [{
                        name: '实际水位高度',
                        type: 'bar',
                        barWidth: '25%',
                        data: y1
                    }, {
                        name: '预测水位高度',
                        type: 'line',
                        smooth: true,
                        data: y2
                    }],


                    // legend:{
                    //   data:['水位变化速度']
                    // }
                }
                this.chartInstance.setOption(option)
            },
            // 屏幕大小适配 图标自适应
            screenAdapter() {
                // 得到图标容器宽度-》获取字体大小
                this.titleFontSize = (this.$refs.contrast.offsetWidth / 100) * 3.6
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
        left: 10px;
        top: 10px;
        z-index: 10;
        color: white;

        .title-icon {
            margin-left: 10px;
            cursor: pointer;
        }
    }
</style>