package com.app.service;

import java.util.List;

import com.app.model.lDetail;

public interface LDetailService {
	 //获取路线详细信息
    List<lDetail> getDetailList(Integer lId);
}
