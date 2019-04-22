<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.kw" placeholder="请输入关键词搜索"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" plain v-on:click="getRateList">查询</el-button>
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
      <el-table-column align="center" prop="tInfoTitle" label="景点名称" min-width="80"></el-table-column>
      <el-table-column align="center" prop="tName" label="评价人" :formatter="formatRealname" min-width="70"></el-table-column>
       <el-table-column align="center" prop="eContent" label="内容" min-width="150"></el-table-column>
       <el-table-column align="center" prop="eTime" label="时间" min-width="150"></el-table-column>
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
import { getRateList} from "../../api/api";

export default {
  data() {
    return {
      filters: {
        name: ""
      },
      newName:"",
      dataList: [],
      total: 0,
      page: 1,
      listLoading: false,
    };
  },
  methods: {
    formatRealname:function(row, column){
        if(row.eIsAnonymous == '是'){
            return "匿名"
        }else{
          return row.tName;
        }
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getRateList();
    },
    //
    getRateList() {
      this.listLoading = true;
      let para = {
        page: this.page,
        tInfoTitle: this.filters.kw,
        token: sessionStorage.getItem("token")
      };
      //console.log(para);
      setTimeout(() => {
        //NProgress.start();
        getRateList(para)
          .then(res => {
            console.log(res);
            this.total = res.data.total;
            this.dataList = res.data.rateList;
            this.listLoading = false;
          }).catch(err => {
            this.logining = false;
            this.$message({
              message: "网络错误",
              type: "error"
            });
          });
      }, 500);
    }
  },
  mounted() {
    this.getRateList();
  }
};
</script>

<style scoped>
</style>