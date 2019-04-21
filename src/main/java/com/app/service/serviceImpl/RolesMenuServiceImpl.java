package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.mapper.RolesMenuMapper;
import com.app.model.RolesMenu;
import com.app.service.RolesMenuService;

@Service
@Transactional
public class RolesMenuServiceImpl implements RolesMenuService {

	@Autowired
	private RolesMenuMapper mapper;
	
	


	@Override
	public int insert(RolesMenu record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(RolesMenu record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}



	@Override
	public int updateByPrimaryKeySelective(RolesMenu record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(RolesMenu record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public RolesMenu selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public void delByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		mapper.delByRoleId(roleId);
	}

	@Override
	public void delByMenuId(Integer menuId) {
		// TODO Auto-generated method stub
		mapper.delByMenuId(menuId);
	}

	@Override
	public List<RolesMenu> RoleMenulist() {
		// TODO Auto-generated method stub
		return mapper.RoleMenulist();
	}

	


}
