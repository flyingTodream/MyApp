package com.app.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.app.model.TInfo;
import com.app.service.ImgService;
import com.app.service.LDetailService;
import com.app.service.LineService;
import com.app.service.TInfoService;
import com.common.CommonContext;
import com.common.utils.HttpReq;

@RestController
@RequestMapping("api/")
public class HomeController {

	@Autowired
	private TInfoService tInfoServiceImpl;
	
	@Autowired
	private ImgService imgServiceImpl;
	
	@Autowired
	private LineService LineServiceImpl;
	
	@Autowired
	private LDetailService LDetailServiceImpl;
	
	//首页推荐景点
	@RequestMapping("home/homeSearch")
	public JSONObject homeSearch(String kw) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			if(kw == null) {
				json.put("list", tInfoServiceImpl.homeSelectInfo(null));
			}else {
				json.put("list", tInfoServiceImpl.searchInfo(kw));
			}
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
	
	//获取路线详细内容
		@RequestMapping("line/getLineDetail")
		public JSONObject getLineDetail(Integer lId) {
			JSONObject json = new JSONObject();
			json.put("code", CommonContext.HTTP_SUCCESS);
			try {
				json.put("LineDetailList",LDetailServiceImpl.getDetailList(lId));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				json.put("code", CommonContext.HTTP_ERROR);
			}
			return json;
		}
		//获取目的地的经纬度
		@RequestMapping("map/getMap")
		public static JSONObject getMap(String address) {
			JSONObject res = new JSONObject();
			
			String url = "https://restapi.amap.com/v3/geocode/geo?address="+address+"&key=2ae85cab2657192fb7b16af76fc1b1aa";
			
			JSONObject json = HttpReq.getXpath(url);
			JSONArray geocodes = (JSONArray) json.get("geocodes");
			JSONObject obj = (JSONObject)geocodes.get(0);
			res.put("res", obj.get("location").toString().substring(0, obj.get("location").toString().length()-1));
			//System.out.println(res);
			return res;
		}
}
