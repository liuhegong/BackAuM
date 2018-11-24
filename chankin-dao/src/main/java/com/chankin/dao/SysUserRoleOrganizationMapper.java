package com.chankin.dao;

import com.chankin.model.entity.SysUserRoleOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleOrganizationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserRoleOrganization record);

    int insertSelective(SysUserRoleOrganization record);

    SysUserRoleOrganization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRoleOrganization record);

    int updateByPrimaryKey(SysUserRoleOrganization record);

    List<SysUserRoleOrganization> selectByUserId(@Param("userId") Long userId);

    void deleteByUserId(@Param("userId") Long userId);

    List<Long> selectByRoleOrganizationId(@Param("roleOrganizationId") long roleOrganizationId);
}