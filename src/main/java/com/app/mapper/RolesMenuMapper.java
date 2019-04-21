package com.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;

import com.app.model.RolesMenu;

public interface RolesMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RolesMenu record);

    int insertSelective(RolesMenu record);

    RolesMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolesMenu record);

    int updateByPrimaryKey(RolesMenu record);
        
    @Delete("delete from roles_menu where role=#{roleId}")
    void delByRoleId(Integer roleId);
    
    @Delete("delete from roles_menu where menu_id=#{menuId}")
    void delByMenuId(Integer menuId);
    
    List<RolesMenu> RoleMenulist();
    
    List<RolesMenu> roleMenuList(String username);
}