package com.chankin.dao;

import com.chankin.model.entity.SysIpForbidden;

public interface SysIpForbiddenMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysIpForbidden record);

    int insertSelective(SysIpForbidden record);

    SysIpForbidden selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysIpForbidden record);

    int updateByPrimaryKey(SysIpForbidden record);
}