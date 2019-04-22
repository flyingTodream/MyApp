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
          <el-input v-model="filters.realname" placeholder="员工"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="filters.status" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="workPlanAuditor">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table
      :data="users"
      highlight-current-row
      v-loading="listLoading"
      @selection-change="selsChange"
      style="width: 100%;min-width:984px;"
    >
      <el-table-column type="index" width="60"></el-table-column>
      <el-table-column align="center" prop="assessmentMonth" label="考核月份" width="100"></el-table-column>
      <el-table-column
        align="center"
        prop="realname"
        label="人员名称"
        :formatter="formatRealname"
        width="120"
      ></el-table-column>
      <el-table-column prop="createTime" label="创建日期" width="110"></el-table-column>
      <el-table-column align="center" prop="modifyTime" label="修改日期" width="110"></el-table-column>
      <el-table-column align="center" prop="submitTime" label="提交日期" width="110"></el-table-column>
      <el-table-column
        align="center"
        prop="status"
        :formatter="formatStatus"
        label="计划状态"
        width="120"
      ></el-table-column>1
      <el-table-column align="center" label="操作" min-width="180">
        <template slot-scope="scope">
          <el-button
            size="small"
            :disabled="isDisable(scope.$index, scope.row)"
            @click="handleAudit(scope.$index, scope.row)"
          >审核</el-button>
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
import { workPlanAuditor } from "../../api/api";

export default {
  data() {
    return {
      filters: {},
      users: [],
      total: 0,
      page: 1,
      listLoading: false,
      sels: [], //列表选中列
      options: [
        {
          value: "1",
          label: "已提交"
        },
        {
          value: "2",
          label: "已审核"
        },
        {
          value: "3",
          label: "已总结"
        },
        {
          value: "4",
          label: "已考评"
        }
      ],
      value: ""
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
    isDisable(index, row) {
      if (row.status == 1) return false;
      else return true;
    },
    handleCurrentChange(val) {
      this.page = val;
      this.workPlanAuditor();
    },
    //获取工作计划列表
    workPlanAuditor() {
      this.listLoading = true;
      let para = {
        page: this.page,
        assessmentMonth: this.filters.assessmentMonth,
        status: this.filters.status,
        realName: this.filters.realname,
        token: sessionStorage.getItem("token")
      };
      //console.log(para);
      if (para.assessmentMonth != null) {
        var date = new Date(para.assessmentMonth);
        var datamonth = date.getFullYear() + "-" + (date.getMonth() + 1);
        var month = datamonth;
        para.assessmentMonth = month;
      }

      //console.log(para);
      setTimeout(() => {
        //NProgress.start();
        workPlanAuditor(para).then(res => {
          //console.log(res);
          if (res.data.code == 500) {
            this.$message({
              type: "error",
              message: "网络请求错误"
            });
          } else {
            // console.log(res);
            this.total = res.data.total;
            this.users = res.data.auditorWorkList;
            this.listLoading = false;
          }
        });
      }, 500);
    },
    //显示编辑界面
    handleAudit: function(index, row) {
      this.$router.push({
        name: "审核月度工作计划",
        params: { id: row.id, month: row.assessmentMonth }
      });
      //this.$router.push({ path: "/editWorkplan" });
    },
    selsChange: function(sels) {
      this.sels = sels;
    }
  },
  mounted() {
    this.workPlanAuditor();
  }
};
</script>

<style scoped>
</style>