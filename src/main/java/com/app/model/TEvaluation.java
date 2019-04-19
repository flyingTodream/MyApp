package com.app.model;

import java.io.Serializable;

public class TEvaluation implements Serializable {
    private Integer eId;

    private Integer tInfoId;

    private String tName;

    private Integer eGrade;

    private String eContent;

    private String eTime;

    private Integer tId;

    private String eIsAnonymous;

    private String tInfoTitle;

    private String tInfoImg;

    private Long tInfoMoney;

    private String tInfoIntroduce;
    
    private String tImg;

    public String gettImg() {
		return tImg;
	}

	public void settImg(String tImg) {
		this.tImg = tImg;
	}

	private static final long serialVersionUID = 1L;

    @Override
	public String toString() {
		return "TEvaluation [eId=" + eId + ", tInfoId=" + tInfoId + ", tName=" + tName + ", eGrade=" + eGrade
				+ ", eContent=" + eContent + ", eTime=" + eTime + ", tId=" + tId + ", eIsAnonymous=" + eIsAnonymous
				+ ", tInfoTitle=" + tInfoTitle + ", tInfoImg=" + tInfoImg + ", tInfoMoney=" + tInfoMoney
				+ ", tInfoIntroduce=" + tInfoIntroduce + ", tImg=" + tImg + "]";
	}

	public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    public Integer gettInfoId() {
        return tInfoId;
    }

    public void settInfoId(Integer tInfoId) {
        this.tInfoId = tInfoId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public Integer geteGrade() {
        return eGrade;
    }

    public void seteGrade(Integer eGrade) {
        this.eGrade = eGrade;
    }

    public String geteContent() {
        return eContent;
    }

    public void seteContent(String eContent) {
        this.eContent = eContent;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public String geteIsAnonymous() {
        return eIsAnonymous;
    }

    public void seteIsAnonymous(String eIsAnonymous) {
        this.eIsAnonymous = eIsAnonymous;
    }

    public String gettInfoTitle() {
        return tInfoTitle;
    }

    public void settInfoTitle(String tInfoTitle) {
        this.tInfoTitle = tInfoTitle;
    }

    public String gettInfoImg() {
        return tInfoImg;
    }

    public void settInfoImg(String tInfoImg) {
        this.tInfoImg = tInfoImg;
    }

    public Long gettInfoMoney() {
        return tInfoMoney;
    }

    public void settInfoMoney(Long tInfoMoney) {
        this.tInfoMoney = tInfoMoney;
    }

    public String gettInfoIntroduce() {
        return tInfoIntroduce;
    }

    public void settInfoIntroduce(String tInfoIntroduce) {
        this.tInfoIntroduce = tInfoIntroduce;
    }
}