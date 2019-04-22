package com.app.service;

import java.util.List;

import com.app.model.HomeImg;

public interface HomeImgService {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(HomeImg record);

    List<HomeImg> getHomeImgList();
}
