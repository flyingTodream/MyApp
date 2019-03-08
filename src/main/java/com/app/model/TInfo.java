package com.app.model;

import java.io.Serializable;

public class TInfo implements Serializable {
    private Integer tInfoId;

    private Integer aId;

    private Long tInfoMoney;

    private String tInfoImg;

    private String tInfoAddress;

    private String tInfoTitle;

    private String tInfoIntroduce;

    private static final long serialVersionUID = 1L;

    public Integer gettInfoId() {
        return tInfoId;
    }

    public void settInfoId(Integer tInfoId) {
        this.tInfoId = tInfoId;
    }

    public Integer getaId() {
        return aId;
    }

    public void setaId(Integer aId) {
        this.aId = aId;
    }

    public Long gettInfoMoney() {
        return tInfoMoney;
    }

    public void settInfoMoney(Long tInfoMoney) {
        this.tInfoMoney = tInfoMoney;
    }

    public String gettInfoImg() {
        return tInfoImg;
    }

    public void settInfoImg(String tInfoImg) {
        this.tInfoImg = tInfoImg;
    }

    public String gettInfoAddress() {
        return tInfoAddress;
    }

    public void settInfoAddress(String tInfoAddress) {
        this.tInfoAddress = tInfoAddress;
    }

    public String gettInfoTitle() {
        return tInfoTitle;
    }

    public void settInfoTitle(String tInfoTitle) {
        this.tInfoTitle = tInfoTitle;
    }

    public String gettInfoIntroduce() {
        return tInfoIntroduce;
    }

    public void settInfoIntroduce(String tInfoIntroduce) {
        this.tInfoIntroduce = tInfoIntroduce;
    }
}