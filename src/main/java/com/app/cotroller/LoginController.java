package com.app.cotroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.app.model.TUserInfo;
import com.app.service.TUserInfoService;
import com.common.CommonContext;
import com.common.utils.JwtUtils;
import com.common.utils.Md5;

@RestController
@RequestMapping("/api")
public class LoginController {
	@Autowired
	private TUserInfoService tUserInfoServiceImpl;

	// 登录主方法
	@RequestMapping("user/doLogin")
	public JSONObject doLogin(TUserInfo entity) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {

			TUserInfo searchEntity = tUserInfoServiceImpl.selectByPhone(entity.gettPhone());
			if (searchEntity == null) {
				json.put("status", "404");
			} else {
				if (Md5.string2MD5(entity.gettPass()).equals(searchEntity.gettPass())) {
					json.put("token", JwtUtils.sign(searchEntity.gettName(), searchEntity.gettId().toString()));
					json.put("status", "200");
				} else {
					json.put("status", CommonContext.NO_LOGIN);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}

	// 判断是否登录
	@RequestMapping("user/isLogin")
	public JSONObject isLogin(HttpServletRequest req, HttpServletResponse res) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			String token = req.getHeader("token");
			//System.out.println(token);
			if (JwtUtils.verify(token)) {
				json.put("islogin", CommonContext.IS_LOGIN);
			} else {
				json.put("islogin", CommonContext.NO_LOGIN);
			}
		} catch (Exception e) {
			// TODO: handle exception
			json.put("code", CommonContext.HTTP_ERROR);
		}
		//System.out.println(json);
		return json;
	}

}
