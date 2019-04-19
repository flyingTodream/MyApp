package com.app.service;

import java.math.BigDecimal;

import com.app.model.TUserInfo;

public interface TUserInfoService {
	
	TUserInfo selectByPhone(String phone);
	
	TUserInfo selectByPrimaryKey(Integer tId);
	
    int updateMoney(BigDecimal tMoney,Integer tId);
    
    int updateByPrimaryKeySelective(TUserInfo record);
    
    int insertSelective(TUserInfo record);

    
}
