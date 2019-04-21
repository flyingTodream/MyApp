package com.app.service;


import java.util.List;

import com.app.model.Roles;

public interface RolesService {
	 int deleteByPrimaryKey(Integer id);

	    int insert(Roles record);

	    int insertSelective(Roles record);

	    Roles selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(Roles record);

	    int updateByPrimaryKey(Roles record);
	    


	    
	    List<Roles> getRoles();
}
