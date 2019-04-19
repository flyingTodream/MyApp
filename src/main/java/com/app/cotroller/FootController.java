package com.app.cotroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.app.service.FootService;
import com.common.CommonContext;
import com.common.utils.JwtUtils;

@RestController
@RequestMapping("api/foot")
public class FootController {
	
	@Autowired
	private FootService FootServiceImpl;
	
	//获取足迹列表
	@RequestMapping("/getFootList")
	public JSONObject getFootList(HttpServletRequest req,HttpServletResponse res) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		String token = req.getHeader("token");
		try {
			if(JwtUtils.verify(token)) {
				json.put("status", CommonContext.IS_LOGIN);
				json.put("footList", FootServiceImpl.getFootList(Integer.valueOf(JwtUtils.getId(token))));
			}else {
				json.put("status", CommonContext.NO_LOGIN);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_SUCCESS);
		}
		return json;

	}
	
	//删除足迹
	@RequestMapping("/delFoot")
	public JSONObject delFoot(HttpServletRequest req,HttpServletResponse res,Integer fId) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			FootServiceImpl.deleteByPrimaryKey(fId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_SUCCESS);
		}
		return json;
	}
	
}
