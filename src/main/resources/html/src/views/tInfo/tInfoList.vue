<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.kw" placeholder="请输入关键词搜索"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" plain v-on:click="getInfoList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="dialogVisible = true">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>
<el-dialog
  title="新增"
  :visible.sync="dialogVisible"
  width="30%"
  :before-close="handleClose">
  <el-input v-model="newName" placeholder="请输入名称"></el-input>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="addtInfo">确 定</el-button>
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
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column type="index" width="60"></el-table-column>
      <el-table-column align="center" prop="tInfoTitle" label="景点名称" min-width="150"></el-table-column>
      <el-table-column align="center" prop="tInfoAddress" label="地址" min-width="150"></el-table-column>
      <el-table-column align="center" prop="tInfoMoney" label="门票" min-width="110"></el-table-column>
            <el-table-column align="center" label="推荐路线" min-width="180">
        <template slot-scope="scope">
          <el-button size="small" @click="editLineList(scope.$index, scope.row)">编辑</el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" min-width="180">
        <template slot-scope="scope">
          <el-button size="small" @click="handleCk(scope.$index, scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deltInfo(scope.row.tInfoId)" plain>删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" @click="batchRemove" :disabled="this.dataList.length===0">批量删除</el-button>
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
import { getInfoList, deltInfo ,addInfo} from "../../api/api";

export default {
  data() {
    return {
      filters: {
        name: ""
      },
      newName:"",
      dialogVisible:false,
      dataList: [],
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
    handleCurrentChange(val) {
      this.page = val;
      this.getInfoList();
    },
    //
    getInfoList() {
      this.listLoading = true;
      let para = {
        page: this.page,
        kw: this.filters.kw,
        token: sessionStorage.getItem("token")
      };
      //console.log(para);
      setTimeout(() => {
        //NProgress.start();
        getInfoList(para)
          .then(res => {
           // console.log(res);
            this.total = res.data.list.total;
            this.dataList = res.data.list.tInfo;
            this.listLoading = false;
          })
          .catch(err => {
            this.logining = false;
            this.$message({
              message: "网络错误",
              type: "error"
            });
          });
      }, 500);
    },
    handleCk: function(index, row) {
      this.$router.push({
        name: "编辑景点",
        params: { tId: row.tInfoId}
      });
    },editLineList:function(index, row){
    this.$router.push({
        name: "推荐路线",
        params: { tId: row.tInfoId}
      });
    },
    selsChange: function(sels) {
      this.sels = sels;
    },
    addtInfo:function(){
        if(this.newName == ""){
            return;
        }else{
            let para = {
                name:this.newName,
                token: sessionStorage.getItem("token")
            };
            addInfo(para).then(res=>{
                if(res.data.status == 0){
                    this.$message({
                    message: "添加成功",
                    type: "success"
                    });
                    this.newName = "";
                    this.dialogVisible = false;
                    this.getInfoList();
                }else if(res.data.status == 401){
                    this.$message({
                        message: "登录超时，请重新登录",
                        type: "error"
                        }); 
                        this.$router.push({ path: '/login' });
                }
            }).catch(err=>{
                this.$message({
                message: "网络错误",
                type: "error"
                });
            })
        }
    },
    isDisable(index, row) {
      if (row.status == 2) {
        return false;
      } else {
        return true;
      }
    }, //批量删除
    batchRemove: function() {
      if (this.sels.length == 0) {
        this.$message({
          message: "请选择列",
          type: "error"
        });
        return;
      }

      this.$confirm("确认删除选中记录吗？", "提示", {
        type: "warning"
      }).then(() => {
          this.listLoading = true;
            for (var i = 0; i < this.sels.length; i++) {
                this.deltInfo(this.sels[i].tInfoId);
            }
            this.listLoading = false;
            this.$message({
                 message: "删除成功",
                 type: "success"
                 }); 
        });
    },deltInfo:function(tInfoId){
        this.listLoading = true;
        let para = {
            tInfoId:tInfoId,
            token: sessionStorage.getItem("token")
        };
             deltInfo(para).then(res => {
                 if(res.data.status == 0){
                        this.getInfoList();
                 }else{
                     this.$message({
                        message: "登录超时，请重新登录",
                        type: "error"
                        }); 
                        this.$router.push({ path: '/login' });
                 }
          }).catch(err => {
            this.logining = false;
            this.$message({
              message: "网络错误",
              type: "error"
            });
          });
    },handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      }
  },
  mounted() {
    this.getInfoList();
  }
};
</script>

<style scoped>
</style>