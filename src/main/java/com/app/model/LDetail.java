package com.app.model;

import java.io.Serializable;

public class LDetail implements Serializable {
    private Integer lId;

    private String lLine;

    private Integer lOrder;

    private static final long serialVersionUID = 1L;

    public Integer getlId() {
        return lId;
    }

    public void setlId(Integer lId) {
        this.lId = lId;
    }

    public String getlLine() {
        return lLine;
    }

    public void setlLine(String lLine) {
        this.lLine = lLine;
    }

    public Integer getlOrder() {
        return lOrder;
    }

    public void setlOrder(Integer lOrder) {
        this.lOrder = lOrder;
    }
}