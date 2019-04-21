package com.app.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.mapper.UserRolesMapper;
import com.app.model.UserRoles;
import com.app.service.UserRolesService;


@Service
@Transactional
public class UserRolesServiceImpl implements UserRolesService {

	@Autowired
	private UserRolesMapper mapper;
	


	@Override
	public int insert(UserRoles record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(UserRoles record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}


	@Override
	public int updateByPrimaryKeySelective(UserRoles record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserRoles record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public UserRoles selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<UserRoles> getUserRolesList(UserRoles record) {
		// TODO Auto-generated method stub
		return mapper.getUserRolesList(record);
	}

	@Override
	public int deleteByUsername(String username) {
		// TODO Auto-generated method stub
		return mapper.deleteByUsername(username);
	}

	@Override
	public UserRoles selectByUserName(String username) {
		// TODO Auto-generated method stub
		return mapper.selectByUserName(username);
	}

}
