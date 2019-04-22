<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <div class="block">
            <el-date-picker v-model="filters.assessmentMonth" type="month" placeholder="选择月"></el-date-picker>
          </div>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.content" placeholder="工作内容"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getOtherEvaluationWorkPlanPage">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table
      :data="evaluateList"
      highlight-current-row
      v-loading="listLoading"
      @selection-change="selsChange"
      style="width: 100%;min-width:1019px;"
    >
      <el-table-column type="index" width="60"></el-table-column>
      <el-table-column align="center" prop="assessmentMonth" label="考核月份" min-width="100"></el-table-column>
      <el-table-column
        align="center"
        prop="realname"
        label="人员名称"
        :formatter="formatRealname"
        min-width="120"
      ></el-table-column>
      <el-table-column prop="createTime" label="创建日期" min-width="110"></el-table-column>
      <el-table-column align="center" prop="modifyTime" label="修改日期" min-width="110"></el-table-column>
      <el-table-column align="center" prop="auditTime" label="审核日期" min-width="110"></el-table-column>
      <el-table-column
        align="center"
        prop="status"
        :formatter="formatStatus"
        label="计划状态"
        min-width="120"
      ></el-table-column>1
      <el-table-column align="center" label="操作" min-width="180">
        <template slot-scope="scope">
          <el-button
            size="small"
            :disabled="isDisable(scope.$index, scope.row)"
            @click="handleCk(scope.$index, scope.row)"
          >考评</el-button>
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
import { getOtherEvaluationWorkPlanPage } from "../../api/api";

export default {
  data() {
    return {
      filters: {
        name: ""
      },
      evaluateList: [],
      total: 0,
      page: 1,
      listLoading: false,
      sels: [], //列表选中列

      editFormVisible: false, //编辑界面是否显示
      editLoading: false,
      editFormRules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }]
      },
      //编辑界面数据
      editForm: {
        id: 0,
        name: "",
        sex: -1,
        age: 0,
        birth: "",
        addr: ""
      },

      addFormVisible: false, //新增界面是否显示
      addLoading: false,
      addFormRules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }]
      },
      //新增界面数据
      addForm: {
        name: "111",
        sex: -1,
        age: 0,
        birth: "",
        addr: "1111"
      }
    };
  },
  methods: {
    //计划状态显示转换
    formatStatus: function(row, column) {
      if (row.status == 0) return "未提交";
      else if (row.status == 1) return "已提交";
      else if (row.status == 2) return "已审核";
      else if (row.status == 3) return "已总结";
      else if (row.status == 4) return "已考评";
    },
    formatRealname: function(row, column) {
      return row.staffInfo.realname;
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getOtherEvaluationWorkPlanPage();
    },
    //获取工作计划考评列表
    getOtherEvaluationWorkPlanPage() {
      this.listLoading = true;
      let para = {
        page: this.page,
        content: this.filters.content,
        assessmentMonth: this.filters.assessmentMonth,
        token: sessionStorage.getItem("token")
      };

      if (para.assessmentMonth != null) {
        var date = new Date(para.assessmentMonth);
        var datamonth = date.getFullYear() + "-" + (date.getMonth() + 1);
        var month = datamonth;
        para.assessmentMonth = month;
      }
      //console.log(para);
      setTimeout(() => {
        //NProgress.start();
        getOtherEvaluationWorkPlanPage(para).then(res => {
          if (res.data.code == 500) {
            this.$message({
              type: "error",
              message: "网络请求错误"
            });
          } else {
            // console.log(res);
            this.total = res.data.total;
            this.evaluateList = res.data.evaluateList;
            this.listLoading = false;
          }
        });
      }, 500);
    },
    handleCk: function(index, row) {
      this.$router.push({
        name: "考评月度工作计划",
        params: {
          id: row.id,
          month: row.assessmentMonth,
          staffWorkSelfComments: row.staffWorkEvaluation.staffWorkSelfComments,
          staffWorkSelfScore: row.staffWorkEvaluation.staffWorkSelfScore
        }
      });
    },
    selsChange: function(sels) {
      this.sels = sels;
    },

    isDisable(index, row) {
      if (row.status == 3) return false;
      else return true;
    }
  },
  mounted() {
    this.getOtherEvaluationWorkPlanPage();
  }
};
</script>

<style scoped>
</style>