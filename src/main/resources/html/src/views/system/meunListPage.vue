<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-button type="primary" @click="dialogVisible = true">新增父节点</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-dialog title="新增父节点" :visible.sync="dialogVisible" width="30%">
      <el-form :inline="true" :model="addMenu">
        <el-input v-model="addMenu.name" placeholder="父节点名称"></el-input>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addFatherMenu">确 定</el-button>
      </span>
    </el-dialog>

    <!--列表-->
    <el-table
      :data="menuList"
      highlight-current-row
      v-loading="listLoading"
      @selection-change="selsChange"
      style="width: 100%;min-width:1019px;"
    >
      <el-table-column type="index" width="60"></el-table-column>
      <el-table-column prop="name" align="center" label="name" min-width="200"></el-table-column>
      <el-table-column align="center" label="操作" min-width="180">
        <template slot-scope="scope">
          <el-button size="small" @click="handleAdd(scope.$index, scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--新增界面-->
    <el-dialog
      size="large"
      title="编辑"
      :visible.sync="outerVisible"
      :close-on-click-modal="false"
      width="1000px"
    >
      <el-button class="children-add" type="primary" @click="innerVisible = true">新增</el-button>

      <el-dialog
        title="新增"
        top="5%"
        :visible.sync="innerVisible"
        :modal="false"
        size="small"
        style="height:700px;"
        width="600px"
      >
        <el-form :inline="true" :model="filters">
          <el-form-item>父节点名称：
            <el-input style="width:415px;" v-model="filters.pname" placeholder="父节点名称"></el-input>
          </el-form-item>

          <el-form-item>
            <el-input style="width:500px;" v-model="filters.name" placeholder="name"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input style="width:500px;" v-model="filters.path" placeholder="path"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false">取 消</el-button>
          <el-button type="primary" @click="addchildrenMenu">确 定</el-button>
        </span>
      </el-dialog>
      <el-table
        :data="childenMenuList"
        highlight-current-row
        v-loading="listLoading"
        @selection-change="selsChange"
        style="width: 100%;"
      >
        <el-table-column type="index" width="60"></el-table-column>

        <el-table-column label="name" prop="name" min-width="110" align="center">
          <template slot-scope="{row,$index}">
            <el-input class="edit-cell" v-if="showEdit[$index]" v-model="row.name"></el-input>
            <span v-if="!showEdit[$index]">{{row.name}}</span>
          </template>
        </el-table-column>
        <el-table-column label="path" prop="path" min-width="110" align="center">
          <template slot-scope="{row,$index}">
            <el-input class="edit-cell" v-if="showEdit[$index]" v-model="row.path"></el-input>
            <span v-if="!showEdit[$index]">{{row.path}}</span>
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
    </el-dialog>
  </section>
</template>

<script>
import util from "../../common/js/util";
//import NProgress from 'nprogress'
import {
  getMenuList,
  getChildrenMenuList,
  InsUpdMenu,
  delMenu
} from "../../api/api";

export default {
  data() {
    return {
      filters: {},
      addMenu: {},
      pid: "",
      showEdit: [], //显示编辑框
      showBtn: [],
      outerVisible: false,
      innerVisible: false,
      dialogVisible: false,
      childenMenuList: [],
      menuList: [],
      page: 1,
      listLoading: false,
      sels: [], //列表选中列

      addFormVisible: false, //新增界面是否显示
      addLoading: false,
      addFormRules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }]
      },
      //新增界面数据
      addForm: {}
    };
  },
  methods: {
    //点击编辑
    handleEdit(index, row) {
      this.showEdit[index] = true;
      this.showBtn[index] = true;
      this.$set(this.showEdit, row, true);
      this.$set(this.showBtn, row, true);
      this.getChildrenMenuList(this.id);
    },
    //取消编辑
    handleCancel(index, row) {
      this.$set(this.showEdit, row, true);
      this.$set(this.showBtn, row, true);
      this.showEdit[index] = false;
      this.showBtn[index] = false;
      this.getChildrenMenuList(this.id);
    },

    getChildrenMenuList(id) {
      let para = { id: id };
      //alert(id);
      //console.log(para);
      getChildrenMenuList(para).then(res => {
        //console.log(res.data);
        if (res.data.code == 500) {
          this.$message({
            type: "error",
            message: "网络请求错误"
          });
        } else {
          //console.log(res);
          this.childenMenuList = res.data.menuListPid;
          this.listLoading = false;
        }
      });
    },

    //获取工作计划列表
    getMenuList() {
      this.listLoading = true;
      let para = {};
      setTimeout(() => {
        //NProgress.start();
        getMenuList(para).then(res => {
          //console.log(res.data);
          if (res.data.code == 500) {
            this.$message({
              type: "error",
              message: "网络请求错误"
            });
          } else {
            //console.log(res);
            this.menuList = res.data.menuList;
            this.listLoading = false;
          }
        });
      }, 500);
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
    handleAdd: function(index, row) {
      this.id = row.id;
      this.addFormVisible = true;
      this.outerVisible = true;
      this.filters.pname = row.name;
      this.getChildrenMenuList(row.id);
    },
    selsChange: function(sels) {
      this.sels = sels;
    }, //更新子菜单
    handleUpdate(index, row) {
      let para = {
        id: row.id,
        pid: this.id,
        name: row.name,
        path: row.path,
        component: row.component,
        token: sessionStorage.getItem("token")
      };
      if (para.name == "" || para.name == null) {
        this.$message({
          type: "error",
          message: "请填写name"
        });
        return;
      } else if (para.path == "" || para.path == null) {
        this.$message({
          type: "error",
          message: "请填写path"
        });
        return;
      }

      InsUpdMenu(para).then(res => {
        if (res.data.code == 200 && res.data.status) {
          this.$message({
            type: "success",
            message: "保存成功"
          });
          this.$set(this.showEdit, row, true);
          this.$set(this.showBtn, row, true);
          this.showEdit[index] = false;
          this.showBtn[index] = false;
          this.getChildrenMenuList(this.id);
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
    }, //新增子菜单
    addchildrenMenu() {
      let para = {
        id: "",
        pid: this.id,
        name: this.filters.name,
        path: this.filters.path,
        component: "",
        pname: this.filters.pname,
        token: sessionStorage.getItem("token")
      };
      if (para.name == "" || para.name == null) {
        this.$message({
          type: "error",
          message: "请填写name"
        });
        return;
      } else if (para.path == "" || para.path == null) {
        this.$message({
          type: "error",
          message: "请填写path"
        });
        return;
      }

      InsUpdMenu(para).then(res => {
        if (res.data.code == 200 && res.data.status) {
          this.$message({
            type: "success",
            message: "保存成功"
          });
          this.filters.name = "";
          this.filters.path = "";
          this.innerVisible = false;
          this.getChildrenMenuList(this.id);
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
    //删除子菜单
    handleDelete(index, row) {
      let para = {
        id: row.id,
        token: sessionStorage.getItem("token")
      };
      delMenu(para).then(res => {
        if (res.data.code == 200 && res.data.num == "N" && res.data.status) {
          this.$message({
            type: "error",
            message: "父节点下含有子节点，不能删除，请先删除子节点"
          });
          return;
        } else if (
          res.data.code == 200 &&
          res.data.num == "Y" &&
          res.data.status
        ) {
          this.$message({
            type: "success",
            message: "删除成功"
          });
          this.getMenuList();
          this.getChildrenMenuList(this.id);
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
    addFatherMenu() {
      let param = {
        name: this.addMenu.name,
        id: "",
        pid: "",
        path: "",
        component: "",
        token: sessionStorage.getItem("token")
      };
      if (param.name == "" || param.name == null) {
        this.$message({
          type: "info",
          message: "请输入父节点名称"
        });
        return;
      }
      InsUpdMenu(param).then(res => {
        if (res.data.code == 200 && res.data.status) {
          this.$message({
            type: "success",
            message: "保存成功"
          });
          this.dialogVisible = false;
          this.getMenuList();
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
    }
  },
  mounted() {
    this.getMenuList();
  }
};
</script>

<style scoped>
.children-add {
  margin-bottom: 10px;
}
</style>