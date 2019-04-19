package com.app.mapper;

import java.math.BigDecimal;

import com.app.model.TUserInfo;

public interface TUserInfoMapper {
    int deleteByPrimaryKey(Integer tId);

    int insert(TUserInfo record);

    int insertSelective(TUserInfo record);

    TUserInfo selectByPrimaryKey(Integer tId);

    int updateByPrimaryKeySelective(TUserInfo record);

    int updateByPrimaryKey(TUserInfo record);
    
    TUserInfo selectByPhone(String phone);
    
    int updateMoney(BigDecimal tMoney,Integer tId);
}