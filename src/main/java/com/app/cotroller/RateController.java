package com.app.cotroller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.app.model.Foot;
import com.app.model.OrderInfo;
import com.app.model.TEvaluation;
import com.app.service.FootService;
import com.app.service.OrderInfoService;
import com.app.service.RateService;
import com.common.CommonContext;
import com.common.utils.DateUtils;
import com.common.utils.JwtUtils;
import com.common.utils.StringUtils;

@RestController
@RequestMapping("api/")
public class RateController {

	@Autowired
	private RateService rateServiceImpl;
	
	@Autowired
	private OrderInfoService orderInfoServiceImpl;

	@Autowired
	private FootService FootServiceImpl;

	
	// 获取评价列表
	@RequestMapping("rate/getRateList")
	public JSONObject getRateList(TEvaluation record) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		try {
			List<TEvaluation> rateList = rateServiceImpl.getRateListByTId(record);
			json.put("rateList", rateList);
			if(rateList.size() > 0) {
				json.put("rateAvg", rateServiceImpl.getRateAvg(record.gettInfoId()));
			}else {
				json.put("rateAvg", 0);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("code", CommonContext.HTTP_ERROR);
		}
		return json;
	}

	//添加评价
	@RequestMapping("rate/addRate")
	public JSONObject addRate(TEvaluation record,String oId,HttpServletRequest req) {
		JSONObject json = new JSONObject();
		json.put("code", CommonContext.HTTP_SUCCESS);
		String token = req.getHeader("token");
		try {
			if(JwtUtils.verify(token)) {
				json.put("status", CommonContext.IS_LOGIN);
				OrderInfo orderEntity = new OrderInfo();
				orderEntity.setoId(Integer.valueOf(oId));
				orderEntity.setIsEvaluation(CommonContext.IS_EVA);
				if(record.geteIsAnonymous().equals("1")) {
					record.seteIsAnonymous("否");
				}else {
					record.seteIsAnonymous("是");
				}
				if(StringUtils.isEmpty(record.geteContent())) {
					record.seteContent("此用户没有填写任何评价");
				}
				record.settName(JwtUtils.getUsername(token));
				record.seteTime(DateUtils.dateToString(new Date()));
				record.settId(Integer.valueOf(JwtUtils.getId(token)));
				Foot footEntity = new Foot();
				footEntity.settId(Integer.valueOf(JwtUtils.getId(token)));
				footEntity.settInfoId(record.gettInfoId());
				footEntity.settInfoTitle(record.gettInfoTitle());
				footEntity.settName(JwtUtils.getUsername(token));
				footEntity.setfTime(DateUtils.dateToString(new Date()));
				rateServiceImpl.insertSelective(record);
				FootServiceImpl.insertSelective(footEntity);
				orderInfoServiceImpl.updateByPrimaryKeySelective(orderEntity);
				
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
	
	
}
