package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.LInfoMapper;
import com.app.model.LInfo;
import com.app.service.LineService;

@Service
public class LineServiceImpl implements LineService {

	@Autowired
	private LInfoMapper mapper;
	
	@Override
	public List<LInfo> getLineList(Integer tId) {
		// TODO Auto-generated method stub
		return mapper.getLineList(tId);
	}

}
