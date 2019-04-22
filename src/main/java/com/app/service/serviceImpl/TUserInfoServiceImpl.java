package com.app.service.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.app.mapper.TUserInfoMapper;
import com.app.model.TUserInfo;
import com.app.service.TUserInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class TUserInfoServiceImpl implements TUserInfoService {

	@Autowired
	private TUserInfoMapper mapper;
	
	public TUserInfo selectByPhone(String phone) {
		// TODO Auto-generated method stub
		return mapper.selectByPhone(phone);
	}

	@Override
	public TUserInfo selectByPrimaryKey(Integer tId) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(tId);
	}

	@Override
	public int updateMoney(BigDecimal tMoney, Integer tId) {
		// TODO Auto-generated method stub
		return mapper.updateMoney(tMoney, tId);
	}

	@Override
	public int updateByPrimaryKeySelective(TUserInfo record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(TUserInfo record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public JSONObject getUserList(TUserInfo record) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		PageHelper.startPage(record.getPage(), 10);
		List<TUserInfo> List = mapper.getUserList(record);
		PageInfo<TUserInfo> pageInfo = new PageInfo<TUserInfo>(List);
		json.put("userList", pageInfo.getList());
		json.put("total", pageInfo.getTotal());
		return json;
	}

	@Override
	public int deleteByPrimaryKey(Integer tId) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(tId);
	}

}
