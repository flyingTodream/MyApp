package com.app.service;

import java.util.List;

import com.app.model.OrderInfo;

public interface OrderInfoService {
    int deleteByPrimaryKey(Integer oId);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Integer oId);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
    
    List<OrderInfo> getOrderList(OrderInfo record);
}
