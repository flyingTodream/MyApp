package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.TInfoMapper;
import com.app.model.TInfo;
import com.app.service.TInfoService;

@Service
public class TInfoServiceImpl implements TInfoService {

	@Autowired
	private TInfoMapper mapper;
	
	@Override
	public List<TInfo> homeSelectInfo(TInfo record) {
		// TODO Auto-generated method stub
		return mapper.homeSelectInfo(record);
	}

}
