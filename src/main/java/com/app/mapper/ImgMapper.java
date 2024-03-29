package com.app.mapper;

import java.util.List;

import com.app.model.Img;

public interface ImgMapper {
    int deleteByPrimaryKey(Integer imgId);

    int insert(Img record);

    int insertSelective(Img record);

    Img selectByPrimaryKey(Integer imgId);

    int updateByPrimaryKeySelective(Img record);

    int updateByPrimaryKey(Img record);
    
    List<Img> selImg(Integer tId);
}