package com.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.app.model.Roles;


public interface RolesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Roles record);

    int insertSelective(Roles record);

    Roles selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Roles record);

    int updateByPrimaryKey(Roles record);
    
    List<Roles> getRoles();

    @Select("select * from roles r , roles_menu rm where r.id = rm.role and rm.menu_id = #{menuId}")
    List<Roles> queryRolesByMenuId(Integer menuId);
    
    @Select("select * from roles")
    @Results(id="roleMap",value= {
    		@Result(id=true,column="id",property="id"),
    		@Result(column="description",property="description"),
    		@Result(column="role",property="role"),
    		@Result(column="id",property="menus",
    				many=@Many(select="com.windoor.performance.mapper.MenuMapper.queryMenuByRoleId"))
    })
    List<Roles> findAll();
}