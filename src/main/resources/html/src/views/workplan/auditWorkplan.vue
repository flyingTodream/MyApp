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
      <el-table-column min-width="60" header-align="center">
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

      <el-table-column fixed="right" label="操作" min-width="100" align="center">
        <template slot-scope="{row,$index}">
          <el-button
            type="success"
            size="small"
            @click.native="handleUpdate($index, row)"
            v-if="showBtn[$index]"
          >保存</el-button>
          <el-button
            size="small"
            @click.native="handleCancel($index, row)"
            v-if="showBtn[$index]"
          >取消</el-button>
          <el-button
            type="primary"
            size="small"
            @click.native="handleEdit($index, row)"
            v-if="!showBtn[$index]"
          >编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="success" @click="WorkPlanAudit">审核</el-button>
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
  workPlanVerify
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
    //新增行
    handleAdd() {
      var li = {
        id: "",
        workType: "",
        workContent: ""
      };
      this.WorkDetail.push(li);
    }, //删除
    handleDelete(index, row) {
      let para = {
        id: row.id,
        token: sessionStorage.getItem("token")
      };
      if (row.id != "") {
        delWorkDetail(para).then(res => {
          //console.log(res);
          if (res.data.code == 200 && res.data.status) {
            this.$message({
              type: "success",
              message: "删除成功"
            });
            this.WorkDetail.splice(index, 1);
          } else if (res.data.code == 200 && !res.data.status) {
            this.$message({
              message: "登录超时，请重新登录",
              type: "error"
            });
            this.$router.push({ path: "/login" });
          } else {
            this.$message({
              type: "error",
              message: "网络请求错误"
            });
          }
        });
      } else {
        this.WorkDetail.splice(index, 1);
      }
    },
    //更新
    handleUpdate(index, row) {
      //console.log(row);
      if (row.workType == "") {
        this.$message({
          type: "error",
          message: "请选择工作类型"
        });
        return;
      }
      if (row.workContent == "") {
        this.$message({
          type: "error",
          message: "请填写工作计划"
        });
        return;
      }
      let para = {
        id: row.id,
        workType: row.workType,
        workContent: row.workContent,
        staffWorkId: this.$route.params.id,
        token: sessionStorage.getItem("token")
      };
      addWorkDetail(para).then(res => {
         if (res.data.code == 200 && res.data.status) {
          this.$message({
            type: "success",
            message: "保存成功"
          });
          this.$set(this.showEdit, row, true);
          this.$set(this.showBtn, row, true);
          this.showEdit[index] = false;
          this.showBtn[index] = false;
          this.getWorkDetailPage();
        } else if (res.data.code == 200 && !res.data.status) {
          this.$message({
            message: "登录超时，请重新登录",
            type: "error"
          });
          this.$router.push({ path: "/login" });
        } else {
          this.$message({
            type: "error",
            message: "网络请求错误"
          });
        }
      });
    },
    getOptions() {
      let para = {};
      staffWorkType(para).then(res => {
        this.list = res.data.staffworktype.map(item => {
          return { value: item.workTypeRef, label: item.workTypeDesc };
        });
        this.options = this.list.filter(item => {
          return item.label.toLowerCase();
        });
      });
    }, //点击编辑
    handleEdit(index, row) {
      this.showEdit[index] = true;
      this.showBtn[index] = true;
      this.$set(this.showEdit, row, true);
      this.$set(this.showBtn, row, true);
      this.getWorkDetailPage();
    },
    //取消编辑
    handleCancel(index, row) {
      this.$set(this.showEdit, row, true);
      this.$set(this.showBtn, row, true);
      this.showEdit[index] = false;
      this.showBtn[index] = false;
      this.getWorkDetailPage();
    },
    //审核
    WorkPlanAudit() {
      let para = {
        token: sessionStorage.getItem("token"),
        id: this.$route.params.id
      };
      this.$confirm("确认审核？")
        .then(_ => {
          workPlanVerify(para).then(res => {
            // console.log(res);
            if (res.data.code == 200 && res.data.status) {
              this.$message({
                type: "success",
                message: "审核成功"
              });
              this.$router.push({ path: "/workplanAudit" });
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
    }
  },
  mounted() {
    this.getWorkDetailPage();
    this.getOptions();
    this.month = this.$route.params.month;
  }
};
</script>
<style>
</style>
