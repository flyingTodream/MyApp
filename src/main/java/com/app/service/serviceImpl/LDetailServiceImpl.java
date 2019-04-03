package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.lDetailMapper;
import com.app.model.lDetail;
import com.app.service.LDetailService;

@Service
public class LDetailServiceImpl implements LDetailService {

	@Autowired
	private lDetailMapper mapper;
	
	@Override
	public List<lDetail> getDetailList(Integer lId) {
		// TODO Auto-generated method stub
		return mapper.getDetailList(lId);
	}

}
