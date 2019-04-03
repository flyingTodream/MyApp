package com.app.cotroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.app.model.TEvaluation;
import com.app.service.RateService;
import com.common.CommonContext;

@RestController
@RequestMapping("api/")
public class RateController {

	@Autowired
	private RateService rateServiceImpl;

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

}
