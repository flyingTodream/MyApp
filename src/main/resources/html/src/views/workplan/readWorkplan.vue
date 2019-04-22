<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true">
        <el-form-item label="月份">{{month}}</el-form-item>
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

      <el-table-column label="工作类型" prop="workTypeDesc" min-width="110" align="center">
        <template slot-scope="{row,$index}">
          <el-select v-if="showEdit[$index]" v-model="row.workType" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>

          <span v-if="!showEdit[$index]">{{row.workTypeDesc}}</span>
        </template>
      </el-table-column>

      <el-table-column label="工作内容" prop="workContent" min-width="110" align="center">
        <template slot-scope="{row,$index}">
          <el-input class="edit-cell" v-if="showEdit[$index]" v-model="row.workContent"></el-input>
          <span v-if="!showEdit[$index]">{{row.workContent}}</span>
        </template>
      </el-table-column>
    </el-table>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
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
  workPlanSubmit
} from "../../api/api";
export default {
  data() {
    return {
      options: [],
      WorkDetail: [],
      showEdit: [], //显示编辑框
      showBtn: [],
      showBtnOrdinary: true,
      total: 0,
      page: 1,
      listLoading: false,
      month: ""
    };
  },

  methods: {
    //获取工作计划列表
    getWorkDetailPage() {
      this.listLoading = true;
      let para = {
        page: this.page,
        token: sessionStorage.getItem("token"),
        id: this.$route.params.id
      };

      setTimeout(() => {
        //console.log(this.$route);
        getWorkDetailPage(para).then(res => {
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
      }, 500);
    },
    back() {
      window.history.go(-1);
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getWorkDetailPage();
    }
  },
  mounted() {
    this.getWorkDetailPage();
    this.month = this.$route.params.month;
  }
};
</script>
<style>
</style>
