package com.app.model;

import java.io.Serializable;

public class Foot implements Serializable {
    private Integer footId;

    private Integer tInfoId;

    private Integer tId;

    private String tInfoTitle;

    private String tName;

    private String tImg;

    private String tInfoImg;

    private String fTime;

    private static final long serialVersionUID = 1L;

    public Integer getFootId() {
        return footId;
    }

    public void setFootId(Integer footId) {
        this.footId = footId;
    }

    public Integer gettInfoId() {
        return tInfoId;
    }

    public void settInfoId(Integer tInfoId) {
        this.tInfoId = tInfoId;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public String gettInfoTitle() {
        return tInfoTitle;
    }

    public void settInfoTitle(String tInfoTitle) {
        this.tInfoTitle = tInfoTitle;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettImg() {
        return tImg;
    }

    public void settImg(String tImg) {
        this.tImg = tImg;
    }

    public String gettInfoImg() {
        return tInfoImg;
    }

    public void settInfoImg(String tInfoImg) {
        this.tInfoImg = tInfoImg;
    }

    public String getfTime() {
        return fTime;
    }

    public void setfTime(String fTime) {
        this.fTime = fTime;
    }
}