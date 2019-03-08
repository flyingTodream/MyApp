package com.app.mapper;

import com.app.model.ULike;

public interface ULikeMapper {
    int deleteByPrimaryKey(Integer uLikeId);

    int insert(ULike record);

    int insertSelective(ULike record);

    ULike selectByPrimaryKey(Integer uLikeId);

    int updateByPrimaryKeySelective(ULike record);

    int updateByPrimaryKey(ULike record);
}