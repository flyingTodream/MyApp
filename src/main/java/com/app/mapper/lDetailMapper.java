package com.app.mapper;

import java.util.List;

import com.app.model.lDetail;

public interface lDetailMapper {
    int deleteByPrimaryKey(Integer detailId);

    int insert(lDetail record);

    int insertSelective(lDetail record);

    lDetail selectByPrimaryKey(Integer detailId);

    int updateByPrimaryKeySelective(lDetail record);

    int updateByPrimaryKey(lDetail record);
    
    //获取路线详细信息
    List<lDetail> getDetailList(Integer lId);
}