package com.app.cotroller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.app.model.TUserInfo;
import com.app.service.RateService;
import com.app.service.TUserInfoService;
import com.common.CommonContext;
import com.common.utils.JwtUtils;

@RequestMapping("api/")
@RestController
public class AdminRateController {
	

	@Autowired
	private TUserInfoService tUserInfoServiceImpl;

	@Autowired
	private RateService rateServiceImpl;
	
	@RequestMapping("admin/getRateList")
	public JSONObject getRateList(@RequestBody(required = false) Map<String, Object> map) {
		String tInfoTitle = null;
		if(map.get("tInfoTitle")!=null) {
			tInfoTitle = map.get("tInfoTitle").toString();
		}
		String token = map.get("token").toString();
		TUserInfo userInfo = tUserInfoServiceImpl.selectByPrimaryKey(Integer.valueOf(JwtUtils.getId(token)));
		if(userInfo.gettAnswer().equals(CommonContext.USER_TYPE_ADMIN)) {
			return rateServiceImpl.getRateListAdmin(Integer.valueOf(JwtUtils.getId(token)), tInfoTitle, Integer.valueOf(map.get("page").toString()));
			
		}else {
			return rateServiceImpl.getRateListAdmin(null, tInfoTitle, Integer.valueOf(map.get("page").toString()));
			
		}
		
	}
	
}
