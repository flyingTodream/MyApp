package com.app.mapper;

import com.app.model.TUserInfo;

public interface TUserInfoMapper {
    int deleteByPrimaryKey(Integer tId);

    int insert(TUserInfo record);

    int insertSelective(TUserInfo record);

    TUserInfo selectByPrimaryKey(Integer tId);

    int updateByPrimaryKeySelective(TUserInfo record);

    int updateByPrimaryKey(TUserInfo record);
}