<template>
<div class="main">
  <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px" class="demo-ruleForm login-container">
    <h3 class="title">后台管理登录</h3>
    <el-form-item prop="account">
      <el-input type="text" v-model="ruleForm2.account" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="checkPass">
      <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-col style="height:30px;color:#409EFF">注册</el-col>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2" :loading="logining">登录</el-button>
      <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
    </el-form-item>
  </el-form>
</div>
</template>

<script>
  import { requestLogin } from '../api/api';
  import axios from 'axios';
  import getSummaryPage from './workplan/getSummaryPage.vue'
  
  //import NProgress from 'nprogress'
  export default {
    data() {
      return {
        logining: false,
        ruleForm2: {
          account: '18827551256',
          checkPass: '12345678'
        },
        rules2: {
          account: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            //{ validator: validaePass }
          ],
          checkPass: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            //{ validator: validaePass2 }
          ]
        },
        checked: true
      };
    },
    methods: {
      handleReset2() {
        this.$refs.ruleForm2.resetFields();
      },
      handleSubmit2(ev) {
        var _this = this;
        this.$refs.ruleForm2.validate((valid) => {
          if (valid) {
            //_this.$router.replace('/table');
            this.logining = true;
            //NProgress.start();
            setTimeout(() => {
            
                    var loginParams = { tPhone: this.ruleForm2.account, tPass: this.ruleForm2.checkPass };
             requestLogin(loginParams).then(data => {
              this.logining = false;
               //NProgress.done();
               console.log(data);
              if (data.status == 404) {
                this.$message({
                  message: "用户不存在",
                  type: 'error'
                });
              }else if(data.status == 403){
                  this.$message({
                    message: "用户非管理员，请使用管理员账号登录",
                    type: 'error'
                  });
              }else if(data.status == 1){
                  this.$message({
                    message: "用户名或密码错误",
                    type: 'error'
                  });
              } else if(data.status == 0){
                //console.log(data.menuList);
                this.$message({
                    message: "登录成功",
                    type: 'success'
                  });
                sessionStorage.clear();
                sessionStorage.setItem('token', data.token);
                sessionStorage.setItem('realname', data.name);
                this.$router.push({ path: '/' });
              }
            }).catch((err)=>{
               this.logining = false;
　　　　        this.$message({
                  message: "网络错误",
                  type: 'error'
                });
　　        });
            },1000);
        
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }

</script>

<style lang="scss" scoped>
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px 800px;
    width: 300px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
  .main{
    background-image: url('/static/login.jpg');
     background-repeat: no-repeat;
    background-size: 100% 100%;
    position: absolute;
    top: 0px;
    left: 0px;
    right: 0px;
    bottom: 0px;
    min-width: 1100px;
    min-height: 500px;
    
  }
</style>