package com.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.app.model.OrderInfo;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Integer oId);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Integer oId);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
    
    List<OrderInfo> getOrderList(OrderInfo record);
    
    List<OrderInfo> getOrderAdminList(@Param("aId") Integer aId);
    
    List<OrderInfo> getOrderSum();
}