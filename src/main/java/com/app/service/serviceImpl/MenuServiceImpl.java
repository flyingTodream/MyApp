package com.app.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.app.mapper.MenuMapper;
import com.app.model.Menu;
import com.app.service.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuMapper mapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Menu record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(Menu record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public Menu selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Menu record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Menu record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public JSONObject getMenuList() {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		List<Menu> menuList = mapper.getMenuList();
		json.put("menuList", menuList);
		return json;
	}

	@Override
	public List<Menu> getMenuId(Integer id) {
		// TODO Auto-generated method stub
		return mapper.getMenuId(id);
	}

	@Override
	public JSONObject getMenu() {
		JSONObject json = new JSONObject();
		List<Menu> menuList = mapper.getMenu();
		json.put("menuList", menuList);
		return json;
	}
	
	@Override
	public List<Menu> roleMenuList(String username) {
		// TODO Auto-generated method stub
		List<Menu> list = mapper.roleMenuList(username);
		List<Integer> pid = new ArrayList<Integer>();
		for(int i = 0 ; i < list.size() ; i++) {
			pid.add(list.get(i).getPid());
		}
		List<Menu> returnList = new ArrayList<Menu>();
		//去除相同元素
		pid = deleteSameNum(pid);
		for(int j = 0 ; j < pid.size() ; j++) {
			Menu menu = mapper.selectByPrimaryKey(pid.get(j));
			List<Menu> MneuList = new ArrayList<Menu>();
			for(Menu childrenMenu : list) {
				if(menu.getId() == childrenMenu.getPid()) {
					MneuList.add(childrenMenu);
				}
			}
			menu.setChildren(MneuList);
			returnList.add(menu);
		}
		
		return returnList;
	}

	   
	    /**
	     *  deleteSameNum TODO 只剩下一个相同的元素 
	     * @param list
	     * @return List<Integer>
	     */
	    public static List<Integer> deleteSameNum(List<Integer> list){
	        Set<Integer> myset = new HashSet<Integer>();
	        for( int i=0; i<list.size(); i++){
	            myset.add(list.get(i));
	        }
	         
	        List<Integer> myList = new ArrayList<Integer>();
	        for(int num: myset)
	            myList.add(num);
	        return myList;
	    }
	
	
}
