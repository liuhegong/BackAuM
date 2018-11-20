package com.chankin.dao;

import com.chankin.model.entity.SysLoginStatus;

public interface SysLoginStatusMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLoginStatus record);

    int insertSelective(SysLoginStatus record);

    SysLoginStatus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLoginStatus record);

    int updateByPrimaryKey(SysLoginStatus record);
}