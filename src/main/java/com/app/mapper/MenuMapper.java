package com.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.app.model.Menu;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    
    List<Menu> getMenuList();
    
    List<Menu> getMenuId(@Param("id")Integer id);
    
    List<Menu> getMenu();
    
    /**
	 * 根据username获取菜单
	 * @return
	 */
	List<Menu> roleMenuList(String username);
}