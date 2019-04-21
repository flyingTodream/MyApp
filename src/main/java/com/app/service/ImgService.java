package com.app.service;

import java.util.List;

import com.app.model.Img;

public interface ImgService {
	List<Img> selImg(Integer tId);
	
    int deleteByPrimaryKey(Integer imgId);


    int insertSelective(Img record);
}
