package com.app.mapper;

import com.app.model.OrderInfo;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Integer oId);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Integer oId);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
}