package com.app.service;

import java.util.List;

import com.app.model.RolesMenu;

public interface RolesMenuService {
	int deleteByPrimaryKey(Integer id);

	int insert(RolesMenu record);

	int insertSelective(RolesMenu record);

	RolesMenu selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(RolesMenu record);

	int updateByPrimaryKey(RolesMenu record);
	
	void delByRoleId(Integer roleId);
	
	void delByMenuId(Integer menuId);
	
	/**
	 * 获取用户权限信息
	 * @return
	 */
	List<RolesMenu> RoleMenulist();
	
}
