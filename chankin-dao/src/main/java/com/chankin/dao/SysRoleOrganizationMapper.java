package com.chankin.dao;

import com.chankin.model.entity.SysRoleOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleOrganizationMapper {


    int deleteByPrimaryKey(Long id);

    int insert(SysRoleOrganization record);

    int insertSelective(SysRoleOrganization record);

    SysRoleOrganization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleOrganization record);

    int updateByPrimaryKey(SysRoleOrganization record);

    boolean isExistName(@Param("name") String name, @Param("parentId") long parentId);

    boolean isExistNameExcludeId(@Param("id") long id, @Param("name") String name, @Param("parentId") long parentId);

    int selectCounts();

    List<SysRoleOrganization> selectChildren(@Param("parentId") long parentId);

    List<Long> selectByRoleId(@Param("roleId") long roleId);
}