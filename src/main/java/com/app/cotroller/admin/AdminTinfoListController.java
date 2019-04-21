package com.app.cotroller.admin;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.app.model.Img;
import com.app.model.TInfo;
import com.app.model.TUserInfo;
import com.app.service.ImgService;
import com.app.service.TInfoService;
import com.app.service.TUserInfoService;
import com.common.CommonContext;
import com.common.utils.FileUpload;
import com.common.utils.JwtUtils;

@RestController
@RequestMapping("api/")
public class AdminTinfoListController {

	@Autowired
	private ImgService imgServiceImpl;
	
	@Autowired
	private TInfoService tInfoServiceImpl;

	@Autowired
	private TUserInfoService tUserInfoServiceImpl;

	
	@Value("${url}")
    private String url;
	
	// 首页推荐景点
	@RequestMapping("admin/getInfoList")
	public JSONObject homeSearch(@RequestBody(required = false) Map<String, Object> map) {
		String kw = null;
		String token = map.get("token").toString();
		if (map.get("kw") != null) {
			kw = map.get("kw").toString();
		}
		JSONObject json = new JSONObject();
		TInfo searchEntity = new TInfo();
		searchEntity.settInfoTitle(kw);
		searchEntity.setPage(Integer.valueOf(map.get("page").toString()));
		TUserInfo userInfo = tUserInfoServiceImpl.selectByPrimaryKey(Integer.valueOf(JwtUtils.getId(token)));
		if (userInfo.gettAnswer().equals(CommonContext.USER_TYPE_ADMIN)) {
			searchEntity.setaId(Integer.valueOf(JwtUtils.getId(token)));
		}
		json.put("list", tInfoServiceImpl.selectInfoPage(searchEntity));

		return json;
	}
	
	
	/**
	 * 删除景点信息
	 */
	@RequestMapping("admin/deltInfo")
	public JSONObject deltInfo(@RequestBody(required = false) Map<String, Object> map) {
		String tInfoId = map.get("tInfoId").toString();
		JSONObject json = new JSONObject();
		String token = map.get("token").toString();
		if(JwtUtils.verify(token)) {
			json.put("status", CommonContext.IS_LOGIN);
			tInfoServiceImpl.deleteByPrimaryKey(Integer.valueOf(tInfoId));
			
		}else {
			json.put("status", CommonContext.NO_LOGIN);
		}
		return json;
	}
	
	/**
	 * 添加景点
	 * @param 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("admin/addInfo")
	public JSONObject addInfo(@RequestBody(required = false) Map<String, Object> map) {
		JSONObject json = new JSONObject();
		String token = map.get("token").toString();
		String name = map.get("name").toString();
		if(JwtUtils.verify(token)) {
			json.put("status", CommonContext.IS_LOGIN);
			TInfo entity = new TInfo();
			entity.settInfoTitle(name);
			entity.setaId(Integer.valueOf(JwtUtils.getId(token)));
			tInfoServiceImpl.insertSelective(entity);
		}else {
			json.put("status", CommonContext.NO_LOGIN);
		}
		return json;
	}
	//上传图片
	@RequestMapping(value="admin/uploadImg",method = RequestMethod.POST)
	public void uploadImg(MultipartHttpServletRequest multiReq,HttpServletRequest req) throws Exception {
		System.out.println(ResourceUtils.getURL("classpath:").getPath()+"-------");
		
		System.out.println(req.getParameter("tId"));
		//String path = req.getServletContext().getRealPath("");
		String filePath = "static/img/";
		String fileName = FileUpload.upload(multiReq, ResourceUtils.getURL("classpath:").getPath()+filePath);
		TInfo entity = new TInfo();
		entity.settInfoId(Integer.valueOf(req.getParameter("tId")));
		entity.settInfoImg(url+"/img/"+fileName);
		tInfoServiceImpl.updateByPrimaryKeySelective(entity);
	}
	
	
	/**
	 * 获取景点信息
	 */
	@RequestMapping("admin/getTInfo")
	public TInfo getTInfo(@RequestBody(required = false) Map<String, Object> map) {
		//JSONObject json = new JSONObject();
		String tId = map.get("tId").toString();
		return tInfoServiceImpl.selectByPrimaryKey(Integer.valueOf(tId));
	}
	
	
	/**
	 * 获取轮播图
	 */
	@RequestMapping("admin/getImgList")
	public List<Img> getImgList(@RequestBody(required = false) Map<String, Object> map) {
		String tId = map.get("tId").toString();
		return imgServiceImpl.selImg(Integer.valueOf(tId));
	}
	
	/**
	 * 删除轮播图
	 */
	@RequestMapping("admin/delImg")
	public void delImg(@RequestBody(required = false) Map<String, Object> map) {
		String imgId = map.get("imgId").toString();
		imgServiceImpl.deleteByPrimaryKey(Integer.valueOf(imgId));
	}
	
	
	/**
	 * 更新景点信息
	 */
	@RequestMapping("admin/updateInfo")
	public void updateInfo(@RequestBody(required = false) Map<String, Object> map) {
		String tInfoId = map.get("tInfoId").toString();
		String tInfoTitle = map.get("tInfoTitle").toString();
		String tInfoMoney = map.get("tInfoMoney").toString();
		String tInfoIntroduce = map.get("tInfoIntroduce").toString();
		TInfo entity = new TInfo();
		entity.settInfoId(Integer.valueOf(tInfoId));
		entity.settInfoTitle(tInfoTitle);
		entity.settInfoIntroduce(tInfoIntroduce);
		entity.settInfoMoney(new BigDecimal(tInfoMoney));
		tInfoServiceImpl.updateByPrimaryKeySelective(entity);
	}
	
	/**
	 * 增加轮播图片
	 */
	@RequestMapping(value="admin/addImgList",method = RequestMethod.POST)
	public void addImgList(MultipartHttpServletRequest multiReq,HttpServletRequest req) throws Exception {
		System.out.println(ResourceUtils.getURL("classpath:").getPath()+"-------");
		
		System.out.println(req.getParameter("tId"));
		//String path = req.getServletContext().getRealPath("");
		String filePath = "static/img/";
		String fileName = FileUpload.upload(multiReq, ResourceUtils.getURL("classpath:").getPath()+filePath);
		Img entity = new Img();
		entity.settInfoId(Integer.valueOf(req.getParameter("tId")));
		entity.setImg(url+"/img/"+fileName);
		imgServiceImpl.insertSelective(entity);
	}
}
