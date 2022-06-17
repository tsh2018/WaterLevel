<!--
 * @Author: Tashany
 * @Date: 2022-03-08 21:56:45
 * @LastEditors: Tashany
 * @LastEditTime: 2022-04-01 22:09:33
 * @Description: file content
 * @FilePath: \water_admin\src\components\editInitData.vue
-->
<template>
    <div>
        <el-table  style="width: 50%;margin:60px auto;" :row-class-name="waterLevel" :data="level">
            <el-table-column label="初始水位高度 /毫米" width="180" prop="waterHeight">
            </el-table-column>
            <el-table-column  label="初始水位速度 毫米/秒" width="180" prop="waterSpeed">
            </el-table-column>
            <el-table-column  label="初始水位加速度  m/s^2 " width="180" prop="waterAcceleration">
            </el-table-column>
            <el-table-column label="操作" align="center">   
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="handleEdit(scope.$index, scope.row)">修改
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog title="修改" :visible.sync="centerDialogVisible" width="25%" center>
            <el-form :model="changeInfo">
                <el-form-item label="初始水位高度">
                    <el-input v-model="changeInfo.waterHeight" clearable>
                        <i slot="suffix">mm</i>
                    </el-input>
                </el-form-item>
                <el-form-item label="初始水位速度">
                    <el-input v-model="changeInfo.waterSpeed" clearable>
                        <i slot="suffix">mm/s</i>
                    </el-input>
                </el-form-item>
                <el-form-item label="初始水位加速度">
                    <el-input v-model="changeInfo.waterAcceleration" clearable>
                        <i slot="suffix"> m/s^2 </i>
                    </el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="setInitWaterInfo">确 定</el-button>
            </div>

        </el-dialog>
    </div>
</template>

<script>
    import {
        EventBus
    } from '../store/Bus.js'
     import {
        getLastInfo,
        setInitWaterInfo
    } from '@/api/user.js'
    export default {
        data() {
            return {
                selectInfo: {},
                centerDialogVisible: false,
                level:[],
                changeInfo:{
                    waterHeight:'',
                    waterSpeed:'',
                    waterAcceleration:''
                }
            }
        },
        methods: {
            //编辑警戒水位信息
            handleEdit() {
                console.log(index, row);
            },
            waterLevel({
                row,
                rowIndex
            }) {
                return 'success-row';
            },
            handleEdit(index, row) {
                this.centerDialogVisible = true
                this.selectInfo = this.level[index]
            },
            async getLastInfo(){
                try{
                    const res = await getLastInfo();
                    console.log(res)
                    this.level.push(res.object)
                   this.changeInfo = res.object
                   console.log(this.level)
                }catch(e){

                }
            },
            async setInitWaterInfo(){
                try {
                    console.log(this.changeInfo)
                    console.log(this.level[0].id)
                    const res = await setInitWaterInfo(this.changeInfo,this.level[0].id)
                    console.log(res)
                    this.$message({
                        message: '修改成功！',
                        type: 'success'
                    });
                } catch (error) {
                    this.$message({
                        message: '修改失败！',
                        type: 'error'
                    });
                }
                this.centerDialogVisible = false
                this.level.length = 0;
                this.getLastInfo()
            }
            
        },
        mounted() {
            //向组件head.vue传送面包屑导航内容
            EventBus.$emit("pathInfo", [{
                    title: '首页',
                    index: ''
                },
                {
                    title: '大屏数据初始化',
                    index: 'init',
                },
                {
                    itle: '设置初始化数据',
                    index: 'editInitData'
                },
            ]);
            this.getLastInfo()
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