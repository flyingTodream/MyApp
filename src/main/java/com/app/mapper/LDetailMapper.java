package com.app.mapper;

import com.app.model.LDetail;

public interface LDetailMapper {
    int insert(LDetail record);

    int insertSelective(LDetail record);
}