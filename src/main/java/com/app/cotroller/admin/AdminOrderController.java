package com.app.cotroller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.app.model.OrderInfo;
import com.app.model.TUserInfo;
import com.app.service.OrderInfoService;
import com.app.service.TUserInfoService;
import com.common.CommonContext;
import com.common.utils.JwtUtils;

@RequestMapping("api/")
@RestController
public class AdminOrderController {
	
	@Autowired
	private OrderInfoService orderInfoServiceImpl;
	
	@Autowired
	private TUserInfoService tUserInfoServiceImpl;
	
	/**
	 * 获取订单列表
	 * @param map
	 * @return
	 */
	@RequestMapping("admin/getOrderList")
	public JSONObject getRateList(@RequestBody(required = false) Map<String, Object> map) {
		String token = map.get("token").toString();
		TUserInfo userInfo = tUserInfoServiceImpl.selectByPrimaryKey(Integer.valueOf(JwtUtils.getId(token)));
		if(userInfo.gettAnswer().equals(CommonContext.USER_TYPE_ADMIN)) {
			return orderInfoServiceImpl.getOrderAdminList(userInfo.gettId(), Integer.valueOf(map.get("page").toString()));
			
		}else {
			return orderInfoServiceImpl.getOrderAdminList(null,Integer.valueOf(map.get("page").toString()));
			
		}
	}
	
	/**
	 * 获取订单统计列表
	 * @param map
	 * @return
	 */
	@RequestMapping("admin/getOrderSum")
	public List<OrderInfo> getOrderSum() {
		return orderInfoServiceImpl.getOrderSum();
	}
}
