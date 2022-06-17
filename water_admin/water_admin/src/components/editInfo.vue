<!--
 * @Author: Tashany
 * @Date: 2022-03-08 21:54:28
 * @LastEditors: Tashany
 * @LastEditTime: 2022-04-01 22:07:54
 * @Description: file content
 * @FilePath: \water_admin\src\components\editInfo.vue
-->
<template>
    <div>
        <div style="width: 30%; margin: 30px auto;background-color: #e4f9f5 !important;height:180px">
            <div style="float:left;width:70%;">
                <el-image style="width: 120px; height: 120px; margin-top:40px" :src="url">
                </el-image>
            </div>
            <div style="float:left;width:30%;margin-top: 50px;text-align: left;">
                <el-upload class="upload-demo" action="http://localhost:8081/file/upload" :headers="headers"
                    :on-change="handleChange" :on-success="handleUploadSuccess" :before-upload="beforeUpload">
                    <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
            </div>
        </div>
        <div style="width: 30%; margin: 15px auto;">
            <el-form ref="loginUser" label-width="100px" class="demo-dynamic" :model="loginUser">
                <el-form-item label="管理员编号">
                    <el-input v-model="loginUser.magId" disabled></el-input>
                </el-form-item>
                <el-form-item label="管理员名">
                    <el-input v-model="loginUser.magName"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <span @click="open">
                        <el-input v-model="loginUser.magPassward" type="password"></el-input>
                    </span>
                </el-form-item>
                <el-form-item label="管理员手机号">
                    <el-input v-model="loginUser.magNum"></el-input>
                </el-form-item>
                <el-form-item label="管理员邮箱">
                    <el-input v-model="loginUser.magEmail"></el-input>
                </el-form-item>
                <el-form-item label="权限等级">
                    <el-radio-group v-model="loginUser.magRoot">
                        <el-radio label="1">一级</el-radio>
                        <el-radio label="2">二级</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="联系地址">
                    <el-input v-model="loginUser.magAddress" type="textarea"></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="loginUser.magContent" type="textarea"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="updateSubmit('loginUser')">提交修改</el-button>
                    <el-button @click="reset">取消</el-button>
                </el-form-item>
            </el-form>
        </div>
        <!-- 修改密码弹出层组件 -->
        <el-dialog title="修改密码" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                class="demo-ruleForm">
                <el-form-item label="输入新密码" prop="pass">
                    <el-input type="password" v-model="rePwd.pass" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="再次输入密码" prop="checkPass">
                    <el-input type="password" v-model="rePwd.checkPass" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="changePwd">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    import {
        EventBus
    } from '../store/Bus.js'

    import {
        jsonClone
    } from '@/tools/fun.js'
    import {
        updateSubmit,
        reqgetinfo
    } from '@/api/user.js'
    export default {
        data() {
            //修改密码验证
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                headers: {
                    token: localStorage.getItem("token")
                },
                imageUrl: localStorage.getItem("magImg"),
                preLoginUser: {},
                dialogVisible: false,
                loginUser: {
                    magId: "",
                    magName: "",
                    magNum: "",
                    magAddress: "",
                    magContent: "",
                    magPassward: "",
                    magRoot: "",
                    magEmail: ''
                },
                url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
                fileList: {
                    name: '',
                    url: ''
                },
                formLabelAlign: {
                    name: '',
                    region: '',
                    type: ''
                },
                dynamicValidateForm: {
                    domains: [{
                        value: ''
                    }],
                    email: ''
                },
                rePwd: {
                    pass: '',
                    checkPass: '',
                    age: ''
                },
                rules: {
                    pass: [{
                        validator: validatePass,
                        trigger: 'blur'
                    }],
                    checkPass: [{
                        validator: validatePass2,
                        trigger: 'blur'
                    }],
                },
                ruleForm: {}
            }

        },
        mounted() {
            this.preLoginUser = jsonClone(this.loginUser)
            this.getData(localStorage.getItem("magName"))
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
                    title: '修改信息',
                    index: 'IndividualEditInfo',
                },
            ]);
        },
        methods: {
            handleChange(file, fileList) {
                // this.fileList = fileList.slice(-3);


            },
            handleUploadSuccess(res) {
                console.log(res)
                this.url = res.url;
                this.imageUrl = res.url;
                this.fileList.url = res.url;
                // this.$store.commit("setAvator", res.url);
            },
            //获取信息
            async getData(name) {
                try {
                    const res = await reqgetinfo(name)
                    this.loginUser = res
                    console.log(this.loginUser)
                    this.url = res.magImg
                } catch (e) {
                }
            },
            beforeUpload(file) {
                const isJPG = (file.type === "image/jpeg" || file.type === "image/png");
                const isLt2M = file.size / 1024 / 1024 < 10;
                if (!isJPG) {
                    this.$message.error("上传头像图片只能是 JPG 格式!");
                }
                if (!isLt2M) {
                    this.$message.error("上传头像图片大小不能超过 10MB!");
                }
                return isJPG && isLt2M;
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        alert('submit!');
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            //修改密码
            changePwd() {
                this.dialogVisible = false
                this.loginUser.magPwd = this.rePwd.pass
            },
            //打开对话框
            open() {
                this.dialogVisible = true
            },
            async updateInfo(loginUser) {
                try {
                    const res = await updateSubmit(loginUser);
                    console.log(res);
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
            },
            //提交表单
            updateSubmit(ruleForm) {
                this.$refs[ruleForm].validate((valid) => {
                    if (valid) {
                        console.log(this.loginUser)
                        this.updateInfo(this.loginUser)
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            //关闭对话框
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {});
            },
            reset() {
                this.loginUser = this.preLoginUser
                // this.$router.replace({path:'/individualEditInfo'})
            }

        },
    }
</script>

<style>
    .el-descriptions__header {
        width: 50%;
        margin: 30px auto;
        line-height: 50px;
    }

    .el-descriptions__body {
        width: 50%;
        margin: 0 auto;
    }

    .my-label {
        border: 2px rgb(162 223 212) solid !important;
        color: rgb(255, 255, 255) !important;
        background: rgb(113, 201, 206) !important;
    }

    .my-content {

        border: 2px rgb(162 223 212) solid !important;
        background: rgb(203, 241, 245);
    }
</style>