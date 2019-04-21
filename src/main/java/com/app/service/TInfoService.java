package com.app.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.app.model.TInfo;

public interface TInfoService {
	
	List<TInfo> homeSelectInfo(TInfo record);
	
	List<TInfo> searchInfo(String kw);
	
	JSONObject selectInfoPage(TInfo record);
	
    int deleteByPrimaryKey(Integer tInfoId);


    int insertSelective(TInfo record);

    TInfo selectByPrimaryKey(Integer tInfoId);

    int updateByPrimaryKeySelective(TInfo record);
}
