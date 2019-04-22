<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.content" placeholder="工作内容"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getWorkPlanPage">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table
      :data="users"
      highlight-current-row
      v-loading="listLoading"
      @selection-change="selsChange"
      style="width: 100%;min-width:1019px;"
    >
      <el-table-column type="selection" width="55"></el-table-column>
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
      <el-table-column align="center" prop="submitTime" label="提交日期" min-width="110"></el-table-column>
      <el-table-column
        align="center"
        prop="status"
        :formatter="formatStatus"
        label="计划状态"
        min-width="80"
      ></el-table-column>1
      <el-table-column align="center" label="操作" min-width="200">
        <template slot-scope="scope">
          <el-button size="small" @click="handleCk(scope.$index, scope.row)">查看</el-button>
          <el-button
            size="small"
            :disabled="isDisable(scope.$index, scope.row)"
            @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button>
          <el-button
            type="danger"
            :disabled="isDisable(scope.$index, scope.row)"
            size="small"
            @click="handleDel(scope.$index, scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
      <el-pagination
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :page-size="10"
        :total="total"
        style="float:right;"
      ></el-pagination>
    </el-col>

    <!--新增界面-->
    <el-dialog
      title="新增"
      :visible.sync="addFormVisible"
      v-model="addFormVisible"
      :close-on-click-modal="false"
    >
      <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
        <el-form-item label="计划月份" prop="assessmentMonth">
          <el-date-picker
            v-model="addForm.assessmentMonth"
            type="month"
            placeholder="选择月"
            auto-complete="off"
            aria-required="required"
            :picker-options="pickerOptions0"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import util from "../../common/js/util";
//import NProgress from 'nprogress'
import {
  checkWorkPlan,
  getWorkPlanPage,
  delWorkPlan,
  batchDeleteWorkPlan,
  editUser,
  addWorkPlan
} from "../../api/api";

export default {
  data() {
    return {
      filters: {
        name: ""
      },
      users: [],
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
      pickerOptions0: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7; //如果没有后面的-8.64e6就是不可以选择今天的
        }
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
      this.getWorkPlanPage();
    },
    //获取工作计划列表
    getWorkPlanPage() {
      this.listLoading = true;
      let para = {
        page: this.page,
        content: this.filters.content,
        token: sessionStorage.getItem("token")
      };
      setTimeout(() => {
        //NProgress.start();
        getWorkPlanPage(para).then(res => {
          //console.log(res.data);
          if (res.data.code == 500) {
            this.$message({
              type: "error",
              message: "网络请求错误"
            });
          } else {
            //console.log(res);
            this.total = res.data.total;
            this.users = res.data.WorkPlan;
            this.listLoading = false;
          }
        });
      }, 500);
    },
    //检查本月是否制定工作计划
    checkWorkPlan() {
      let param = {
        token: sessionStorage.getItem("token")
      };
      checkWorkPlan(param).then(res => {
        //console.log(res);
        if (res.data.code == 200 && res.data.isMaked == "Y") {
          this.$message({
            message: "本月工作计划已制定",
            center: true
          });
        } else if (res.data.code == 500) {
          this.$message({
            type: "error",
            message: "网络请求失败"
          });
        }
      });
    },

    //删除
    handleDel: function(index, row) {
      this.$confirm("确认删除该记录吗?", "提示", {
        type: "warning"
      })
        .then(() => {
          this.listLoading = true;
          //NProgress.start();
          let para = {
            id: row.id,
            token: sessionStorage.getItem("token")
          };
          delWorkPlan(para).then(res => {
            this.listLoading = false;
            // console.log(res);
            if (res.data.code == 200 && res.data.status) {
              this.$message({
                message: "删除成功",
                type: "success"
              });
              this.getWorkPlanPage();
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
        .catch(() => {});
    },
    //显示编辑界面
    handleEdit: function(index, row) {
      this.$router.push({
        name: "编辑月度工作计划",
        params: { id: row.id, month: row.assessmentMonth }
      });
      //this.$router.push({ path: "/editWorkplan" });
    },
    handleCk: function(index, row) {
      this.$router.push({
        name: "查看月度工作计划",
        params: { id: row.id, month: row.assessmentMonth }
      });
    },
    //显示新增界面
    handleAdd: function() {
      this.addFormVisible = true;
      this.addForm = {
        assessmentMonth: ""
      };
    },

    //新增
    addSubmit: function() {
      this.$refs.addForm.validate(valid => {
        if (valid) {
          this.addLoading = true;
          //NProgress.start();
          let para = Object.assign({}, this.addForm);
          para.token = sessionStorage.getItem("token");
          if (para.assessmentMonth == "") {
            this.$message({
              message: "请选择月份",
              type: "info"
            });
            this.addLoading = false;
            return;
          }
          //console.log(para);
          var date = new Date(para.assessmentMonth);
          var datamonth = date.getFullYear() + "-" + (date.getMonth() + 1);
          var month = datamonth;
          para.assessmentMonth = month;
          // console.log(para);
          addWorkPlan(para).then(res => {
            this.addLoading = false;
            if (
              res.data.code == 200 &&
              res.data.isMaked == "N" &&
              res.data.status
            ) {
              this.$message({
                message: "提交成功",
                type: "success"
              });
              this.$refs["addForm"].resetFields();
              this.addFormVisible = false;
              this.getWorkPlanPage();
            } else if (
              res.data.code == 200 &&
              res.data.isMaked == "Y" &&
              res.data.status
            ) {
              this.$message({
                message: "所选月份工作计划已经制定，请重新选择月份",
                type: "info"
              });
              //this.$refs["addForm"].resetFields();
              return;
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
              this.$refs["addForm"].resetFields();
              this.addFormVisible = false;
              this.getWorkPlanPage();
            }
          });
        }
      });
    },
    selsChange: function(sels) {
      this.sels = sels;
    },
    //批量删除
    batchRemove: function() {
      var ids = this.sels.map(item => item.id).toString();
      this.$confirm("确认删除选中记录吗？", "提示", {
        type: "warning"
      })
        .then(() => {
          this.listLoading = true;
          //NProgress.start();
          let para = {
            ids: ids,
            token: sessionStorage.getItem("token")
          };
          batchDeleteWorkPlan(para).then(res => {
            this.listLoading = false;
            //console.log(res);
            if (res.data.code == 200 && res.data.status) {
              this.$message({
                message: "删除成功",
                type: "success"
              });
              this.getWorkPlanPage();
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
        .catch(() => {});
    },
    isDisable(index, row) {
      if (row.status == 0) return false;
      else return true;
    }
  },
  mounted() {
    this.checkWorkPlan();
    this.getWorkPlanPage();
  }
};
</script>

<style scoped>
</style>