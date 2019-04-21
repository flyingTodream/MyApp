package com.app.cotroller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.common.CommonContext;
import com.common.utils.JwtUtils;

@RestController
@RequestMapping("/api")
public class AdminLoginController {
	// 判断是否登录
	@RequestMapping("admin/isLogin")
	public JSONObject isLogin(@RequestBody(required=false) Map<String, Object> map,HttpServletRequest req, HttpServletResponse res) {
		JSONObject json = new JSONObject();
		String token = map.get("token").toString();
		// System.out.println(token);
		if (JwtUtils.verify(token)) {
			json.put("islogin", CommonContext.IS_LOGIN);
		} else {
			json.put("islogin", CommonContext.NO_LOGIN);
		}

		// System.out.println(json);
		return json;
	}
}
