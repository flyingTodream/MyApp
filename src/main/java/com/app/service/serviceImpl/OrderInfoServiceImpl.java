package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.OrderInfoMapper;
import com.app.model.OrderInfo;
import com.app.service.OrderInfoService;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

	@Autowired
	private OrderInfoMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Integer oId) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(oId);
	}

	@Override
	public int insert(OrderInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(OrderInfo record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public OrderInfo selectByPrimaryKey(Integer oId) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(oId);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderInfo record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(OrderInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<OrderInfo> getOrderList(OrderInfo record) {
		// TODO Auto-generated method stub
		return mapper.getOrderList(record);
	}

}
