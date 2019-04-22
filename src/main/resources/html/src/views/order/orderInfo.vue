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
      <el-table-column align="center" prop="oMoney" label="总订单额" min-width="100"></el-table-column>
    </el-table>
  </section>
</template>

<script>
import util from "../../common/js/util";
//import NProgress from 'nprogress'
import { getOrderSum} from "../../api/api";

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
    getOrderSum() {
      this.listLoading = true;
      let para = {
      };
      //console.log(para);
      setTimeout(() => {
        //NProgress.start();
        getOrderSum(para)
          .then(res => {
            this.dataList = res.data;
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
    this.getOrderSum();
  }
};
</script>

<style scoped>
</style>