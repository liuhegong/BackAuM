package com.chankin.dao;

import com.chankin.model.entity.SysUser;
import com.chankin.model.entity.SysUserPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserPermission record);

    int insertSelective(SysUserPermission record);

    SysUserPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserPermission record);

    int updateByPrimaryKey(SysUserPermission record);

    List<SysUserPermission> selectByUserId(@Param("userId") Long userId);

    void deleteByUserId(@Param("userId") long userId);
}