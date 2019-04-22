package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.homeImgMapper;
import com.app.model.HomeImg;
import com.app.service.HomeImgService;
@Service
public class HomeImgServiceImpl implements HomeImgService {

	@Autowired
	private homeImgMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(HomeImg record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public List<HomeImg> getHomeImgList() {
		// TODO Auto-generated method stub
		return mapper.getHomeImgList();
	}

}
