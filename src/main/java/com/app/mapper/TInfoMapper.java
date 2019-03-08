package com.app.mapper;

import com.app.model.TInfo;

public interface TInfoMapper {
    int deleteByPrimaryKey(Integer tInfoId);

    int insert(TInfo record);

    int insertSelective(TInfo record);

    TInfo selectByPrimaryKey(Integer tInfoId);

    int updateByPrimaryKeySelective(TInfo record);

    int updateByPrimaryKeyWithBLOBs(TInfo record);

    int updateByPrimaryKey(TInfo record);
}