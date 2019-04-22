<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true">
        <el-form-item>
          <el-input v-model="tName" placeholder="昵称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="phone" placeholder="手机"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" plain v-on:click="getUserList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="dialogVisible = true">新增</el-button>
        </el-form-item>

        <el-dialog title="新增" :visible.sync="dialogVisible" width="30%">
          <el-form-item>
            <el-input v-model="addName" placeholder="管理员名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="addPhone" placeholder="手机"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="addEmail" placeholder="邮箱"></el-input>
          </el-form-item>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addAdmin">确 定</el-button>
          </span>
        </el-dialog>
      </el-form>
    </el-col>
    <!--列表-->
    <el-table
      :data="dataList"
      highlight-current-row
      v-loading="listLoading"
      style="width: 100%;min-width:1019px;"
    >
      <el-table-column type="index" width="60"></el-table-column>
      <el-table-column align="center" prop="tName" label="昵称" min-width="80"></el-table-column>
      <el-table-column align="center" prop="tPhone" label="手机" min-width="70"></el-table-column>
      <el-table-column align="center" prop="tEmail" label="邮箱" min-width="150"></el-table-column>
      <el-table-column align="center" prop="tRegtime" label="注册时间" min-width="150"></el-table-column>
      <el-table-column align="center" label="操作" min-width="180">
        <template slot-scope="scope">
          <el-button size="small" type="danger" @click="delUser(scope.row.tId)" plain>删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :page-size="10"
        :total="total"
        style="float:right;"
      ></el-pagination>
    </el-col>
  </section>
</template>

<script>
import util from "../../common/js/util";
//import NProgress from 'nprogress'
import { getUserList, delUser, addAdmin } from "../../api/api";

export default {
  data() {
    return {
      dialogVisible: false,
      dataList: [],
      total: 0,
      page: 1,
      tName: "",
      phone: "",
      listLoading: false,
      addName: "",
      addPhone: "",
      addEmail: ""
    };
  },
  methods: {
    handleCurrentChange(val) {
      this.page = val;
      this.getUserList();
    },
    //
    getUserList() {
      this.listLoading = true;
      let para = {
        page: this.page,
        tAnswer: "1",
        tName: this.tName,
        tPhone: this.phone
      };
      //console.log(para);
      setTimeout(() => {
        //NProgress.start();
        getUserList(para)
          .then(res => {
            console.log(res);
            this.total = res.data.total;
            this.dataList = res.data.userList;
            this.listLoading = false;
          })
          .catch(err => {
            this.logining = false;
            this.$message({
              message: "网络错误",
              type: "error"
            });
          });
      }, 500);
    },
    delUser: function(tId) {
      let para = {
        tId: tId
      };
      console.log(tId);
      delUser(para)
        .then(res => {
          this.$message({
            message: "删除成功",
            type: "success"
          });
          this.getUserList();
        })
        .catch(err => {
          this.$message({
            message: "网络错误",
            type: "error"
          });
        });
    },
    addAdmin: function() {
      let para = {
        name: this.addName,
        phone: this.addPhone,
        email: this.addEmail
      };
      if (para.name == null || para.name == "") {
        this.$message({
          message: "请补全信息",
          type: "info"
        });
        return;
      } else if (para.phone == null || para.phone == "") {
        this.$message({
          message: "请补全信息",
          type: "info"
        });
        return;
      } else if (para.email == null || para.email == "") {
        this.$message({
          message: "请补全信息",
          type: "info"
        });
        return;
      }
      addAdmin(para)
        .then(res => {
          if (res.data.status == 1) {
            this.$message({
              message: "此手机号用户已存在",
              type: "info"
            });
            return;
          } else {
            this.$message({
              message: "添加成功",
              type: "success"
            });
            this.dialogVisible = false;
            this.name = "";
            this.email = "";
            this.phone = "";
            this.getUserList();
          }
        })
        .catch(err => {
          this.$message({
            message: "网络请求错误",
            type: "error"
          });
        });
    }
  },
  mounted() {
    this.getUserList();
  }
};
</script>

<style scoped>
</style>