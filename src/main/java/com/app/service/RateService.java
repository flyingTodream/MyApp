package com.app.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.app.model.TEvaluation;

public interface RateService {
    //获取评价列表
    List<TEvaluation> getRateListByTId(TEvaluation record);
    
    //获取景点平均分
    float getRateAvg(Integer tInfoId);
    
    int insertSelective(TEvaluation record);
    
    JSONObject getRateListAdmin(Integer aId,String tInfoTitle,Integer page);
}
