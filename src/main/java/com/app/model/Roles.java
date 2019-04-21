package com.app.model;

import java.io.Serializable;
import java.util.List;


public class Roles extends BaseModel implements Serializable {
    private Integer id;

    private String description;

    private String role;
    
    private List<Menu> menus;
    
    private String menuIds;
    
    private String menuNames;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
		String mIds = "";
		String mNames = "";
		for(Menu m : this.menus) {
			mIds += m.getId() + ",";
			mNames += m.getName() + ",";
		}
		setMenuIds(mIds);
		setMenuNames(mNames);
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	public String getMenuNames() {
		return menuNames;
	}

	public void setMenuNames(String menuNames) {
		this.menuNames = menuNames;
	}
    
}