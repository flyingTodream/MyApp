package com.app.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.app.model.Menu;

public interface MenuService {
	int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    
    JSONObject getMenuList();
    
    List<Menu> getMenuId(Integer id);

	JSONObject getMenu();
	
	/**
	 * 根据username获取菜单
	 * @return
	 */
	List<Menu> roleMenuList(String username);

}
