package com.app.service;

import java.util.List;

import com.app.model.UserRoles;

public interface UserRolesService {
	int deleteByPrimaryKey(Integer id);

    int deleteByUsername(String username);
	
	int insert(UserRoles record);

	int insertSelective(UserRoles record);

	UserRoles selectByPrimaryKey(Integer id);
	
	UserRoles selectByUserName(String username);

	int updateByPrimaryKeySelective(UserRoles record);

	int updateByPrimaryKey(UserRoles record);
	
    List<UserRoles> getUserRolesList(UserRoles record);
}
