package com.app.service.serviceImpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.TUserInfoMapper;
import com.app.model.TUserInfo;
import com.app.service.TUserInfoService;
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

}
