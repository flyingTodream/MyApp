<template>
  <section>
    <el-table
      :data="roleMenuList"
      tooltip-effect="dark"
      style="width: 100%"
      v-loading="listLoading"
      align="center"
    >
      <el-table-column label="#" width="60" align="center" header-align="center">
        <template slot-scope="{row,$index}">
          <span>{{$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="roleName" align="center" label="角色名称" min-width="80"></el-table-column>
      <el-table-column prop="description" align="center" label="备注" min-width="80"></el-table-column>
      <el-table-column label="权限" prop="namelist" min-width="200" align="center">
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

          <span v-if="!showEdit[$index]">{{row.namelist}}</span>
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
  getRoleMenuListPage,
  getRoleList,
  updataRoleList,
  getMenu,
  updataRoleMenu
} from "../../api/api";
export default {
  data() {
    return {
      isShowSelect: false,
      filters: {},
      options: [],
      roleSelect: [],
      departmentoptions: [],
      roleMenuList: [],
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
    getRoleMenuListPage() {
      this.listLoading = true;
      let para = {
        page: this.page,
        username: this.filters.username,
        realname: this.filters.realname,
        department: this.filters.department
      };
      getRoleMenuListPage(para).then(res => {
        //console.log(res);
        this.roleMenuList = res.data.roleMenulist;
        this.listLoading = false;
      });

      //console.log(this.$route);
    },
    //更新
    handleUpdate(index, row) {
      //console.log(this.roleSelect);
      let para = {
        roleId: row.roleId,
        menuList: this.roleSelect.join(","),
        token: sessionStorage.getItem("token")
      };
      if (para.menuList == "" || para.menuList == null) {
        this.$message({
          type: "info",
          message: "请选择权限"
        });
        return;
      }
      updataRoleMenu(para).then(res => {
        if (res.data.code == 200 && res.data.status) {
          this.$message({
            type: "success",
            message: "保存成功"
          });
          this.$set(this.showEdit, row, true);
          this.$set(this.showBtn, row, true);
          this.showEdit[index] = false;
          this.showBtn[index] = false;
          this.getRoleMenuListPage();
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
      getMenu(para).then(res => {
        this.list = res.data.menuList.map(item => {
          return { value: item.id, label: item.name };
        });
        this.options = this.list.filter(item => {
          return item.label.toLowerCase();
        });
      });
    }, //点击编辑
    handleEdit(index, row) {
      var arr = row.idlist.split(",");
      this.roleSelect = arr.map(Number);
      //console.log();
      this.showEdit[index] = true;
      this.showBtn[index] = true;
      this.$set(this.showEdit, row, true);
      this.$set(this.showBtn, row, true);
      this.getRoleMenuListPage();
    },
    //取消编辑
    handleCancel(index, row) {
      this.$set(this.showEdit, row, true);
      this.$set(this.showBtn, row, true);
      this.showEdit[index] = false;
      this.showBtn[index] = false;
      this.getRoleMenuListPage();
    },
    back() {
      window.history.go(-1);
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getRoleMenuListPage();
    },
    //重置
    reset() {
      this.filters = {};
      this.getRoleMenuListPage();
    }
  },
  mounted() {
    this.getRoleMenuListPage();
    this.getOptions();
  }
};
</script>
<style>
.el-tree-node__content {
  height: 35px;
}
</style>
