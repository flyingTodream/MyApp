package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.ULikeMapper;
import com.app.model.ULike;
import com.app.service.ULikeService;
@Service
public class ULikeServiceImpl implements ULikeService {

	@Autowired
	private ULikeMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Integer uLikeId) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(uLikeId);
	}

	@Override
	public int insert(ULike record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(ULike record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public ULike selectByPrimaryKey(Integer uLikeId) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(uLikeId);
	}

	@Override
	public int updateByPrimaryKeySelective(ULike record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ULike record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public ULike selectIsLike(ULike record) {
		// TODO Auto-generated method stub
		return mapper.selectIsLike(record);
	}

	@Override
	public List<ULike> getLikeList(Integer tId) {
		// TODO Auto-generated method stub
		return mapper.getLikeList(tId);
	}

}
