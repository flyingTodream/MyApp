package com.app.cotroller.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.app.model.Menu;
import com.app.model.TUserInfo;
import com.app.model.UserRoles;
import com.app.service.MenuService;
import com.app.service.TUserInfoService;
import com.app.service.UserRolesService;
import com.common.CommonContext;
import com.common.utils.DateUtils;
import com.common.utils.JwtUtils;
import com.common.utils.Md5;

/**
 * @ClassName: StaffController
 * @Description: 用户角色
 */
@RestController
@RequestMapping("api/")
public class AdminUserController {

	@Autowired
	private TUserInfoService tUserInfoServiceImpl;

	@Autowired
	private UserRolesService userRolesService;

	@Autowired
	private MenuService menuServiceImpl;

	@RequestMapping("system/user/getUserMenu")
	public List<Menu> getUserMenu(@RequestBody(required = false) Map<String, Object> map, HttpServletRequest req,
			HttpServletResponse res) {
		String token = map.get("token").toString();
		System.out.println(menuServiceImpl.roleMenuList(JwtUtils.getUsername(token)));
		return menuServiceImpl.roleMenuList(JwtUtils.getId(token));
	}

	/**
	 * 管理员登录
	 * 
	 * @param map
	 * @param req
	 * @param res
	 * @return
	 */

	@RequestMapping("admin/doLogin")
	public JSONObject doLogin(@RequestBody(required = false) Map<String, Object> map, HttpServletRequest req,
			HttpServletResponse res) {
		JSONObject json = new JSONObject();
		String tPhone = map.get("tPhone").toString();
		String tPass = map.get("tPass").toString();
		TUserInfo entity = new TUserInfo();
		entity = tUserInfoServiceImpl.selectByPhone(tPhone);
		if (entity != null) {
			if (entity.gettAnswer().equals(CommonContext.USER_TYPE_TR)) {
				json.put("status", "403");
			} else {
				if (Md5.string2MD5(tPass).equals(entity.gettPass())) {
					json.put("token", JwtUtils.sign(entity.gettName(), entity.gettId().toString()));
					json.put("name", entity.gettName());
					json.put("status", "0");
				} else {
					json.put("status", "1");
				}
			}
		} else {
			json.put("status", "404");
		}
		// String token = map.get("token").toString();
		return json;
	}

	/**
	 * 修改密码
	 * 
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping("doModifyPassword")
	@ResponseBody
	public JSONObject modifyPassword(@RequestBody Map<String, Object> map, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			String newpassword = map.get("newPassword").toString();
			String token = map.get("token").toString();
			if (JwtUtils.verify(token)) {
				json.put("status", CommonContext.IS_LOGIN);
				TUserInfo tUserInfo = tUserInfoServiceImpl.selectByPrimaryKey(Integer.valueOf(JwtUtils.getId(token)));
				tUserInfo.settPass(Md5.string2MD5(newpassword));
				tUserInfoServiceImpl.updateByPrimaryKeySelective(tUserInfo);
			} else {
				json.put("status", CommonContext.NO_LOGIN);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("status", CommonContext.HTTP_ERROR);
		}

		return json;
	}

	/**
	 * 获取用户列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("admin/getUserList")
	public JSONObject getUserList(@RequestBody Map<String, Object> map) {
		String tName = null;
		String tPhone = null;
		if (map.get("tName") != null) {
			tName = map.get("tName").toString();
		}
		if (map.get("tPhone") != null) {
			tPhone = map.get("tPhone").toString();
		}
		String tAnswer = map.get("tAnswer").toString();
		TUserInfo entity = new TUserInfo();
		entity.settName(tName);
		entity.settPhone(tPhone);
		entity.settAnswer(tAnswer);
		entity.setPage(Integer.valueOf(map.get("page").toString()));
		return tUserInfoServiceImpl.getUserList(entity);
	}

	/**
	 * 删除用户
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("admin/delUser")
	public void delUser(@RequestBody Map<String, Object> map) {
		String tId = map.get("tId").toString();
		tUserInfoServiceImpl.deleteByPrimaryKey(Integer.valueOf(tId));
	}

	/**
	 * 添加景点管理员
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("admin/addAdmin")
	public JSONObject addAdmin(@RequestBody Map<String, Object> map) {
		JSONObject json = new JSONObject();
		String name = map.get("name").toString();
		String phone = map.get("phone").toString();
		String email = map.get("email").toString();
		if (tUserInfoServiceImpl.selectByPhone(phone) != null) {
			json.put("status", 1);
		} else {
			json.put("status", 0);
			TUserInfo entity = new TUserInfo();
			entity.settName(name);
			entity.settPhone(phone);
			entity.settEmail(email);
			entity.settImg("../../static/fumou-center-template/header.png");
			entity.settPass(Md5.string2MD5("12345678"));
			entity.settPayword(Md5.string2MD5("123456"));
			entity.settAnswer("1");
			entity.settMoney(new BigDecimal(0));
			entity.settRegtime(DateUtils.dateToString(new Date()));
			tUserInfoServiceImpl.insertSelective(entity);
			UserRoles userRoles = new UserRoles();
			userRoles.setRole(12);
			userRoles.setUsername(entity.gettId().toString());
			userRolesService.insertSelective(userRoles);
		}

		return json;
	}

}
