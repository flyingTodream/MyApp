package com.app.cotroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.app.model.TUserInfo;

@RestController
@RequestMapping("/api")
public class LoginController {

	@RequestMapping("user/doLogin")
	public JSONObject doLogin(TUserInfo entity) {
		JSONObject json = new JSONObject();
		System.out.println(entity.gettName());
		json.put("res", entity.gettName());
		return json;
	}
	
}
