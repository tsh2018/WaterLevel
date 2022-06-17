<!--
 * @Author: Tashany
 * @Date: 2022-04-18 14:46:30
 * @LastEditors: Tashany
 * @LastEditTime: 2022-04-24 17:46:19
 * @Description: file content
 * @FilePath: \water_admin\src\components\page\map.vue
-->
<template>
    <div class="com-container" @dblclick="revertMap">
        <div class="com-chart" ref="map">
        </div>
    </div>
</template>

<script>
    import axios from 'axios'
    import {
        getProvinceMapInfo
    } from '@/tools/map_utils.js'
    import {
       getConnect
    } from '@/api/user.js'
    export default {
        data() {
            return {
                chartInstance: null,
                // 返回服务器的数据
                allData: null,
                titleFontSize: null, //指明标题字体大小
            }
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
            async initChart() {
                this.chartInstance = this.$echarts.init(this.$refs.map, 'chalk')
                // 获取地图矢量数据
                const ret = await axios.get('http://localhost:8082/static/map/china.json')
                // console.log(ret)
                // 注册地图
                this.$echarts.registerMap('china', ret.data)
                const initOption = {
                    title: {
                        text: '▎检测点分布',
                        left: 20,
                        top: 20
                    },
                    geo: {
                        type: 'map',
                        map: 'china',
                        itemStyle: {
                            areaColor: 'rgb(0,59,81)',
                            borderColor: '#fff'
                        }
                    },
                    legend: {
                        left: '5%',
                        bottom: '5%',
                        orient: 'vartical',
                        itemHeight: 12,
                        textStyle: {
                            fontSize: 12,
                        },
                    }
                }
                this.chartInstance.setOption(initOption)
                // 点击监听事件
                this.chartInstance.on('click', async arg => {
                    // console.log(arg)
                    // arg.name得到所点击的省份，是中文
                    // 判断当前点击省的地图矢量数据是否缓存
                    const provinceInfo = getProvinceMapInfo(arg.name)
                        // 获取该省份矢量数据
                        const ret = await axios.get('http://localhost:8082' + provinceInfo.path)
                        this.$echarts.registerMap(provinceInfo.key, ret.data)
                   
                    const changeOption = {
                        geo: {
                            map: provinceInfo.key,

                        }
                    }
                    this.chartInstance.setOption(changeOption)
                })
            },
            // 获取服务器数据
            getData() {
                this.updateChart()
            },
            // 更新图表
            updateChart() {
                this.allData = [{
                        name: '武汉',
                        location: [
                            ['114.31',
                                '30.59'
                            ]
                        ],
                        value: [
                            '地铁三号线',
                            '地铁一号线'
                        ]
                    },
                    {
                        name: '成都',
                        location: [
                            ['103.93',
                                '30.58'
                            ]
                        ],
                        value: [
                            '地铁三号线',
                            '地铁一号线'
                        ]
                    }
                ]
                const seriesArr = this.allData.map(item => {
                    // return的对象代表的是一个类别下的所有散点数据
                    // 在地图中显示散点的数据，需要增加一个配置 coordinateSystem
                    return {
                        type: 'effectScatter',
                        name: item.name,
                        data: item.location,
                        coordinateSystem: 'geo',
                        rippleEffect: {
                            scale: 3,
                            brushType: 'stroke'
                        }
                    }
                })
                const option = {
                    series: seriesArr,
                }
                this.chartInstance.setOption(option)
            },
            // 屏幕大小适配 图标自适应
            screenAdapter() {
                // 得到图标容器宽度-》获取字体大小
                this.titleFontSize = (this.$refs.map.offsetWidth / 100) * 3.6
                const adapterOption = {
                    title: {
                        textStyle: {
                            fontSize: this.titleFontSize
                        }
                    },
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
            },
            // 回到中国地图
            revertMap() {
                const revertOption = {
                    geo: {
                        map: 'china'
                    }
                }
                this.chartInstance.setOption(revertOption)
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