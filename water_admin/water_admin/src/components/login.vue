<!--
 * @Author: Tashany
 * @Date: 2022-03-16 15:17:38
 * @LastEditors: Tashany
 * @LastEditTime: 2022-04-01 21:22:03
 * @Description: file content
 * @FilePath: \water_admin\src\components\login.vue
-->
<template>
	<div>
		<!-- 登录 -->
		<section class="w3l-forms-23">
			<div class="forms23-block-hny">
				<div class="wrapper">
					<h1> Login Form</h1>
					<div class="d-grid forms23-grids">
						<div class="form23">
							<div class="main-bg">
								<h6 class="sec-one">John Doe</h6>
								<div class="speci-login first-look">
									<img src="../assets/user.png" alt="" class="img-responsive">
								</div>
							</div>
							<div class="bottom-content">
								<form name="login">
									<input type="text" v-model="magName" id="magName" class="input-form"
										placeholder="Your UserName" required="required" />
									<input type="password" id="magPassword" class="input-form" v-model="magPassward"
										placeholder="Your Password" required="required" />
								</form>
								<button type="submit" class="loginhny-btn btn" @click="getLogin()">Login</button>
								<p>Not a member yet? <a href="#" @click="getRegister">Register</a></p>
							</div>
						</div>
					</div>
					<div class="w3l-copy-right text-center">
						<p>© 20202 Tashany Login Form. All rights CUIT | TASHANY Design by</p>
					</div>
				</div>
			</div>
		</section>
		<!-- 注册对话框 -->
		<el-dialog title="注册" :visible.sync="dialogFormVisible" width="30%">
			<el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
				class="demo-ruleForm">
				<el-form-item label="用户名" prop="magName">
					<el-input type="text" v-model="ruleForm.magName" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="magPassward">
					<el-input type="password" v-model="ruleForm.magPassward" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="确认密码" prop="checkPass">
					<el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="邮箱" prop="magEmail">
					<el-input type="email" v-model="ruleForm.magEmail" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="电话号码" prop="magNum">
					<el-input type="text" v-model.number="ruleForm.magNum"></el-input>
				</el-form-item>
				<el-form-item label="权限等级">
					<el-radio-group v-model="ruleForm.magRoot" @change="test">
						<el-radio v-model="radio" label="1">一级</el-radio>
						<el-radio v-model="radio" label="2">二级</el-radio>
					</el-radio-group>
				</el-form-item>

				<!-- <div slot="footer" class="dialog-footer"> -->
				<el-button @click="resetForm('ruleForm')">重 置</el-button>
				<el-button type="primary" @click="submitForm('ruleForm')">提 交</el-button>
				<!-- </div> -->
			</el-form>
		</el-dialog>
	</div>
</template>
<script>
	import {
		reqgetLogin,
		reqgetRegister
	} from '@/api/login.js'
	import {
		setCookie,
		getCookie,
		delCookie
	} from '@/tools/cookie.js';
	import router from '../router';
	export default {
		data() {
			// 验证手机号
			var checkNum = (rule, value, callback) => {
				var reg = /^[1]([3-9])[0-9]{9}$/
				if (!reg.test(value)) {
					return callback(new Error('请输入正确手机号'));
				} else {
					callback();
				}
			}
			// 验证用户名
			var checkName = (rule, value, callback) => {
				var reg =  /^([\u4e00-\u9fa5]{2,4})|([A-Za-z0-9_]{4,16})|([a-zA-Z0-9_\u4e00-\u9fa5]{3,16})$/;
				console.log(reg.test(value))
				if (!reg.test(value)) {
					return callback(new Error('管理员名要求4-16位字母,数字,汉字,下划线'));
				} else {
					callback();
				}
			};
			// 验证密码
			var validatePass = (rule, value, callback) => {
				var reg = /^\w{6,16}$/
				if (!reg.test(value)) {
					callback(new Error('请输入6-16位密码'));
				} else {
					if (this.ruleForm.checkPass !== '') {
						this.$refs.ruleForm.validateField('checkPass');
					}
					callback();
				}
			};
			// 再次输入密码
			var validatePass2 = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请再次输入密码'));
				} else if (value !== this.ruleForm.magPassward) {
					callback(new Error('两次输入密码不一致!'));
				} else {
					callback();
				}
			};
			return {
				magName: '',
				magPassward: '',
				// 单选默认
				radio: 1,
				// 对话框
				dialogFormVisible: false,
				//表单内容
				ruleForm: {
					magPassward: '',
					checkPass: '',
					magName: '',
					magEmail: '',
					magNum: '',
					magRoot: ''
				},
				// 校验规则
				rules: {
					magPassward: [{
						validator: validatePass,
						trigger: ['blur']
					}],
					checkPass: [{
						validator: validatePass2,
						trigger: 'blur'
					}],
					magName: [{
						validator: checkName,
						trigger: 'blur'
					}],
					magEmail: [{
							required: true,
							message: '请输入邮箱地址',
							trigger: 'blur'
						},
						{
							type: 'email',
							message: '请输入正确的邮箱地址',
							trigger: ['blur', 'change']
						}
					],
					magNum: [{
						validator: checkNum,
						trigger: 'blur'
					}],
					magRoot: [{
						required: true,
						message: '请选择权限等级',
						trigger: 'change'
					}]
				}
			};
		},
		methods: {
			test() {
				console.log(this.ruleForm.root)
			},
			//登录
			async getLogin() {
				try {
					const res = await reqgetLogin(this.magName, this.magPassward)
					console.log(res)
					// 将头像和名字传到cookie
					setCookie('magImg', res.data.magImg)
					setCookie('magName', res.data.magName)
					localStorage.setItem('magImg', res.data.magImg);
					localStorage.setItem('magName', res.data.magName);
					localStorage.setItem('token', res.data.token);
					console.log(res)
					this.$router.push("/home")
				} catch (e) {}
			},
			//注册
			async Register() {
				try {
					const res = await reqgetRegister(this.ruleForm)
					return res;
				} catch (e) {}
				a
			},
			// 表单提交注册
			submitForm(formName) {
				this.$refs[formName].validate((valid) => {
					console.log(formName)
					if (valid) {
						var res = this.Register().then((res) => {
							console.log(res)
							if (res.code === '200') {
								this.$message({
									message: res.msg,
									type: 'success'
								});
								this.$router.push("/")
							} else if (res.code === '501') {
								this.$message({
									message: res.msg,
									type: 'error'
								});
							} else {
								this.$message({
									message: res.msg,
									type: 'warning'
								});
							}
						});

					} else {
						console.log('error submit!!');
						return false;
					}
				});
				this.dialogFormVisible = false;
			},
			// 重置表单
			resetForm(formName) {
				this.$refs[formName].resetFields();
			},
			getRegister() {
				this.dialogFormVisible = true
			}
		}
	}
</script>
<style scoped>
	@import url("../CSS/style.css");
</style>