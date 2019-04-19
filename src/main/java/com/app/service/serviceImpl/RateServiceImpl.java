package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.TEvaluationMapper;
import com.app.model.TEvaluation;
import com.app.service.RateService;

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

}
