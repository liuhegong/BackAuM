package com.chankin.dao;

import com.chankin.model.entity.SysPermissionGroup;

public interface SysPermissionGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysPermissionGroup record);

    int insertSelective(SysPermissionGroup record);

    SysPermissionGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermissionGroup record);

    int updateByPrimaryKey(SysPermissionGroup record);
}