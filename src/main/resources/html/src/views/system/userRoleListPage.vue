<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.username" placeholder="用户账号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.realname" placeholder="用户名称"></el-input>
        </el-form-item>
        <el-input class="filters-input" v-model="filters.department" type="hidden"></el-input>
        <el-form-item>
          <el-input
            v-model="filters.departmentName"
            placeholder="所属部门"
            readonly="readonly"
            @click.native="isShowSelect = !isShowSelect"
          >></el-input>
          <el-tree
            class="filters-tree"
            :data="departmentoptions"
            :props="defaultProps"
            v-if="isShowSelect"
            @node-click="handleNodeClick"
          ></el-tree>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" v-on:click="userRoleListPage">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <el-table
      :data="userRoleList"
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
      <el-table-column prop="username" align="center" label="用户账号" min-width="70"></el-table-column>
      <el-table-column prop="realname" align="center" label="用户姓名" min-width="80"></el-table-column>
      <el-table-column prop="typeDesc.staffTypeDesc" align="center" label="用户类型" min-width="80"></el-table-column>
      <el-table-column
        prop="departmentInfo.departmentName"
        align="center"
        label="所属部门"
        min-width="80"
      ></el-table-column>
      <el-table-column label="用户角色" prop="description" min-width="200" align="center">
        <template slot-scope="{row,$index}">
          <el-select
            v-if="showEdit[$index]"
            multiple
            collapse-tags
            v-model="roleSelect"
            placeholder="请选择"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>

          <span v-if="!showEdit[$index]">{{row.description}}</span>
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
  userRoleListPage,
  getDepartments,
  staffWorkType,
  getRoleList,
  updataRoleList
} from "../../api/api";
export default {
  data() {
    return {
      isShowSelect: false,
      filters: {},
      options: [],
      roleSelect: [],
      departmentoptions: [],
      userRoleList: [],
      showEdit: [], //显示编辑框
      showBtn: [],
      showBtnOrdinary: true,
      total: 0,
      page: 1,
      defaultProps: {
        id: "id",
        label: "departmentName",
        children: "children"
      },
      listLoading: false,
      month: "",
      addFormVisible: false, //新增界面是否显示
      //新增界面数据
      addForm: {
        workType: ""
      },
      addLoading: false
    };
  },

  methods: {
    //树形结构点击事件
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
    handleCurrentChange(row, event, column) {
      //this.optionsRest();
      // console.log(row, event, column);
    },
    //获取用户角色列表列表
    userRoleListPage() {
      this.listLoading = true;
      let para = {
        page: this.page,
        username: this.filters.username,
        realname: this.filters.realname,
        department: this.filters.department
      };
      userRoleListPage(para).then(res => {
        // console.log(res);
        this.total = res.data.total;
        this.userRoleList = res.data.rows;
        this.listLoading = false;
      });

      //console.log(this.$route);
    },
    //更新
    handleUpdate(index, row) {
      //console.log(this.roleSelect);
      let para = {
        username: row.username,
        roleList: this.roleSelect.join(","),
        token: sessionStorage.getItem("token")
      };
      updataRoleList(para).then(res => {
        if (res.data.code == 200 && res.data.status) {
          this.$message({
            type: "success",
            message: "保存成功"
          });
          this.$set(this.showEdit, row, true);
          this.$set(this.showBtn, row, true);
          this.showEdit[index] = false;
          this.showBtn[index] = false;
          this.userRoleListPage();
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
      getRoleList(para).then(res => {
        //console.log(res);
        this.list = res.data.map(item => {
          //console.log(item);

          return { value: item.id, label: item.description };
        });
        this.options = this.list.filter(item => {
          return item.label.toLowerCase();
        });
      });
    },
    getDepartments() {
      let para = {};
      getDepartments(para).then(res => {
        //console.log(res);
        this.departmentoptions = res.data;
      });
    }, //点击编辑
    handleEdit(index, row) {
      var arr = row.roleId.split(",");
      this.roleSelect = arr.map(Number);
      //console.log();
      this.showEdit[index] = true;
      this.showBtn[index] = true;
      this.$set(this.showEdit, row, true);
      this.$set(this.showBtn, row, true);
      this.userRoleListPage();
    },
    //取消编辑
    handleCancel(index, row) {
      this.$set(this.showEdit, row, true);
      this.$set(this.showBtn, row, true);
      this.showEdit[index] = false;
      this.showBtn[index] = false;
      this.userRoleListPage();
    },
    //提交
    WorkPlanSubmit() {},
    back() {
      window.history.go(-1);
    },
    handleCurrentChange(val) {
      this.page = val;
      this.userRoleListPage();
    },
    //重置
    reset() {
      this.filters = {};
      this.userRoleListPage();
    }
  },
  mounted() {
    this.userRoleListPage();
    this.getOptions();
    this.getDepartments();
  }
};
</script>
<style>
.el-tree-node__content {
  height: 35px;
}
</style>
