<template>
  <section>
    <!--列表-->
    <el-table
      :data="dataList"
      highlight-current-row
      v-loading="listLoading"
      style="width: 100%;min-width:1019px;"
    >
      <el-table-column type="index" width="60"></el-table-column>
      <el-table-column align="center" prop="tInfoTitle" label="景点名称" min-width="100"></el-table-column>
      <el-table-column align="center" prop="tName" label="姓名" min-width="100"></el-table-column>
       <el-table-column align="center" prop="tInfoMoney" label="单价" min-width="150"></el-table-column>
       <el-table-column align="center" prop="oNumber" label="人数" min-width="100"></el-table-column>
       <el-table-column align="center" prop="oMoney" label="订单总价" min-width="150"></el-table-column>
       <el-table-column align="center" prop="isOrder" :formatter="formatOder" label="是否付款" min-width="70"></el-table-column>
       <el-table-column align="center" prop="isEvaluation" :formatter="formatRate" label="是否评价" min-width="70"></el-table-column>
       <el-table-column align="center" prop="oTime" label="时间" min-width="150"></el-table-column>
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
import { getOrderList} from "../../api/api";

export default {
  data() {
    return {
      filters: {
        name: ""
      },
      newName:"",
      dataList: [],
      total: 0,
      page: 1,
      listLoading: false,
    };
  },
  methods: {
    formatOder:function(row, column){
        if(row.isOrder == 0){
            return "是"
        }else{
          return "否";
        }
    },
    formatRate:function(row, column){
        if(row.isEvaluation == 0){
            return "是"
        }else{
          return "否";
        }
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getRateList();
    },
    //
    getOrderList() {
      this.listLoading = true;
      let para = {
        page: this.page,
        tInfoTitle: this.filters.kw,
        token: sessionStorage.getItem("token")
      };
      //console.log(para);
      setTimeout(() => {
        //NProgress.start();
        getOrderList(para)
          .then(res => {
            console.log(res);
            this.total = res.data.total;
            this.dataList = res.data.orderList;
            this.listLoading = false;
          }).catch(err => {
            this.logining = false;
            this.$message({
              message: "网络错误",
              type: "error"
            });
          });
      }, 500);
    }
  },
  mounted() {
    this.getOrderList();
  }
};
</script>

<style scoped>
</style>