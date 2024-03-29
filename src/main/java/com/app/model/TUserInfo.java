package com.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TUserInfo extends BaseModel implements Serializable {
    private Integer tId;

    private String tName;

    private String tImg;

    private String tPass;

    private String tEmail;

    private String tPhone;

    private String tQuestion;

    private String tAnswer;

    private String tAddress;

    private BigDecimal tMoney;

    private String tPayword;

    private String tRegtime;

    private static final long serialVersionUID = 1L;

    @Override
	public String toString() {
		return "TUserInfo [tId=" + tId + ", tName=" + tName + ", tImg=" + tImg + ", tPass=" + tPass + ", tEmail="
				+ tEmail + ", tPhone=" + tPhone + ", tQuestion=" + tQuestion + ", tAnswer=" + tAnswer + ", tAddress="
				+ tAddress + ", tMoney=" + tMoney + ", tPayword=" + tPayword + ", tRegtime=" + tRegtime + "]";
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

    public String gettImg() {
        return tImg;
    }

    public void settImg(String tImg) {
        this.tImg = tImg;
    }

    public String gettPass() {
        return tPass;
    }

    public void settPass(String tPass) {
        this.tPass = tPass;
    }

    public String gettEmail() {
        return tEmail;
    }

    public void settEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    public String gettPhone() {
        return tPhone;
    }

    public void settPhone(String tPhone) {
        this.tPhone = tPhone;
    }

    public String gettQuestion() {
        return tQuestion;
    }

    public void settQuestion(String tQuestion) {
        this.tQuestion = tQuestion;
    }

    public String gettAnswer() {
        return tAnswer;
    }

    public void settAnswer(String tAnswer) {
        this.tAnswer = tAnswer;
    }

    public String gettAddress() {
        return tAddress;
    }

    public void settAddress(String tAddress) {
        this.tAddress = tAddress;
    }

    public BigDecimal gettMoney() {
        return tMoney;
    }

    public void settMoney(BigDecimal tMoney) {
        this.tMoney = tMoney;
    }

    public String gettPayword() {
        return tPayword;
    }

    public void settPayword(String tPayword) {
        this.tPayword = tPayword;
    }

    public String gettRegtime() {
        return tRegtime;
    }

    public void settRegtime(String tRegtime) {
        this.tRegtime = tRegtime;
    }
}