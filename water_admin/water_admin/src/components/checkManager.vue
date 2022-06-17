<!--
 * @Author: Tashany
 * @Date: 2022-03-08 21:54:39
 * @LastEditors: Tashany
 * @LastEditTime: 2022-05-06 15:26:16
 * @Description: file content
 * @FilePath: \water_admin\src\components\checkManager.vue
-->
<template>
    <div>
        <template>
            <el-table :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)" style="width: 100%;margin-top:30px" stripe>
                <el-table-column label="管理员编号" width="180" align="center">
                    <template slot-scope="scope">
                        <i class="el-icon-user"></i>
                        <span style="margin-left: 10px">{{ scope.row.magId }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="姓名" width="150" align="center">
                    <template slot-scope="scope">
                        <i class="el-icon-user"></i>
                        <span style="margin-left: 10px">{{ scope.row.magName }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="手机号码" width="180" align="center">
                    <template slot-scope="scope">
                        <i class="el-icon-mobile-phone"></i>
                        <span style="margin-left: 10px">{{ scope.row.magNum}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="邮箱" width="180" align="center">
                    <template slot-scope="scope">
                        <i class="el-icon-mobile-Emaile"></i>
                        <span style="margin-left: 10px">{{ scope.row.magEmail}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="联系地址" width="300" align="center">
                    <template slot-scope="scope">
                        <i class="el-icon-office-building"></i>
                        <span style="margin-left: 10px">{{ scope.row.magAddress }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="备注" width="180" align="center">
                    <template slot-scope="scope">
                        <i class="el-icon-tickets"></i>
                        <span style="margin-left: 10px">{{ scope.row.magContent }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </template>
        <el-pagination @size-change="handleSizeChange" 
   @current-change="handleCurrentChange"
   :current-page="currentPage" 
   :page-sizes="[1,5,10,20]" 
   :page-size="pageSize" 
   layout="total, sizes, prev, pager, next, jumper" 
   :total="tableData.length">
        </el-pagination>
    </div>
</template>

<script>
    import {
        EventBus
    } from '../store/Bus.js'
    import {
        getManagerList,
        deleteManager
    } from '@/api/user.js'
    export default {
        data() {
            return {
                tableData: [{
                    magId: "",
                    magName: "",
                    magPassward: "",
                    magEmail: "",
                    magNum: "",
                    magImg: "",
                    magRoot: '',
                    magsAdress: "",
                    magRemark: "",
                }],
                currentPage: 1, // 当前页码
                total: 20, // 总条数
                pageSize: 5 // 每页的数据条数
            }
        },
        methods: {
            handleEdit(index, row) {

                console.log(index, row);
            },

            //删除指定行
            async handleDelete(index, row) {
                // //删除表格信息
                // if (this.tableData.splice(index, 1)) {
                //    
                // } else {
                //     this.$message({
                //         message: '删除失败！',
                //         type: 'warning'
                //     });
                // }
                try {
                    const res = deleteManager(row.magId)
                    console.log(res)
                    this.$message({
                        message: '删除成功！',
                        type: 'success'
                    });
                    this.getManageList()
                } catch (error) {
                    this.$message({
                        message: '删除失败！',
                        type: 'warning'
                    });
                }
                getManagerList()


            },
            async getManageList() {
                try {
                    const res = await getManagerList()
                    console.log(res.object)
                    this.tableData = res.object

                } catch (e) {
                    console.log(e)

                }

            },
            //每页条数改变时触发 选择一页显示多少行
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.currentPage = 1;
                this.pageSize = val;
            },
            //当前页改变时触发 跳转其他页
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage = val;
            }
        },
        mounted() {
            //向组件head.vue传送面包屑导航内容
            EventBus.$emit("pathInfo", [{
                    title: '首页',
                    index: ''
                },
                {
                    title: '个人管理',
                    index: 'IndividualMyInfo'
                },
                {
                    title: '查看管理员',
                    index: 'IndividualMyInfo',
                },
            ]);
            this.getManageList()

        },
    }
</script>

<style>

</style>