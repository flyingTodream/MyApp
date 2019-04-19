package com.app.service;

import java.util.List;

import com.app.model.Foot;

public interface FootService {
    int deleteByPrimaryKey(Integer footId);


    int insertSelective(Foot record);
    
    List<Foot> getFootList(Integer tId);
}
