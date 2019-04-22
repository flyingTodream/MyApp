<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form style="min-width:790px;" :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.username" placeholder="用户账号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.realname" placeholder="用户名称"></el-input>
          <el-input class="filters-input" v-model="filters.department" type="hidden"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="filters.departmentName"
            placeholder="所属部门"
            readonly="readonly"
            @click.native="isShowSelect = !isShowSelect"
          >></el-input>
          <el-tree
            class="filters-tree"
            :data="departmentOptions"
            :props="defaultProps"
            v-if="isShowSelect"
            @node-click="handleNodeClick"
          ></el-tree>

          <!--
            <el-select v-model="filters.department" placeholder="所属部门">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>-->
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUserListPage">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table
      :data="userList"
      highlight-current-row
      v-loading="listLoading"
      @selection-change="selsChange"
      style="width: 100%;min-width:1019px;"
    >
      <el-table-column type="index" width="60"></el-table-column>
      <el-table-column align="center" prop="username" label="用户账号" width="100"></el-table-column>
      <el-table-column align="center" prop="realname" label="姓名" width="100"></el-table-column>
      <el-table-column align="center" prop="typeDesc.staffTypeDesc" label="用户类型" width="120"></el-table-column>
      <el-table-column prop="departmentInfo.departmentName" label="所属部门" width="110"></el-table-column>
      <el-table-column align="center" prop="leaderInfo.realname" label="上级领导" width="110"></el-table-column>
      <el-table-column align="center" prop="addTime" label="创建时间" width="110"></el-table-column>
      <el-table-column align="center" label="操作" min-width="180">
        <template slot-scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="delUser(scope.$index, scope.row)">删除</el-button>
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

    <!--新增界面-->
    <el-dialog
      title="编辑"
      :visible.sync="addFormVisible"
      v-model="addFormVisible"
      :close-on-click-modal="false"
    >
      <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
        <el-form-item label="用户账号" prop="username">
          <el-input class="edit-cell" v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="realname">
          <el-input class="edit-cell" v-model="addForm.realname"></el-input>
        </el-form-item>
        <el-form-item label="用户类型" prop="staffType">
          <el-select @change="getleader" v-model="addForm.staffType" placeholder="请选择">
            <el-option
              v-for="item in staffTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-input class="filters-input" v-model="addForm.department" type="hidden"></el-input>
        <el-input class="filters-input" v-model="addForm.id" type="hidden"></el-input>

        <el-form-item label="所属部门" prop="departmentName">
          <el-input
            style="width:220px"
            v-model="addForm.departmentName"
            placeholder="所属部门"
            readonly="readonly"
            @click.native="isShowSelect = !isShowSelect"
          >></el-input>
          <el-tree
            style="width:220px"
            class="filters-tree"
            :data="departmentOptions"
            :props="defaultProps"
            v-if="isShowSelect"
            @node-click="handleAddNodeClick"
          ></el-tree>
        </el-form-item>
        <el-form-item label="上级领导" prop="leaderInfo">
          <el-select v-model="addForm.leaderInfo" placeholder="请选择">
            <el-option
              v-for="item in leaderInfoOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
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
  getUserListPage,
  getDepartments,
  workPlanSubmit,
  getStaffType,
  getleader,
  InsUpdUserInfo,
  delUserInfo
} from "../../api/api";
export default {
  data() {
    return {
      filters: {},
      defaultProps: {
        id: "id",
        label: "departmentName",
        children: "children"
      },
      isShowSelect: false,
      departmentOptions: [],
      staffTypeOptions: [],
      leaderInfoOptions: [],
      userList: [],
      list: [],
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
    selsChange: function(sels) {
      this.sels = sels;
    },
    handleCurrentChange(row, event, column) {
      //this.optionsRest();
      // console.log(row, event, column);
    },
    // 点击巡检对象的选择器的树节点
    handleNodeClick(data, Node) {
      // 如果是顶级父节点
      // console.log(data);
      //console.log(Node);
      if (Node.data.children != 0) {
        //alert(Node.data.departmentDesc);
      } else {
        this.filters.departmentName = Node.data.departmentDesc;
        this.filters.department = Node.data.id;
        // 关闭选择器
        this.isShowSelect = false;
      }
    },
    handleAddNodeClick(data, Node) {
      if (Node.data.children != 0) {
        //alert(Node.data.departmentDesc);
      } else {
        this.addForm.departmentName = Node.data.departmentDesc;
        this.addForm.department = Node.data.id;
        // 关闭选择器
        this.isShowSelect = false;
      }
    },
    handleEdit(index, row) {
      this.addForm = {
        username: row.username,
        realname: row.realname,
        staffType: parseInt(row.staffType),
        departmentName: row.departmentInfo.departmentName,
        department: row.department,
        leaderInfo: row.leaderInfo.username,
        id: row.id
      };
      this.getleader();
      this.addFormVisible = true;
    },
    //获取人员列表
    getUserListPage() {
      this.listLoading = true;
      let para = {
        page: this.page,
        username: this.filters.username,
        realname: this.filters.realname,
        department: this.filters.department
      };
      setTimeout(() => {
        getUserListPage(para).then(res => {
          if (res.data.code == 500) {
            this.$message({
              type: "error",
              message: "网络请求错误"
            });
          } else {
            // console.log(res);
            this.total = res.data.total;
            this.userList = res.data.userList;
            this.listLoading = false;
          }
        });
      }, 500);

      //console.log(this.$route);
    },
    //新增行
    handleAdd() {
      this.addFormVisible = true;
      this.addForm = {
        department: ""
      };
    }, //删除
    delUser(index, row) {
      let para = {
        id: row.id,
        token: sessionStorage.getItem("token")
      };
      delUserInfo(para).then(res => {
        //console.log(res);
        if (res.data.code == 200 && res.data.status) {
          this.$message({
            type: "success",
            message: "删除成功"
          });
          this.userList.splice(index, 1);
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
          return;
        }
      });
    },
    addSubmit() {
      this.addLoading = true;
      //NProgress.start();
      let para = Object.assign({}, this.addForm);
      para.token = sessionStorage.getItem("token");
      if (para.username == null) {
        this.$message({
          type: "error",
          message: "请输入用户账号"
        });
        this.addLoading = false;
        return;
      } else if (para.realname == null) {
        this.$message({
          type: "error",
          message: "请输入姓名"
        });
        this.addLoading = false;
        return;
      } else if (para.staffType == null) {
        this.$message({
          type: "error",
          message: "请选择用户类型"
        });
        this.addLoading = false;
        return;
      } else if (para.department == "" || para.department == null) {
        this.$message({
          type: "error",
          message: "请选择所属部门"
        });
        this.addLoading = false;
        return;
      } else if (para.leaderInfo == "" || para.leaderInfo == null) {
        this.$message({
          type: "error",
          message: "请选择上级领导"
        });
        this.addLoading = false;
        return;
      }
       InsUpdUserInfo(para).then(res => {
       if (res.data.code == 200 && res.data.status) {
          this.$message({
            type: "success",
            message: "保存成功"
          });
          this.addLoading = false;
          this.$refs["addForm"].resetFields();
          this.addFormVisible = false;
          this.getUserListPage();
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
    handleCurrentChange(val) {
      this.page = val;
      this.getUserListPage();
    }, //重置
    reset() {
      this.filters = {};
      this.getUserListPage();
    },
    //获取部门
    getdepartment() {
      let para = {};
      getDepartments(para).then(res => {
        this.departmentOptions = res.data;
      });
    }, //获取用户类型
    getStaffType() {
      let para = {};
      getStaffType(para).then(res => {
        //console.log(res);
        this.list = res.data.staffTypeList.map(item => {
          return { value: item.staffTypeRef, label: item.staffTypeDesc };
        });
        this.staffTypeOptions = this.list.filter(item => {
          return item.label.toLowerCase();
        });
      });
    }, //获取上级领导
    getleader() {
      let para = {
        staffType: this.addForm.staffType
      };
      this.list = [];
      getleader(para).then(res => {
        //this.addForm.leaderInfo = null;
        this.list = res.data.map(item => {
          return { value: item.username, label: item.realname };
        });
        this.leaderInfoOptions = this.list.filter(item => {
          return item.label.toLowerCase();
        });
      });
    }
  },
  mounted() {
    this.getUserListPage();
    this.getStaffType();
    this.getdepartment();
  }
};
</script>
<style>
.filters-tree {
  position: absolute;
  overflow: auto;
  z-index: 100;
  width: 100%;
  height: 300px;
}
.filters-input {
  display: none;
}
</style>
