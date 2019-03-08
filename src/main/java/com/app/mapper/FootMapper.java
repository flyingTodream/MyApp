package com.app.mapper;

import com.app.model.Foot;

public interface FootMapper {
    int deleteByPrimaryKey(Integer footId);

    int insert(Foot record);

    int insertSelective(Foot record);

    Foot selectByPrimaryKey(Integer footId);

    int updateByPrimaryKeySelective(Foot record);

    int updateByPrimaryKey(Foot record);
}