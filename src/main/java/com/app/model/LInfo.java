package com.app.model;

import java.io.Serializable;

public class LInfo implements Serializable {
    private Integer lId;

    private Integer tInfoId;

    private String lName;

    private static final long serialVersionUID = 1L;

    public Integer getlId() {
        return lId;
    }

    public void setlId(Integer lId) {
        this.lId = lId;
    }

    public Integer gettInfoId() {
        return tInfoId;
    }

    public void settInfoId(Integer tInfoId) {
        this.tInfoId = tInfoId;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
}