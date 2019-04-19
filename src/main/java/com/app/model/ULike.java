package com.app.model;

import java.io.Serializable;

public class ULike implements Serializable {
    private Integer uLikeId;

    private Integer tId;

    private Integer tInfoId;

    private String uTime;
    
    private String tImg;

    private String tTitle;
    
    public String gettTitle() {
		return tTitle;
	}

	public void settTitle(String tTitle) {
		this.tTitle = tTitle;
	}

	public String gettImg() {
		return tImg;
	}

	public void settImg(String tImg) {
		this.tImg = tImg;
	}

	private static final long serialVersionUID = 1L;

    public Integer getuLikeId() {
        return uLikeId;
    }

    public void setuLikeId(Integer uLikeId) {
        this.uLikeId = uLikeId;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public Integer gettInfoId() {
        return tInfoId;
    }

    public void settInfoId(Integer tInfoId) {
        this.tInfoId = tInfoId;
    }

    public String getuTime() {
        return uTime;
    }

    public void setuTime(String uTime) {
        this.uTime = uTime;
    }
}