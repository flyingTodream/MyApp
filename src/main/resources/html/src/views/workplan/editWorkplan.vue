<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true">
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
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
          <el-button
            type="danger"
            size="small"
            @click.native="handleDelete($index, row)"
            v-if="!showBtn[$index]"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="success" :disabled="this.WorkDetail.length===0" @click="WorkPlanSubmit">提交</el-button>
      <el-button type="primary" plain @click="back">返回</el-button>

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
        <el-form-item label="工作类型" prop="workType">
          <el-select v-model="addForm.workType" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工作内容" prop="workContent">
          <el-input class="edit-cell" v-model="addForm.workContent"></el-input>
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
      month: "",
      addFormVisible: false, //新增界面是否显示
      //新增界面数据
      addForm: {
        workType: ""
      },
      addFormRules: {
        workType: [
          { required: true, message: "请选择工作计划", trigger: "blur" }
        ],
        workContent: [
          { required: true, message: "请输入工作内容", trigger: "blur" }
        ]
      },
      addLoading: false
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

      //console.log(this.$route);
    },
    //新增行
    handleAdd() {
      this.addFormVisible = true;
      this.addForm = {};
      /* var li = {
        id: "2",
        workType: "",
        workContent: ""
      };
      this.WorkDetail.push(li);*/
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
        //console.log(res);
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
    addSubmit() {
      this.addLoading = true;
      //NProgress.start();
      let para = Object.assign({}, this.addForm);
      para.token = sessionStorage.getItem("token");
      para.staffWorkId = this.$route.params.id;
      para.id = "";
      if (para.workType == null) {
        this.$message({
          type: "error",
          message: "请选择工作类型"
        });
        this.addLoading = false;
        return;
      }
      if (para.workContent == null) {
        this.$message({
          type: "error",
          message: "请输入工作内容"
        });
        this.addLoading = false;
        return;
      }
      addWorkDetail(para).then(res => {
        //console.log(res);
        if (res.data.code == 200 && res.data.status) {
          this.$message({
            type: "success",
            message: "保存成功"
          });
          this.addLoading = false;
          this.$refs["addForm"].resetFields();
          this.addFormVisible = false;
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
          this.addLoading = false;
          return;
        }
      });
    },
    getOptions() {
      let para = {};
      staffWorkType(para).then(res => {
        this.list = res.data.staffworktype.map(item => {
          //console.log(item);
          return { value: item.workTypeRef, label: item.workTypeDesc };
        });
        this.options = this.list.filter(item => {
          return item.label.toLowerCase();
        });
      });
    }, //点击编辑
    handleEdit(index, row) {
      // console.log(row);
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
    //提交
    WorkPlanSubmit() {
      let para = {
        token: sessionStorage.getItem("token"),
        id: this.$route.params.id
      };
      workPlanSubmit(para).then(res => {
        // console.log(res);
        if (res.data.code == 200 && res.data.ces == "Y" && res.data.status) {
          this.$message({
            type: "success",
            message: "提交成功"
          });
          this.$router.push({ path: "/makeworkplan" });
        } else if (
          res.data.code == 200 &&
          res.data.ces == "N" &&
          res.data.status
        ) {
          this.$message({
            type: "info",
            message: "所选工作计划不是当前月份，不能提交"
          });
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
        }
      });
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
