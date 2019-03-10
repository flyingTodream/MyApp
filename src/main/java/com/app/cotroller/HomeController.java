package com.app.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.app.model.TInfo;
import com.app.service.ImgService;
import com.app.service.LineService;
import com.app.service.TInfoService;
import com.common.CommonContext;

@RestController
@RequestMapping("api/")
public class HomeController {

	@Autowired
	private TInfoService tInfoServiceImpl;
	
	@Autowired
	private ImgService imgServiceImpl;
	
	@Autowired
	private LineService LineServiceImpl;
	
	//首页推荐景点
	@RequestMapping("home/homeSearch")
	public JSONObject homeSearch(TInfo entity) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			json.put("list", tInfoServiceImpl.homeSelectInfo(entity));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}
	
	//获取轮播图
	@RequestMapping("detail/getImgList")
	public JSONObject getDetailImg(Integer tId) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			json.put("imgList", imgServiceImpl.selImg(tId));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}
	
	//获取路线
	@RequestMapping("line/getLineList")
	public JSONObject getLineList(Integer tId) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			json.put("LineList", LineServiceImpl.getLineList(tId));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}
	
}
