package com.chankin.dao;

import com.chankin.model.entity.SysPermissionGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPermissionGroupMapper {
    int deleteByPrimaryKey(Long id);

    boolean isExistName(@Param("groupId") long groupId, @Param("name") String name);

    boolean isExistCode(@Param("groupId") long groupId, @Param("code") String code);

    boolean isExistGroupName(@Param("name") String name);

    public SysPermissionGroup selectById(@Param("id") Long id);

    int insert(SysPermissionGroup record);

    int insertSelective(SysPermissionGroup record);

    public List<SysPermissionGroup> selectAll();

    SysPermissionGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermissionGroup record);

    int updateByPrimaryKey(SysPermissionGroup record);
}