<!--
 * @Author: Tashany
 * @Date: 2022-04-13 22:46:55
 * @LastEditors: Tashany
 * @LastEditTime: 2022-05-09 19:24:23
 * @Description: file content
 * @FilePath: \water_admin\src\components\page\dataInfo.vue
-->

<template>
    <dv-border-box-8>
        <div class="com-container">
            <dv-border-box-8 class="title">
                <!-- <dv-loading></dv-loading> -->
                <dv-scroll-board :config="config"></dv-scroll-board>
            </dv-border-box-8>
        </div>
    </dv-border-box-8>

</template>

<script>
    import {
        getConnect
    } from '@/api/user.js'
    export default {
        data() {
            return {
                allData: null,
                config: {
                    header: ['时间', '水流速度(mm/s)', '水流加速度', '预警等级'],
                    data: [
                        ['12:33:51', '10', '210', '一'],
                        ['12:33:51', '10', '210', '一'],
                        ['12:33:51', '10', '210', '一'],
                        ['12:33:51', '10', '210', '一'],
                        ['12:33:51', '10', '210', '一'],
                        ['12:33:51', '10', '210', '一'],
                        ['12:33:51', '10', '210', '一'],
                        ['12:33:51', '10', '210', '一']
                    ]
                }

            }
        },
        created() {
            // 再组件创建完成时创建回调函数
            this.$socket.registerCallBack("dataInfo", this.getData) 
        },
        destroyed() {
            // 销毁时取消回调函数
            this.$socket.unRegisterCallBack(this.getData)
        },
        methods: {
            getData(ret) {
                this.allData = JSON.parse(ret)
                let arr = [];
                let length = this.allData.alarmLevel.length;
                for(let i = 0;i<length;i++){
                    let item = [];
                    item.push(this.allData.measureTime[i]);
                    item.push(this.allData.waterSpeed[i]);
                    item.push(this.allData.waterAcceleration[i]);
                    item.push(this.allData.alarmLevel[i]);
                    arr.push(item)
                }
                console.log('arr')
                console.log(arr)
                this.config = {
                    header: ['时间', '水流速度(mm/s)', '水流加速度', '预警等级'],
                    data: arr
                }
            },
        }
    }
</script>

<style>
    /* style="width:90%;height:90%;padding:5%" */
</style>