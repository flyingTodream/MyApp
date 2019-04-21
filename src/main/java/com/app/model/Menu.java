package com.app.model;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {
    private Integer id;

    private String name;

    private Integer pid;

    private String component;

    private String path;
    
    private List<Menu> children;

    /**
	 * @return the children
	 */
	public List<Menu> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}