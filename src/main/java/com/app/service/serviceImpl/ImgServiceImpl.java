package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.ImgMapper;
import com.app.model.Img;
import com.app.service.ImgService;
@Service
public class ImgServiceImpl implements ImgService {
	@Autowired
	private ImgMapper mapper;
	
	
	@Override
	public List<Img> selImg(Integer tId) {
		// TODO Auto-generated method stub
		return mapper.selImg(tId);
	}


	@Override
	public int deleteByPrimaryKey(Integer imgId) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(imgId);
	}


	@Override
	public int insertSelective(Img record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

}
