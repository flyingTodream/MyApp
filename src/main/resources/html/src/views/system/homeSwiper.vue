<template>
  <section>
          <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
          <el-button type="primary" plain :disabled="imgList.length >= 4" @click="adddialogVisible = true">新增</el-button>

      </el-form>
    </el-col>
        
<el-dialog
  title="上传"
  :visible.sync="adddialogVisible"
  width="30%">
      <el-upload
  class="avatar-uploader"
  :action="upLoadurl"
  :show-file-list="false"
  :on-success="addhandleAvatarSuccess"
  :before-upload="addbeforeAvatarUpload">
  <img v-if="img" :src="img" class="avatar">
  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
</el-upload>
  <span slot="footer" class="dialog-footer">
    <el-button @click="adddialogVisible = false">取 消</el-button>
    <el-button type="primary">确 定</el-button>
  </span>
</el-dialog>
<el-col>
           <el-table
      :data="imgList"
      highlight-current-row
      v-loading="listLoading"
      style="width: 100%;min-width:1019px;"
    >
    <el-table-column type="index" width="60"></el-table-column>
      <el-table-column align="center" prop="tInfoTitle" label="图片" min-width="150">
          <template   slot-scope="scope">            
            <img :src="scope.row.url"  min-width="70" height="70" />
          </template> 
      </el-table-column>
            <el-table-column align="center" label="操作" min-width="180">
        <template slot-scope="scope">
          <el-button size="small" type="danger" @click="delHomeImg(scope.row.id)" plain>删除</el-button>
        </template>
      </el-table-column>
           </el-table>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
    </el-col>
  </section>
</template>
<script>
import util from "../../common/js/util";
//import NProgress from 'nprogress'
import {getHomeImgList,delHomeImg} from "../../api/api";
export default {
  data() {
    return {
      upLoadurl:"",
      dialogImageUrl:"",
      filters: {},
      imgList:[],
      imageUrl: '',
      dialogVisible:false,
      tName:"",
      address:"",
      tInfoIntroduce:"",
      money:"",
      listLoading:true,
      adddialogVisible:false,
      img:""
    };
  },
  methods: {
       addhandleAvatarSuccess(res, file) {
        this.img = URL.createObjectURL(file.raw);
        this.adddialogVisible = false;
        this.img = "";
        this.getHomeImgList();
      }, addbeforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },back() {
      window.history.go(-1);
    },imgUploadError(err, file, fileList){//图片上传失败调用
        console.log(err)
        this.$message.error('上传图片失败!');
      },getHomeImgList:function(){
          getHomeImgList(null).then(res=>{
              this.imgList = res.data;
              this.listLoading = false;
          }).catch(err=>{
            this.$message({
              message: "网络错误",
              type: "error"
            });
        });
      },delHomeImg:function(id){
        let para = {
          id:id
        };
         delHomeImg(para).then(res=>{
            this.$message({
              message: "删除成功",
              type: "success"
            });
            this.listLoading = true;
             this.getHomeImgList();
         }).catch(err=>{
            this.$message({
              message: "网络错误",
              type: "error"
            });
         });
       }
  
  },
  onLoad(){
    this.upLoadurl = "http://localhost/api/admin/addHomeImg";
  },
  mounted() {
     this.getHomeImgList();
     this.upLoadurl = "http://localhost/api/admin/addHomeImg";
  }
};
</script>
<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
