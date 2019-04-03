package com.app.model;

import java.io.Serializable;

public class lDetail implements Serializable {
    private Integer detailId;

    private Integer lId;

    private String lLine;

    private Integer lOrder;

    private static final long serialVersionUID = 1L;

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

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