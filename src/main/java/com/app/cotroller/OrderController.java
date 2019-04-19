package com.app.cotroller;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.app.model.OrderInfo;
import com.app.model.TUserInfo;
import com.app.service.OrderInfoService;
import com.app.service.TUserInfoService;
import com.common.CommonContext;
import com.common.utils.DateUtils;
import com.common.utils.JwtUtils;
import com.common.utils.Md5;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderInfoService orderInfoServiceImpl;

	@Autowired
	private TUserInfoService tUserInfoServiceImpl;
	/**
	 * 提交订单
	 * 
	 * @return
	 */
	@RequestMapping("/subOrder")
	public JSONObject subOrder(HttpServletRequest req, HttpServletResponse res, OrderInfo entity) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			String token = req.getHeader("token");
			if (JwtUtils.verify(token)) {
				entity.settId(Integer.valueOf(JwtUtils.getId(token)));
				entity.settName(JwtUtils.getUsername(token));
				entity.setIsOrder(CommonContext.NO_ORDER);
				entity.setIsEvaluation(CommonContext.NO_EVA);
				entity.setoTime(DateUtils.dateToString(new Date()));
				orderInfoServiceImpl.insertSelective(entity);
				json.put("oId", entity.getoId());
			}else {
				json.put("IsLogin", 401);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}
	
	/**
	 * 支付订单
	 */
	@RequestMapping("/payOrder")
	public JSONObject payOrder(HttpServletRequest req,BigDecimal oMoney, HttpServletResponse res,String tPayword,String oId) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			String token = req.getHeader("token");
			if(JwtUtils.verify(token)) {
				TUserInfo entity = tUserInfoServiceImpl.selectByPrimaryKey(Integer.valueOf(JwtUtils.getId(token)));
				if(entity.gettPayword().equals(Md5.string2MD5(tPayword))) {
					if(entity.gettMoney().compareTo(oMoney) == 0 || entity.gettMoney().compareTo(oMoney) == 1) {
						
						tUserInfoServiceImpl.updateMoney(oMoney.multiply(new BigDecimal(-1)), Integer.valueOf(JwtUtils.getId(token)));
						OrderInfo orderEntity = new OrderInfo();
						orderEntity.setoId(Integer.valueOf(oId));
						orderEntity.setIsOrder(CommonContext.IS_ORDER);
						orderInfoServiceImpl.updateByPrimaryKeySelective(orderEntity);
						json.put("status", CommonContext.IS_ORDER);
					}else {
						json.put("status", 2);
					}
				}else {
					json.put("status", CommonContext.NO_ORDER);
				}
			}else {
				json.put("IsLogin", 401);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}
	
	
	//获取订单列表
	@RequestMapping("/getOrderList")
	public JSONObject getOrderList(HttpServletRequest req,HttpServletResponse res,OrderInfo entity) {
		//System.out.println(entity);
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		String token = req.getHeader("token");
		try {
			if(JwtUtils.verify(token)) {
				json.put("status", CommonContext.IS_LOGIN);
				entity.settId(Integer.valueOf(JwtUtils.getId(token)));
				json.put("orderList", orderInfoServiceImpl.getOrderList(entity));
			}else {
				json.put("status", CommonContext.NO_LOGIN);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}
	//删除订单
	@RequestMapping("/delOrder")
	public JSONObject delOrder(HttpServletRequest req,HttpServletResponse res,String oId) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		String token = req.getHeader("token");
		try {
			if(JwtUtils.verify(token)) {
				json.put("status", CommonContext.IS_LOGIN);
				orderInfoServiceImpl.deleteByPrimaryKey(Integer.valueOf(oId));
			}else {
				json.put("status", CommonContext.NO_LOGIN);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}
	
}
