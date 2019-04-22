<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true">
        <el-form-item label="月份">{{month}}</el-form-item>
        <el-form-item label="自我打分">{{staffWorkSelfScore}}分</el-form-item>
        <el-form-item label="自我评价">{{staffWorkSelfComments}}</el-form-item>
      </el-form>
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.staffWorkLeaderEvaluatedScore" type="number" placeholder="打分"></el-input>
        </el-form-item>
        <el-input
          resize="none"
          type="textarea"
          v-model="filters.staffWorkDirectLeaderComments"
          placeholder="评价"
        ></el-input>
      </el-form>
    </el-col>
    <el-table
      :data="WorkDetail"
      tooltip-effect="dark"
      style="width: 100%"
      v-loading="listLoading"
      align="center"
    >
      <el-table-column width="60" header-align="center">
        <template slot-scope="{row,$index}">
          <span>{{$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="workTypeDesc" label="工作类型" min-width="110"></el-table-column>
      <el-table-column align="center" prop="workContent" label="工作内容" min-width="110"></el-table-column>
      <el-table-column align="center" prop="completionSituation" label="完成情况" min-width="110"></el-table-column>
    </el-table>
    <!--工具条-->
    <el-col :span="24" align="center" class="toolbar">
      <el-button type="success" @click="evaluation">提交</el-button>
      <el-button type="primary" plain @click="back">返回</el-button>

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
import {
  getWorkDetailPage,
  delWorkDetail,
  addWorkDetail,
  staffWorkType,
  evaluationOtherWorkPlan
} from "../../api/api";
export default {
  data() {
    return {
      staffWorkSelfScore: "",
      staffWorkSelfComments: "",
      filters: {},
      options: [],
      WorkDetail: [],
      showBtnOrdinary: true,
      total: 0,
      page: 1,
      listLoading: false,
      month: ""
    };
  },

  methods: {
    handleCurrentChange(row, event, column) {
      //this.optionsRest();
      // console.log(row, event, column);
    },
    handleEdit(index, row) {
      // alert("11");
      //console.log(index, row);
    },
    //获取工作计划列表
    getWorkDetailPage() {
      this.listLoading = true;
      let para = {
        page: this.page,
        token: sessionStorage.getItem("token"),
        id: this.$route.params.id
      };
      //console.log(this.$route);
      getWorkDetailPage(para).then(res => {
        //console.log(res.data);
        if (res.data.code == 500) {
          this.$message({
            type: "error",
            message: "网络请求错误"
          });
        } else {
          // console.log(res);
          this.total = res.data.total;
          this.WorkDetail = res.data.workDetailList;
          this.listLoading = false;
        }
      });
    },
    //考评
    evaluation() {
      let para = {
        token: sessionStorage.getItem("token"),
        staffWorkLeaderEvaluatedScore: this.filters
          .staffWorkLeaderEvaluatedScore,
        staffWorkDirectLeaderComments: this.filters
          .staffWorkDirectLeaderComments,
        staffWorkId: this.$route.params.id
      };
      if (
        para.staffWorkLeaderEvaluatedScore > 120 ||
        para.staffWorkLeaderEvaluatedScore <= 0 ||
        para.staffWorkLeaderEvaluatedScore == null
      ) {
        this.$message({
          type: "info",
          message: "分数应在0-120 之间"
        });
        return;
      } else if (!this.isInteger(para.staffWorkLeaderEvaluatedScore)) {
        this.$message({
          type: "info",
          message: "请输入一个整数"
        });
        return;
      }
      //alert(para.staffWorkSelfComments);
      //console.log(para);
      else if (
        para.staffWorkDirectLeaderComments == "" ||
        para.staffWorkDirectLeaderComments == null
      ) {
        this.$message({
          type: "info",
          message: "请输入评价"
        });
        return;
      }
      this.$confirm("确认提交？")
        .then(_ => {
          evaluationOtherWorkPlan(para).then(res => {
            // console.log(res);
            if (res.data.code == 200 && res.data.status) {
              this.$message({
                type: "success",
                message: "提交成功"
              });
              this.$router.push({ path: "/getEvaluationWorkPlanPage" });
            } else if (res.data.code == 200 && !res.data.status) {
              this.$message({
                message: "登录超时，请重新登录",
                type: "error"
              });
              this.$router.push({ path: "/login" });
            } else {
              this.$message({
                message: "网络请求错误",
                type: "error"
              });
            }
          });
        })
        .catch(_ => {
          this.$message({
            type: "info",
            message: "取消操作"
          });
        });
      return;
    },
    back() {
      window.history.go(-1);
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getWorkDetailPage();
    }, //判断是否为整数
    isInteger(obj) {
      return obj % 1 === 0;
    }
  },
  mounted() {
    this.getWorkDetailPage();
    this.month = this.$route.params.month;
    this.staffWorkSelfScore = this.$route.params.staffWorkSelfScore;
    this.staffWorkSelfComments = this.$route.params.staffWorkSelfComments;
  }
};
</script>
<style>
</style>
