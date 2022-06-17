<!--
 * @Author: Tashany
 * @Date: 2022-03-08 21:56:16
 * @LastEditors: Tashany
 * @LastEditTime: 2022-04-01 16:43:17
 * @Description: file content
 * @FilePath: \water_admin\src\components\editAlarmTimes.vue
-->
<template>
    <div>
        <el-table :data="level" style="width: 50%;margin:60px auto;" :row-class-name="waterLevel">
            <el-table-column prop="alarmLevel" label="警戒水位等级" >
            </el-table-column>
            <el-table-column prop="alarmSum" label="警戒总次数/次" >
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="handleEdit(scope.$index, scope.row)">修改
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog title="修改" :visible.sync="centerDialogVisible" width="40%" center>
            <form>
                <el-input v-model="selectInfo.alarmLevel" clearable style="margin-top:20px; width:30%">
                </el-input>
                <el-input placeholder="警戒总次数" v-model="selectInfo.alarmSum" clearable style="margin-left:20px; width:30%">
                    <i slot="suffix">次</i>
                </el-input>
                <span slot="footer" class="dialog-footer" style="margin-left:15px">
                    <el-button @click="centerDialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="subSum">确 定</el-button>
                </span>
            </form>
        </el-dialog>
    </div>
</template>

<script>
    import {
        EventBus
    } from '../store/Bus.js'
    import {
        getAlarmInfo,
        setAlarmSum
    } from '@/api/user.js'
    export default {
        data() {
            return {
                selectInfo: {
                    alarmLevel: '',
                    alarmSum: '',
                },
                centerDialogVisible: false,
                level: [{
                    alarmLevel: '1',
                    alarmHigh: '一级警戒水位',
                    alarmSum:'',
                    alarmTime:''
                }],
                value: ''
            }
        },
        methods: {
            waterLevel({
                row,
                rowIndex
            }) {
                if (rowIndex === 1) {
                    return 'warning-row';
                } else if (rowIndex === 3) {
                    return 'success-row';
                } else if (rowIndex === 0) {
                    return 'danger-row'
                } else {
                    return "thrid-row"
                }
            },
            handleEdit(index, row) {
                this.centerDialogVisible = true
                this.selectInfo = this.level[index]
            },
             //获取警戒信息
            async getAlarm(){
                try{
                    const res = await getAlarmInfo()
                    console.log(res)
                    this.level = res.object
                }catch(e){

                }
            },
            handleEdit(index, row) {
                this.centerDialogVisible = true
                this.selectInfo.alarmLevel = this.level[index].alarmLevel
                this.selectInfo.alarmSum = this.level[index].alarmSum
            },
            //设置警戒次数
            async setAlarmSum(){
                try{
                    console.log(this.selectInfo)
                    const res = await setAlarmSum(this.selectInfo)
                    // console.log(res)
                    this.$message({
                        message: '修改成功！',
                        type: 'success'
                    });
                    
                }catch(e){
                    this.$message({
                        message: '修改失败！',
                        type: 'warning'
                    });
                }
                this.getAlarm()
            },
            //提交
            subSum(){
                this.setAlarmSum();
                this.centerDialogVisible = false
                
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
                    title: '更改警戒次数',
                    index: 'dataEditAlarmTimes'
                },
            ]);
            this.getAlarm()
        },
    }
</script>

<style>
    .el-table .warning-row {
        background: rgb(243, 218, 171);
    }

    .el-table .success-row {
        background: #d8f0cb;
    }

    .el-table .danger-row {
        background-color: rgb(248, 167, 167);
    }

    .el-table .thrid-row {
        background-color: rgb(255, 231, 188)
    }
</style>