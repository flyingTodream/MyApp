package com.app.mapper;

import com.app.model.AuserInfo;

public interface AuserInfoMapper {
    int deleteByPrimaryKey(Integer aId);

    int insert(AuserInfo record);

    int insertSelective(AuserInfo record);

    AuserInfo selectByPrimaryKey(Integer aId);

    int updateByPrimaryKeySelective(AuserInfo record);

    int updateByPrimaryKey(AuserInfo record);
}