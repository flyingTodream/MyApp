package com.app.mapper;

import java.util.List;

import com.app.model.TEvaluation;

public interface TEvaluationMapper {
    int deleteByPrimaryKey(Integer eId);

    int insert(TEvaluation record);

    int insertSelective(TEvaluation record);

    TEvaluation selectByPrimaryKey(Integer eId);

    int updateByPrimaryKeySelective(TEvaluation record);

    int updateByPrimaryKeyWithBLOBs(TEvaluation record);

    int updateByPrimaryKey(TEvaluation record);
    
    //获取评价列表
    List<TEvaluation> getRateListByTId(TEvaluation record);
    
    //获取景点平均分
    float getRateAvg(Integer tInfoId);
}