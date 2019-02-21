package com.app.cotroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/api")
public class LoginController {

	@RequestMapping("user/doLogin")
	public JSONObject doLogin() {
		JSONObject json = new JSONObject();
		json.put("res", "发生的");
		return json;
	}
	
}
