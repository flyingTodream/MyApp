package com.app.cotroller.admin;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.app.model.HomeImg;
import com.app.service.HomeImgService;
import com.common.utils.FileUpload;

@RequestMapping("api/")
@RestController
public class AdminHomeImgControoler {

	@Autowired
	private HomeImgService homeImgServiceImpl;
	
	@Value("${url}")
    private String url;
	/**
	 * 获取首页轮播图
	 * @return
	 */
	@RequestMapping("admin/getHomeImgList")
	public List<HomeImg> getHomeImgList(){
		return homeImgServiceImpl.getHomeImgList();
	}
	
	/**
	 * 删除
	 * @return
	 */
	
	@RequestMapping("admin/delHomeImg")
	public void delHomeImg(@RequestBody(required=false) Map<String, Object> map){
		String id = map.get("id").toString();
		homeImgServiceImpl.deleteByPrimaryKey(Integer.valueOf(id));
	}
	
	/**
	 * 添加图片
	 * @return
	 * @throws Exception 
	 * @throws FileNotFoundException 
	 */
	
	@RequestMapping("admin/addHomeImg")
	public void addHomeImg(MultipartHttpServletRequest multiReq,HttpServletRequest req) throws FileNotFoundException, Exception{
		System.out.println(ResourceUtils.getURL("classpath:").getPath()+"-------");
		
		String filePath = "static/img/";
		String fileName = FileUpload.upload(multiReq, ResourceUtils.getURL("classpath:").getPath()+filePath);
		HomeImg entity = new HomeImg();
		entity.setUrl(url+"/img/"+fileName);
		homeImgServiceImpl.insertSelective(entity);
	}
	
}
