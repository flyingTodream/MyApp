package com.app.mapper;

import com.app.model.TEvaluation;

public interface TEvaluationMapper {
    int deleteByPrimaryKey(Integer eId);

    int insert(TEvaluation record);

    int insertSelective(TEvaluation record);

    TEvaluation selectByPrimaryKey(Integer eId);

    int updateByPrimaryKeySelective(TEvaluation record);

    int updateByPrimaryKeyWithBLOBs(TEvaluation record);

    int updateByPrimaryKey(TEvaluation record);
}