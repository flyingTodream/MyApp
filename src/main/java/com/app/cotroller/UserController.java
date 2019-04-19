package com.app.cotroller;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.app.model.TUserInfo;
import com.app.service.TUserInfoService;
import com.common.CommonContext;
import com.common.utils.DateUtils;
import com.common.utils.JwtUtils;
import com.common.utils.Md5;

@RequestMapping("/api/")
@RestController
public class UserController {

	@Autowired
	private TUserInfoService tUserInfoServiceImpl;

	// 获取用户信息
	@RequestMapping("user/getUserInfo")
	public JSONObject getUserInfo(HttpServletRequest req, HttpServletResponse res) {
		JSONObject json = new JSONObject();
		String token = req.getHeader("token");
		// System.out.println(token);
		json.put("userInfo", tUserInfoServiceImpl.selectByPrimaryKey(Integer.valueOf(JwtUtils.getId(token))));
		return json;
	}

	// 更改用户信息
	@RequestMapping("user/updateUserInfo")
	public JSONObject updateUserInfo(HttpServletRequest req, HttpServletResponse res, TUserInfo entity) {
		JSONObject json = new JSONObject();
		String token = req.getHeader("token");
		json.put("code", CommonContext.HTTP_SUCCESS);
		// System.out.println(token);
		// System.out.println(entity);
		try {
			if (JwtUtils.verify(token)) {
				json.put("islogin", CommonContext.IS_LOGIN);
				entity.settId(Integer.valueOf(JwtUtils.getId(token)));
				tUserInfoServiceImpl.updateByPrimaryKeySelective(entity);
			} else {
				json.put("islogin", CommonContext.NO_LOGIN);
			}
		} catch (Exception e) {
			// TODO: handle exception
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}

	// 充值
	@RequestMapping("user/Recharge")
	public JSONObject Recharge(HttpServletRequest req, HttpServletResponse res, BigDecimal money, String password) {
		JSONObject json = new JSONObject();
		String token = req.getHeader("token");
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			if (JwtUtils.verify(token)) {
				json.put("islogin", CommonContext.IS_LOGIN);
				TUserInfo entity = tUserInfoServiceImpl.selectByPrimaryKey(Integer.valueOf(JwtUtils.getId(token)));
				if (entity.gettPayword().equals(Md5.string2MD5(password))) {
					json.put("status", 0);
					tUserInfoServiceImpl.updateMoney(money, Integer.valueOf(JwtUtils.getId(token)));
				} else {
					json.put("status", 1);
				}
			} else {
				json.put("islogin", CommonContext.NO_LOGIN);
			}
		} catch (Exception e) {
			// TODO: handle exception
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}

	// 修改密码
	@RequestMapping("user/updatePassword")
	public JSONObject updatePassword(HttpServletRequest req, HttpServletResponse res, String password,
			String newpassword) {
		JSONObject json = new JSONObject();
		String token = req.getHeader("token");
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			if (JwtUtils.verify(token)) {
				json.put("islogin", CommonContext.IS_LOGIN);
				TUserInfo entity = tUserInfoServiceImpl.selectByPrimaryKey(Integer.valueOf(JwtUtils.getId(token)));
				if (entity.gettPass().equals(Md5.string2MD5(password))) {
					json.put("status", 0);
					entity.settPass(Md5.string2MD5(newpassword));
					tUserInfoServiceImpl.updateByPrimaryKeySelective(entity);
				} else {
					json.put("status", 1);
				}
			} else {
				json.put("islogin", CommonContext.NO_LOGIN);
			}
		} catch (Exception e) {
			// TODO: handle exception
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}

	// 修改支付密码
	@RequestMapping("user/updatePayPassword")
	public JSONObject updatePayPassword(HttpServletRequest req, HttpServletResponse res, String password) {
		JSONObject json = new JSONObject();
		String token = req.getHeader("token");
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			if (JwtUtils.verify(token)) {
				json.put("islogin", CommonContext.IS_LOGIN);
				TUserInfo entity = new TUserInfo();
				entity.settId(Integer.valueOf(JwtUtils.getId(token)));
				entity.settPayword(Md5.string2MD5(password));
				tUserInfoServiceImpl.updateByPrimaryKeySelective(entity);
			} else {
				json.put("islogin", CommonContext.NO_LOGIN);
			}

		} catch (Exception e) {
			// TODO: handle exception
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}

	// 注册账号
	@RequestMapping("user/reg")
	public JSONObject reg(TUserInfo entity) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			if (tUserInfoServiceImpl.selectByPhone(entity.gettPhone()) != null) {
				json.put("status", 1);
			} else {
				
				entity.settRegtime(DateUtils.dateToString(new Date()));
				entity.settMoney(BigDecimal.ZERO);
				entity.settPass(Md5.string2MD5(entity.gettPass()));
				entity.settPayword(Md5.string2MD5(entity.gettPayword()));
				entity.settImg("../../static/fumou-center-template/header.png");
				entity.settAnswer(CommonContext.USER_TYPE_TR);
				tUserInfoServiceImpl.insertSelective(entity);
				json.put("status", 0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}
	
	// 修改密码
		@RequestMapping("user/forgetPassword")
		public JSONObject forgetPassword(TUserInfo entity,String newPassword) {
			JSONObject json = new JSONObject();
			json.put("code", CommonContext.HTTP_SUCCESS);
			try {
				entity = tUserInfoServiceImpl.selectByPhone(entity.gettPhone());
				if (entity != null) {
					
					entity.settPass(Md5.string2MD5(newPassword));
					tUserInfoServiceImpl.updateByPrimaryKeySelective(entity);
					json.put("status", 0);
				}else {
					json.put("status", 1);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				json.put("code", CommonContext.HTTP_ERROR);
			}
			return json;
		}
}
