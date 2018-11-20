package com.chankin.dao;

import com.chankin.model.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

public interface SysPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    SysPermission selectById(@Param("id") Long id);
}