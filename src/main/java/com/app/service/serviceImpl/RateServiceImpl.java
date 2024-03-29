package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.app.mapper.TEvaluationMapper;
import com.app.model.TEvaluation;
import com.app.service.RateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class RateServiceImpl implements RateService {

	@Autowired
	private TEvaluationMapper mapper;
	
	@Override
	public List<TEvaluation> getRateListByTId(TEvaluation record) {
		// TODO Auto-generated method stub
		return mapper.getRateListByTId(record);
	}

	@Override
	public float getRateAvg(Integer tInfoId) {
		// TODO Auto-generated method stub
		return mapper.getRateAvg(tInfoId);
	}

	@Override
	public int insertSelective(TEvaluation record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public JSONObject getRateListAdmin(Integer aId, String tInfoTitle,Integer page) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		PageHelper.startPage(page, 10);
		List<TEvaluation> List = mapper.getRateListAdmin(aId, tInfoTitle);
		PageInfo<TEvaluation> pageInfo = new PageInfo<TEvaluation>(List);
		json.put("rateList", pageInfo.getList());
		json.put("total", pageInfo.getTotal());
		return json;
	}

}
