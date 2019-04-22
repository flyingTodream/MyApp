<template>
  <el-row class="container">
    <el-col :span="24" class="header">
      <el-col :span="10" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">助E游</el-col>
      <!--<el-col :span="10">
        
        <div class="tools" @click.prevent="collapse">
          <i class="fa fa-align-justify"></i>
        </div>
      </el-col>-->
      <el-col :span="4" class="userinfo">
        <el-dropdown trigger="hover">
          <span class="el-dropdown-link userinfo-inner">
            {{sysUserName}} &nbsp;
            <i class="el-icon-arrow-down"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="passwordEdit">修改密码</el-dropdown-item>
            <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
      <el-dialog title="修改密码" :visible.sync="dialogVisible" center height="10%" width="30%">
        <el-form :inline="true" ref="form"  :rules="rules" :model="filters">
          <el-form-item    prop="password">
            <el-input type="password" v-model="filters.password" placeholder="新密码"></el-input>
          </el-form-item>
          <el-form-item prop="password_confirm">
            <el-input type="password" v-model="filters.password_confirm" placeholder="确认新密码"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="close">取 消</el-button>
          <el-button type="primary" @click="updatePassword">确 定</el-button>
        </span>
      </el-dialog>
    </el-col>
    <el-col :span="24" class="main">
      <aside :class="collapsed?'menu-collapsed':'menu-expanded'">
        <!--导航菜单-->
        <el-menu
          class="el-menu-vertical-demo"
          @open="handleopen"
          @close="handleclose"
          @select="handleselect"
          unique-opened
          router
          v-show="!collapsed"
        >
          <template v-for="(item,index) in this.menu" v-if="!item.hidden">
            <el-submenu :index="index+''" v-if="!item.leaf">
              <template slot="title">
                <i :class="item.iconCls"></i>
                {{item.name}}
              </template>
              <el-menu-item
                v-for="child in item.children"
                :index="child.path"
                :key="child.path"
                v-if="!child.hidden"
                :class="$route.path==child.path?'is-active':''"
              >{{child.name}}</el-menu-item>
            </el-submenu>
            <el-menu-item
              v-if="item.leaf&&item.children.length>0"
              :index="item.children[0].path"
              :class="$route.path==child.path?'is-active':''"
            >
              <i :class="item.iconCls"></i>
              {{item.children[0].name}}
            </el-menu-item>
          </template>
        </el-menu>
        <!--导航菜单-折叠后-->
        <ul class="el-menu el-menu-vertical-demo collapsed" v-show="collapsed" ref="menuCollapsed">
          <li
            v-for="(item,index) in $router.options.routes"
            v-if="!item.hidden"
            class="el-submenu item"
          >
            <template v-if="!item.leaf">
              <div
                class="el-submenu__title"
                style="padding-left: 20px;"
                @mouseover="showMenu(index,true)"
                @mouseout="showMenu(index,false)"
              >
                <i :class="item.iconCls"></i>
              </div>
              <ul
                class="el-menu submenu"
                :class="'submenu-hook-'+index"
                @mouseover="showMenu(index,true)"
                @mouseout="showMenu(index,false)"
              >
                <li
                  v-for="child in item.children"
                  v-if="!child.hidden"
                  :key="child.path"
                  class="el-menu-item"
                  style="padding-left: 40px;"
                  :class="$route.path==child.path?'is-active':''"
                  @click="$router.push(child.path)"
                >{{child.name}}</li>
              </ul>
            </template>
            <template v-else>
              <li class="el-submenu">
                <div
                  class="el-submenu__title el-menu-item"
                  style="padding-left: 20px;height: 56px;line-height: 56px;padding: 0 20px;"
                  :class="$route.path==item.children[0].path?'is-active':''"
                  @click="$router.push(item.children[0].path)"
                >
                  <i :class="item.iconCls"></i>
                </div>
              </li>
            </template>
          </li>
        </ul>
      </aside>
      <section class="content-container">
        <div class="grid-content bg-purple-light">
          <el-col :span="24" class="breadcrumb-container">
            <strong class="title">{{$route.name}}</strong>
            <el-breadcrumb separator="/" class="breadcrumb-inner">
              <el-breadcrumb-item v-for="item in $route.matched" :key="item.path">{{ item.name }}</el-breadcrumb-item>
            </el-breadcrumb>
          </el-col>
          <el-col :span="24" class="content-wrapper">
            <transition name="fade" mode="out-in">
              <router-view></router-view>
            </transition>
          </el-col>
        </div>
      </section>
    </el-col>
  </el-row>
</template>

<script>
import { doModifyPassword, getUserMenu } from "../api/api";
import store from "../vuex/store";
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.filters.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    var Vpasswprd = (rule, value, callback) => {
      if (value.length < 8) {
        callback(new Error("密码不得小于8位"));
      } else {
        callback();
      }
    };
    return {
      rules: {
        password_confirm: [{ validator: validatePass, trigger: "change" }],
        password: [{ validator: Vpasswprd, trigger: "change" }]
      },
      collapsed: false,
      sysUserName: "",
      sysUserAvatar: "",
      dialogVisible: false,
      filters: {
      },
      menu: [],
      form: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: ""
      }
    };
  },
  methods: {
    passwordEdit: function() {
      this.dialogVisible = true;
    },
    //修改密码
    updatePassword: function() {
if(this.filters.password==null){
  return;
}
      else if (this.filters.password!=null && this.filters.password.length < 8) {
        return;
      } else if (this.filters.password!=null && this.filters.password !== this.filters.password_confirm) {
        return;
      } else {
        const loading = this.$loading({
          lock: true,
          text: "修改中...",
          spinner: "el-icon-loading",
          background: "rgba(0, 0, 0, 0.7)"
        });
        let params = {
          newPassword: this.filters.password,
          token: sessionStorage.getItem("token")
        };
        setTimeout(() => {
        doModifyPassword(params).then(data => {
          loading.close();
          if (data.code == 200 && data.status == 0) {
            this.$message({
              type: "success",
              message: "修改成功,请重新登录"
            });
            sessionStorage.clear();
            this.$router.push({ path: "/login" });
          } else if (data.code == 200 && data.status == 401) {
            sessionStorage.clear();
            this.$message({
              type: "error",
              message: "登录超时，请重新登录"
            });
            this.$router.push({ path: "/login" });
          } else {
            this.filters={},
            this.$message({
              type: "info",
              message: "修改失败"
            });
          }
        });
         }, 2000);
      }
    },
    onSubmit() {
      //console.log("submit!");
    },
    handleopen() {
      //console.log('handleopen');
    },
    handleclose() {
      //console.log('handleclose');
    },
    handleselect: function(a, b) {
      // console.log(b);
    }, //关闭修改密码窗口
    close: function() {
      this.filters = {};
      this.dialogVisible = false;
    },
    //退出登录
    logout: function() {
      var _this = this;
      this.$confirm("确认退出吗?", "提示", {
        //type: 'warning'
      })
        .then(() => {
          const loading = this.$loading({
            lock: true,
            text: "正在退出...",
            spinner: "el-icon-loading",
            background: "rgba(0, 0, 0, 0.7)"
          });
          setTimeout(() => {
            loading.close();
            sessionStorage.clear;
            _this.$router.push("/login");
          }, 1000);
        })
        .catch(() => {});
    },
    //折叠导航栏
    collapse: function() {
      this.collapsed = !this.collapsed;
    },
    showMenu(i, status) {
      this.$refs.menuCollapsed.getElementsByClassName(
        "submenu-hook-" + i
      )[0].style.display = status ? "block" : "none";
    }
  },
  mounted() {
    var realname = sessionStorage.getItem("realname");
    if (realname) {
      this.sysUserName = realname || "";
    }
    let params = {
      token: sessionStorage.getItem("token")
    };
    getUserMenu(params).then(res => {
      this.menu = res.data;
    });
    //console.log(this.menu);
  }
};
</script>

<style scoped lang="scss">
@import "~scss_vars";

.container {
  position: absolute;
  top: 0px;
  bottom: 0px;
  width: 100%;
  .header {
    height: 60px;
    line-height: 60px;
    background: $color-primary;
    color: #fff;
    .userinfo {
      text-align: right;
      padding-right: 35px;
      float: right;
      .userinfo-inner {
        cursor: pointer;
        color: #fff;
        img {
          width: 40px;
          height: 40px;
          border-radius: 20px;
          margin: 10px 0px 10px 10px;
          float: right;
        }
      }
    }
    .logo {
      //width:230px;
      height: 60px;
      font-weight: bolder;
      width: 30px;
      text-align: center;
      font-family: "Microsoft YaHei","微软雅黑";
      font-size: 22px;
      padding-left: 20px;
      padding-right: 20px;
      border-color: rgba(238, 241, 146, 0.3);
      border-right-width: 1px;
      border-right-style: solid;
      img {
        width: 40px;
        float: left;
        margin: 10px 10px 10px 18px;
      }
      .txt {
        color: #fff;
      }
    }
    .logo-width {
      width: 230px;
    }
    .logo-collapse-width {
      width: 60px;
    }
    .tools {
      padding: 0px 23px;
      width: 14px;
      height: 60px;
      line-height: 60px;
      cursor: pointer;
    }
  }
  .main {
    display: flex;
    // background: #324057;
    position: absolute;
    top: 60px;
    bottom: 0px;
    overflow: hidden;
    aside {
      flex: 0 0 230px;
      width: 230px;
      // position: absolute;
      // top: 0px;
      // bottom: 0px;
      .el-menu {
        height: 100%;
      }
      .collapsed {
        width: 60px;
        .item {
          position: relative;
        }
        .submenu {
          position: absolute;
          top: 0px;
          left: 60px;
          z-index: 99999;
          height: auto;
          display: none;
        }
      }
    }
    .menu-collapsed {
      flex: 0 0 60px;
      width: 60px;
    }
    .menu-expanded {
      flex: 0 0 230px;
      width: 230px;
    }
    .content-container {
      // background: #f1f2f7;
      flex: 1;
      // position: absolute;
      // right: 0px;
      // top: 0px;
      // bottom: 0px;
      // left: 230px;
      overflow-y: scroll;
      padding: 20px;
      .breadcrumb-container {
        //margin-bottom: 15px;
        .title {
          width: 200px;
          float: left;
          color: #475669;
        }
        .breadcrumb-inner {
          float: right;
        }
      }
      .content-wrapper {
        background-color: #fff;
        box-sizing: border-box;
      }
    }
  }
}
</style>