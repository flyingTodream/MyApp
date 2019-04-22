<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-button type="primary" @click="dialogVisible = true">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-dialog title="新增" :visible.sync="dialogVisible" width="30%">
      <el-form :inline="true">
        <el-input v-model="lName" placeholder="路线名称"></el-input>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addLine">确 定</el-button>
      </span>
    </el-dialog>

    <!--列表-->
    <el-table
      :data="dataList"
      highlight-current-row
      v-loading="listLoading"
      @selection-change="selsChange"
      style="width: 100%;min-width:1019px;"
    >
      <el-table-column type="index" width="60"></el-table-column>
      <el-table-column prop="lName" align="center" label="名称" min-width="200"></el-table-column>
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
        <el-form :inline="true">
          <el-form-item>
            <el-input style="width:415px;" v-model="lLine" placeholder="地点"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false">取 消</el-button>
          <el-button type="primary" @click="addLineDetail">确 定</el-button>
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

        <el-table-column label="Line" prop="lLine" min-width="110" align="center">
          <template slot-scope="{row,$index}">
            <el-input class="edit-cell" v-if="showEdit[$index]" v-model="row.lLine"></el-input>
            <span v-if="!showEdit[$index]">{{row.lLine}}</span>
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
              @click.native="delLineDetail($index, row)"
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
  getLineList,
  addLine,
  delLine,
  getlineDetail,
  updateLineDetail,
  delLineDetail,
  addLineDetail
} from "../../api/api";

export default {
  data() {
    return {
        lName:"",
      filters: {},
      addMenu: {},
      pid: "",
      showEdit: [], //显示编辑框
      showBtn: [],
      outerVisible: false,
      innerVisible: false,
      dialogVisible: false,
      childenMenuList: [],
      dataList: [],
      page: 1,
      listLoading: false,
      sels: [], //列表选中列

      addFormVisible: false, //新增界面是否显示
      addLoading: false,
      addFormRules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }]
      },
      //新增界面数据
      addForm: {},
      lId:"",
      lLine:""
    };
  },
  methods: {
    //点击编辑
    handleEdit(index, row) {
      this.showEdit[index] = true;
      this.showBtn[index] = true;
      this.$set(this.showEdit, row, true);
      this.$set(this.showBtn, row, true);
      this.getlineDetail(this.lId);
    },
    //取消编辑
    handleCancel(index, row) {
      this.$set(this.showEdit, row, true);
      this.$set(this.showBtn, row, true);
      this.showEdit[index] = false;
      this.showBtn[index] = false;
      this.getlineDetail(this.lId);
    },

    getlineDetail(id) {
        this.lId = id;
      let para = { lId: id };
      //alert(id);
      //console.log(para);
      getlineDetail(para).then(res => {
         this.childenMenuList = res.data;
          this.listLoading = false;
        
      }).catch(err=>{
          this.$message({
            type: "error",
            message: "网络请求错误"
          });
      });
    },

    //获取路线集合
    getLineList(tId) {
      this.listLoading = true;
      let para = {
        tId:tId
      };
      setTimeout(() => {
        //NProgress.start();
        getLineList(para).then(res => {
           this.dataList = res.data.lineList;
            this.listLoading = false;
          
        }).catch(err=>{
            this.$message({
              type: "error",
              message: "网络请求错误"
            });
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
      this.addFormVisible = true;
      this.outerVisible = true;
      this.filters.pname = row.name;
      this.getlineDetail(row.lId);
    },
    selsChange: function(sels) {
      this.sels = sels;
    }, //更新子菜单
    handleUpdate(index, row) {
      let para = {
        detailId: row.detailId,
        lLine:row.lLine
      };
    console.log(para);
      updateLineDetail(para).then(res => {
        this.$message({
            type: "success",
            message: "保存成功"
          });
          this.$set(this.showEdit, row, true);
          this.$set(this.showBtn, row, true);
          this.showEdit[index] = false;
          this.showBtn[index] = false;
          this.getlineDetail(this.lId);
      }).catch(err=>{
          this.$message({
            type: "error",
            message: "网络请求错误"
          });
      });
    }, //新增子菜单
    addLineDetail() {
      let para = {
          lId:this.lId,
          lLine:this.lLine
      };
      if (para.lLine == "" || para.lLine == null) {
        this.$message({
          type: "error",
          message: "地点"
        });
        return;
      }

      addLineDetail(para).then(res => {
        this.$message({
            type: "success",
            message: "保存成功"
          });
            this.lLine = "";
          this.innerVisible = false;
          this.getlineDetail(this.lId);
      }).catch(err=>{
          this.$message({
            type: "error",
            message: "网络请求错误"
          });
      });
    },
    //删除子菜单
    handleDelete(index, row) {
      let para = {
        lId: row.lId
      };

      delLine(para).then(res => {
        if (res.data.status == 1) {
          this.$message({
            type: "error",
            message: "父节点下含有子节点，不能删除，请先删除子节点"
          });
          return;
        } else{
          this.$message({
            type: "success",
            message: "删除成功"
          });
          this.getLineList(this.$route.params.tId);
          this.getChildrenMenuList(this.id);
        }
      }).catch(err=>{
          this.$message({
            type: "error",
            message: "网络请求错误"
          });
      });
    },
    addLine() {
      let param = {
          tId:this.$route.params.tId,
          lName:this.lName
      };
      if (this.lName == "" || this.lName == null) {
        this.$message({
          type: "info",
          message: "请输入路线名称"
        });
        return;
      }
      addLine(param).then(res => {
        this.dialogVisible = false;
        this.lName = "";
        this.getLineList(this.$route.params.tId);

      }).catch(err=>{
          this.$message({
            type: "error",
            message: "网络请求错误"
          });
      });
    },delLineDetail:function(index, row){

        let para = {
            detailId:row.detailId
        };
        console.log(para);
        delLineDetail(para).then(res=>{
            this.$message({
            type: "success",
            message: "删除成功"
          });
         this.getlineDetail(this.lId);
        }).catch(err=>{
            this.$message({
            type: "error",
            message: "网络请求错误"
          });
        });
    }
  },
  mounted() {
    this.getLineList(this.$route.params.tId);
  }
};
</script>

<style scoped>
.children-add {
  margin-bottom: 10px;
}
</style>