package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.app.mapper.TInfoMapper;
import com.app.model.TInfo;
import com.app.service.TInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TInfoServiceImpl implements TInfoService {

	@Autowired
	private TInfoMapper mapper;
	
	@Override
	public List<TInfo> homeSelectInfo(TInfo record) {
		// TODO Auto-generated method stub
		return mapper.homeSelectInfo(record);
	}

	@Override
	public List<TInfo> searchInfo(String kw) {
		// TODO Auto-generated method stub
		return mapper.searchInfo(kw);
	}

	@Override
	public JSONObject selectInfoPage(TInfo record) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		PageHelper.startPage(record.getPage(), 10);
		List<TInfo> List = mapper.homeSelectInfo(record);
		PageInfo<TInfo> pageInfo = new PageInfo<TInfo>(List);
		json.put("tInfo", pageInfo.getList());
		json.put("total", pageInfo.getTotal());
		return json;
	}

	@Override
	public int deleteByPrimaryKey(Integer tInfoId) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(tInfoId);
	}

	@Override
	public int insertSelective(TInfo record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public TInfo selectByPrimaryKey(Integer tInfoId) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(tInfoId);
	}

	@Override
	public int updateByPrimaryKeySelective(TInfo record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

}
