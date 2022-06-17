<!--
 * @Author: Tashany
 * @Date: 2022-03-08 21:56:33
 * @LastEditors: Tashany
 * @LastEditTime: 2022-04-01 16:30:10
 * @Description: file content
 * @FilePath: \water_admin\src\components\editNowAlarmLevel.vue
-->
<template>
    <div>
        <el-descriptions title="当前警戒等级" direction="horizontal" :column="1" border style="width:50%;margin:100px auto">
            <el-descriptions-item label="当前警戒等级">{{ nowLevel.alarmLevel}}</el-descriptions-item>
            <el-descriptions-item label="警戒高度 /毫米">{{ nowLevel.alarmHigh}}</el-descriptions-item>
            <el-descriptions-item label="预测要求警戒时间 /秒">{{ nowLevel.alarmTime}}</el-descriptions-item>
            <el-descriptions-item label="警戒总次数 /次">{{ nowLevel.alarmSum}}</el-descriptions-item>
        </el-descriptions>
    </div>
</template>

<script>
    import {
        EventBus
    } from '../store/Bus.js'
    import {
        getNowAlarm
    } from '@/api/user.js'
    export default {
        data() {
            return {
                nowLevel: {
                     alarmLevel: '',
                    alarmHigh: '',
                    alarmSum:'',
                    alarmTime:''
                }
            }
        },
        methods:{
            async getNowAlarm(){
                try {
                    const res = await getNowAlarm();
                    console.log(res);
                    this.nowLevel = res.object.alarmWater;
                } catch (error) {
                    
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
                    title: '水位警戒管理',
                    index: 'dataAlarmManager',
                },
                {
                    title: '查看当前警戒等级',
                    index: 'dataEditNowAlarmLevelt'
                },
            ]);
            this.getNowAlarm()
        },
    }
</script>

<style>
    .my-label {
        background: #d4f1c5;
    }
</style>