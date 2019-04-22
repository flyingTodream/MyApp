<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
          <el-form-item label="名称"></el-form-item>
          <el-input v-model="tName"></el-input>
          <el-form-item label="地点"></el-form-item>
          <el-input v-model="address"></el-input>
          <el-form-item label="门票"></el-form-item>
          <el-input type="number" v-model="money"></el-input>
        <el-form-item label="图片"></el-form-item>

    <el-upload
  class="avatar-uploader"
  :action="upLoadurl"
  :show-file-list="false"
  :on-success="handleAvatarSuccess"
  :before-upload="beforeAvatarUpload">
  <img v-if="imageUrl" :src="imageUrl" class="avatar">
  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
</el-upload>
<el-form-item label="介绍"></el-form-item>
        <el-input
          resize="none"
          type="textarea"
          v-model="tInfoIntroduce"
          autosize
          placeholder="介绍"
        ></el-input>
          <el-form-item style="margin-top:15px">
<el-form-item label="轮播"></el-form-item>
      
          <el-button type="primary" plain :disabled="imgList.length >= 4" @click="adddialogVisible = true">新增</el-button>

<el-dialog
  title="上传"
  :visible.sync="adddialogVisible"
  width="30%">
      <el-upload
  class="avatar-uploader"
  :action="addImgListUrl"
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


        </el-form-item>
           <el-table
      :data="imgList"
      highlight-current-row
      v-loading="listLoading"
      style="width: 100%;min-width:1019px;"
    >
    <el-table-column type="index" width="60"></el-table-column>
      <el-table-column align="center" prop="tInfoTitle" label="图片" min-width="150">
          <template   slot-scope="scope">            
            <img :src="scope.row.img"  min-width="70" height="70" />
          </template> 
      </el-table-column>
            <el-table-column align="center" label="操作" min-width="180">
        <template slot-scope="scope">
          <el-button size="small" type="danger" @click="delImg(scope.row.imgId)" plain>删除</el-button>
        </template>
      </el-table-column>
           </el-table>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
              </el-form>
    </el-col>
    <!--工具条-->
    <el-col :span="24" align="center" class="toolbar">
      <el-button type="success" @click="sub" >提交</el-button>
      <el-button type="primary" plain @click="back">返回</el-button>
    </el-col>
  </section>
</template>
<script>
import util from "../../common/js/util";
//import NProgress from 'nprogress'
import {getTInfo,getImgList,delImg,updateInfo} from "../../api/api";
export default {
  data() {
    return {
      upLoadurl:"",
      addImgListUrl:"",
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
    handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      }, handleRemove(file, fileList) {
       // console.log("删除")
        //console.log(file, fileList);
      },
       handleAvatarSuccess(res, file) {
         this.getTInfo(this.$route.params.tId);
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      addhandleAvatarSuccess(res, file) {
        this.img = URL.createObjectURL(file.raw);
        this.adddialogVisible = false;
        this.img = "";
         this.getImgList(this.$route.params.tId);
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
      }, beforeAvatarUpload(file) {
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
      },getTInfo:function(tId){
          let para = {
              tId:tId
          };
        getTInfo(para).then(res=>{
            //console.log(res)
            this.tName = res.data.tInfoTitle;
            this.address = res.data.tInfoAddress;
            this.tInfoIntroduce = res.data.tInfoIntroduce;
            this.money = res.data.tInfoMoney;
            this.imageUrl = res.data.tInfoImg;
            this.dialogImageUrl = res.data.tInfoImg;
        }).catch(err=>{
            this.$message({
              message: "网络错误",
              type: "error"
            });
        });
      },getImgList:function(tId){
        let para = {
              tId:tId
          };
          getImgList(para).then(res=>{
              this.imgList = res.data;
              this.listLoading = false;
          }).catch(err=>{
            this.$message({
              message: "网络错误",
              type: "error"
            });
        });
      },delImg:function(imgId){
        let para = {
          imgId:imgId
        };
         delImg(para).then(res=>{
            this.$message({
              message: "删除成功",
              type: "success"
            });
            this.listLoading = true;
            this.getImgList(this.$route.params.tId);
         }).catch(err=>{
            this.$message({
              message: "网络错误",
              type: "error"
            });
         });
       },sub:function(){
         let para = {
           tInfoId:this.$route.params.tId,
           tInfoTitle:this.tName,
           tInfoMoney:this.money,
           tInfoAddress:this.address,
           tInfoIntroduce:this.tInfoIntroduce
         };
         console.log(para);
         updateInfo(para).then(res=>{
           this.$message({
              message: "更新成功",
              type: "success"
            });
            this.getTInfo(this.$route.params.tId);
         }).catch(err=>{
            this.$message({
              message: "网络错误",
              type: "error"
            });
         });
       }
  
  },
  onLoad(){
    this.upLoadurl = "http://localhost/api/admin/uploadImg?tId=" + this.$route.params.tId;
    this.addImgListUrl = "http://localhost/api/admin/addImgList?tId=" + this.$route.params.tId;
  },
  mounted() {
     this.getTInfo(this.$route.params.tId);
     this.getImgList(this.$route.params.tId);
     this.upLoadurl = "http://localhost/api/admin/uploadImg?tId=" + this.$route.params.tId;
     this.addImgListUrl = "http://localhost/api/admin/addImgList?tId=" + this.$route.params.tId;
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
