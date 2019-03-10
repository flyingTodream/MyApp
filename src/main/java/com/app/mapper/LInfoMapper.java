package com.app.mapper;

import java.util.List;

import com.app.model.LInfo;

public interface LInfoMapper {
    int deleteByPrimaryKey(Integer lId);

    int insert(LInfo record);

    int insertSelective(LInfo record);

    LInfo selectByPrimaryKey(Integer lId);

    int updateByPrimaryKeySelective(LInfo record);

    int updateByPrimaryKey(LInfo record);
    //获取路线信息
    List<LInfo> getLineList(Integer tId);
}