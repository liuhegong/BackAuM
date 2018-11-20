package com.chankin.dao;

import com.chankin.model.entity.SysRoleOrganization;

public interface SysRoleOrganizationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleOrganization record);

    int insertSelective(SysRoleOrganization record);

    SysRoleOrganization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleOrganization record);

    int updateByPrimaryKey(SysRoleOrganization record);


}