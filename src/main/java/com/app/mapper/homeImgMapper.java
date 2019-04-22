package com.app.mapper;

import java.util.List;

import com.app.model.HomeImg;

public interface homeImgMapper {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(HomeImg record);

    List<HomeImg> getHomeImgList();


}