package com.app.service;

import com.app.model.ULike;

public interface ULikeService {
    int deleteByPrimaryKey(Integer uLikeId);

    int insert(ULike record);

    int insertSelective(ULike record);

    ULike selectByPrimaryKey(Integer uLikeId);

    int updateByPrimaryKeySelective(ULike record);

    int updateByPrimaryKey(ULike record);
    
    ULike selectIsLike(ULike record);
}
