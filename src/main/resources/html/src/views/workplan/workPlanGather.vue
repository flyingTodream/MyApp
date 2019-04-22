<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <div class="block">
            <el-date-picker v-model="filters.month" type="month" placeholder="选择月"></el-date-picker>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="workPlanGather">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="exportExcel">导出</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table
      :data="staffWorkEvaluation"
      id="evaluateList"
      highlight-current-row
      v-loading="listLoading"
      @selection-change="selsChange"
      style="width: 100%;min-width:1019px;"
    >
      <el-table-column type="index" width="60"></el-table-column>
      <el-table-column align="center" prop="realname" label="人员名称" width="120"></el-table-column>
      <el-table-column align="center" prop="staffWorkSelfScore" label="自我评分" min-width="110"></el-table-column>
      <el-table-column align="center" prop="staffWorkSelfComments" label="自我评价" min-width="110"></el-table-column>
      <el-table-column
        align="center"
        prop="staffWorkLeaderEvaluatedScore"
        label="领导评分"
        min-width="110"
      ></el-table-column>
      <el-table-column
        align="center"
        prop="staffWorkDirectLeaderComments"
        label="领导评价"
        min-width="110"
      ></el-table-column>1
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
import { workPlanGather } from "../../api/api";
import FileSaver from "file-saver";
import XLSX from "xlsx";
export default {
  data() {
    return {
      filters: {
        name: ""
      },
      defaultValue: "",
      staffWorkEvaluation: [],
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
    handleCurrentChange(val) {
      this.page = val;
      this.workPlanGather();
    },
    //获取工作计划考评列表
    workPlanGather() {
      this.listLoading = true;
      let para = {
        page: this.page,
        month: this.filters.month
      };

      var date = new Date(para.month);
      var datamonth = date.getFullYear() + "-" + (date.getMonth() + 1);
      var month = datamonth;
      para.month = month;
      //console.log(para);
      setTimeout(() => {
        //NProgress.start();
        workPlanGather(para).then(res => {
          if (res.data.code == 500) {
            this.$message({
              type: "error",
              message: "网络请求错误"
            });
          } else {
            // console.log(res);
            this.total = res.data.total;
            this.staffWorkEvaluation = res.data.staffWorkEvaluation;
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
      if (row.status == 5) return false;
      else return true;
    }, //导出到excel
    exportExcel() {
      if (this.filters.month != null && this.filters.month != "") {
        var date = new Date(this.filters.month);
      } else {
        var date = new Date(new Date());
      }
      var datamonth = date.getFullYear() + "-" + (date.getMonth() + 1);
      /* generate workbook object from table */
      var wb = XLSX.utils.table_to_book(
        document.querySelector("#evaluateList")
      );
      /* get binary string as output */
      var wbout = XLSX.write(wb, {
        bookType: "xlsx",
        bookSST: true,
        type: "array"
      });
      try {
        FileSaver.saveAs(
          new Blob([wbout], { type: "application/octet-stream" }),
          datamonth + "月度考评成绩汇总.xlsx"
        );
      } catch (e) {
        if (typeof console !== "undefined") console.log(e, wbout);
      }
      return wbout;
    }
  },
  mounted() {
    this.workPlanGather();
  }
};
</script>

<style scoped>
</style>