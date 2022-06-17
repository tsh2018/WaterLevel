<!--
 * @Author: Tashany
 * @Date: 2022-04-14 14:21:01
 * @LastEditors: Tashany
 * @LastEditTime: 2022-05-16 15:52:12
 * @Description: file content
 * @FilePath: \water_admin\src\components\detectPoint.vue
-->
<template>
    <div>
        <el-table style="width: 50%;margin:60px auto;" :row-class-name="waterLevel" :data="level">
            <el-table-column label="正在使用检测点数量" width="180" prop="detectPoint">
            </el-table-column>
            <el-table-column label="所有可使用检测点数量" width="180" prop="allPoint">
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
                <el-form-item label="正在使用检测点数量">
                    <el-input v-model="changeInfo.detectPoint" clearable>
                        <i slot="suffix">个</i>
                    </el-input>
                </el-form-item>
                <el-form-item label="所有可使用检测点数量">
                    <el-input v-model="changeInfo.allPoint" clearable>
                        <i slot="suffix">个</i>
                    </el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="setUpdatePoint">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {
        EventBus
    } from '../store/Bus.js'
    import {
        getPoint,
        UpdatePoint
    } from '@/api/user.js'
    export default {
        data() {
            return {
                selectInfo: {},
                centerDialogVisible: false,
                level: [],
                changeInfo: {
                    detectPoint: '',
                    allPoint: '',
                }
            }
        },
        methods: {
            // //编辑警戒水位信息
            // handleEdit() {
            //     console.log(index, row);
            // },
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
            async getInfo() {
                try {
                    const res = await getPoint();
                    console.log(res)
                    this.level.push(res.object)
                    this.changeInfo = res.object
                    console.log(this.level)
                } catch (e) {
                    console.log(e)
                }
            },
            async setUpdatePoint() {
                try {
                    console.log(this.changeInfo)
                    console.log(this.level[0].id)
                    const res = await  UpdatePoint(this.changeInfo)
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
                this.getInfo()
            }

        },
        mounted() {
            //向组件head.vue传送面包屑导航内容
            EventBus.$emit("pathInfo", [{
                    title: '首页',
                    index: ''
                },
                {
                    title: '检测点管理',
                    index: 'detect',
                },
                {
                    title: '设置检测点数量',
                    index: 'detectPoint'
                },
            ]);
            console.log(111)
            this.getInfo()
            console.log(22)
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