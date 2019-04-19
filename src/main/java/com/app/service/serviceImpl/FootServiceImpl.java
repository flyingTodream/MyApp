package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.FootMapper;
import com.app.model.Foot;
import com.app.service.FootService;
@Service
public class FootServiceImpl implements FootService {

	@Autowired
	private FootMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Integer footId) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(footId);
	}

	@Override
	public int insertSelective(Foot record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public List<Foot> getFootList(Integer tId) {
		// TODO Auto-generated method stub
		return mapper.getFootList(tId);
	}

}
