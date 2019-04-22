package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.app.mapper.OrderInfoMapper;
import com.app.model.OrderInfo;
import com.app.service.OrderInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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

	@Override
	public JSONObject getOrderAdminList(Integer aId, Integer page) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		PageHelper.startPage(page, 10);
		List<OrderInfo> List = mapper.getOrderAdminList(aId);
		PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(List);
		json.put("orderList", pageInfo.getList());
		json.put("total", pageInfo.getTotal());
		return json;
	}

	@Override
	public List<OrderInfo> getOrderSum() {
		// TODO Auto-generated method stub
		return mapper.getOrderSum();
	}

}
