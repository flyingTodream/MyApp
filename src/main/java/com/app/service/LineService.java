package com.app.service;

import java.util.List;

import com.app.model.LInfo;

public interface LineService {

    //获取路线信息
    List<LInfo> getLineList(Integer tId);
    
    int deleteByPrimaryKey(Integer lId);


    int insertSelective(LInfo record);

    LInfo selectByPrimaryKey(Integer lId);
}
