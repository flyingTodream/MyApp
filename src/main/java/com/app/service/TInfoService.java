package com.app.service;

import java.util.List;

import com.app.model.TInfo;
import com.app.model.TUserInfo;

public interface TInfoService {
	
	List<TInfo> homeSelectInfo(TInfo record);
	
	List<TInfo> searchInfo(String kw);
	
	
}
