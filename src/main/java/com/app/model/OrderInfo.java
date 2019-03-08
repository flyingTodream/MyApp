package com.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo implements Serializable {
    private Integer oId;

    private Integer tId;

    private String tName;

    private Integer tInfoId;

    private String tInfoImg;

    private BigDecimal tInfoMoney;

    private Date oTime;

    private Integer oNumber;

    private BigDecimal oMoney;

    private String tInfoTitle;

    private String isEvaluation;

    private String isOrder;

    private static final long serialVersionUID = 1L;

    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public Integer gettInfoId() {
        return tInfoId;
    }

    public void settInfoId(Integer tInfoId) {
        this.tInfoId = tInfoId;
    }

    public String gettInfoImg() {
        return tInfoImg;
    }

    public void settInfoImg(String tInfoImg) {
        this.tInfoImg = tInfoImg;
    }

    public BigDecimal gettInfoMoney() {
        return tInfoMoney;
    }

    public void settInfoMoney(BigDecimal tInfoMoney) {
        this.tInfoMoney = tInfoMoney;
    }

    public Date getoTime() {
        return oTime;
    }

    public void setoTime(Date oTime) {
        this.oTime = oTime;
    }

    public Integer getoNumber() {
        return oNumber;
    }

    public void setoNumber(Integer oNumber) {
        this.oNumber = oNumber;
    }

    public BigDecimal getoMoney() {
        return oMoney;
    }

    public void setoMoney(BigDecimal oMoney) {
        this.oMoney = oMoney;
    }

    public String gettInfoTitle() {
        return tInfoTitle;
    }

    public void settInfoTitle(String tInfoTitle) {
        this.tInfoTitle = tInfoTitle;
    }

    public String getIsEvaluation() {
        return isEvaluation;
    }

    public void setIsEvaluation(String isEvaluation) {
        this.isEvaluation = isEvaluation;
    }

    public String getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(String isOrder) {
        this.isOrder = isOrder;
    }
}