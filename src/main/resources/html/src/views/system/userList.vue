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
import { getUserList,delUser} from "../../api/api";

export default {
  data() {
    return {
      dataList: [],
      total: 0,
      page: 1,
      tName:"",
      phone:"",
      listLoading: false,
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
        tAnswer:'0',
        tName:this.tName,
        tPhone:this.phone
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
          }).catch(err => {
            this.logining = false;
            this.$message({
              message: "网络错误",
              type: "error"
            });
          });
      }, 500);
    },delUser:function(tId){
        let para = {
            tId:tId
        };
        console.log(tId);
        delUser(para).then(res=>{
                    this.$message({
                    message: "删除成功",
                    type: "success"
                    });
                    this.getUserList();
        }).catch(err=>{
        this.$message({
              message: "网络错误",
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