<!--
 * @Author: Tashany
 * @Date: 2022-04-13 20:51:01
 * @LastEditors: Tashany
 * @LastEditTime: 2022-04-26 17:10:02
 * @Description: file content
 * @FilePath: \water_admin\src\components\page\alarmInfo.vue
-->
<template>
    <dv-border-box-1 class="com-container">
        <div class="com-chart" ref="alarmLevel"></div>
    </dv-border-box-1>
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
                currentPage: 1, //当前页数
                totalPage: 0, //总页数
                timerId: null, // 定时器标识,
                showChoice: false, //是否显示可选项
                type: 'line',
                titleFontSize: null //指明标题字体大小
            }
        },
        created() {
            // 再组件创建完成时创建回调函数
            this.$socket.registerCallBack("alarmInfo", this.getData)
        },
        mounted() {
            this.initChart()
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
                this.chartInstance = this.$echarts.init(this.$refs.alarmLevel, 'chalk')
                const initOption = {
                    title: {
                        text: '▎警戒次数占比',
                        left: 20,
                        top: 20
                    },
                    legend: {
                        top: '25%',
                        icon: 'circle',
                    },
                    series: [{
                        type: 'pie',
                        label: {
                            show: false
                        },
                        emphasis: {
                            label: {
                                show: true,
                                formatter: arg => {
                                    // return data.info
                                    // console.log(arg)
                                    return arg.dataalarmInfo
                                }
                            }
                        },
                        roseType: 'area',
                        radius: [30, 90],
                        center: ['50%', '70%'],
                        itemStyle: {
                            borderRadius: 5
                        }
                    }, ],
                    tooltip: {
                        show: true,
                        trigger: 'item',
                        formatter: "{b}: {c}次 (占比{d}%)" //模板变量有 {a}、{b}、{c}、{d}，分别表示系列名，数据名，数据值，百分比。
                       
                    }
                }
                this.chartInstance.setOption(initOption)
            },
            getData(ret) {
                this.allData = JSON.parse(ret)
                this.updateChart()

            },
            // 更新图表
            updateChart() {
                var seriesdata = this.allData.alarmWater.map(item => {
                    return {
                        value: item.alarmSum,
                        name: item.alarmLevel + "级警戒"
                    }
                })
                // var seriesdata = [{
                //     value: '10',
                //     name: '一级警戒',
                //     alarmInfo: '一级警戒水位高度200mm'
                // }, {
                //     value: '20',
                //     name: '二级警戒',
                //     alarmInfo: '二级警戒水位高度200mm'
                // }, {
                //     value: '30',
                //     name: '三级警戒',
                //     alarmInfo: '二级警戒水位高度200mm'
                // }, {
                //     value: '50',
                //     name: '四级警戒',
                //     alarmInfo: '三级警戒水位高度200mm'
                // }]
                const option = {
                    series: [{
                        data: seriesdata,
                        itemStyle:{
                            normal:{
                                color:function(params){
                                    var colorList = ["red", "orange","rgb(19,112,251)","green"]
                                    return colorList[params.dataIndex]
                                }
                            }
                        }
                    }]
                }


                this.chartInstance.setOption(option)
            },
            // 屏幕大小适配 图标自适应
            screenAdapter() {
                // 得到图标容器宽度-》获取字体大小
                this.titleFontSize = (this.$refs.alarmLevel.offsetWidth / 100) * 3.6
                const adapterOption = {
                    title: {
                        textStyle: {
                            fontSize: this.titleFontSize
                        }
                    },
                    //图例大小
                    length: {
                        itemWidth: this.titleFontSize / 2,
                        itemHeigh: this.titleFontSize / 2,
                        itemGap: this.titleFontSize / 2,
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