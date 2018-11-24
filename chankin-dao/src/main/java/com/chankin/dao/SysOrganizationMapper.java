package com.chankin.dao;

import com.chankin.model.entity.SysOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysOrganizationMapper {
    int deleteByPrimaryKey(Long id);

    List<SysOrganization> selectChildren(@Param("parentId") long parentId);

    int insert(SysOrganization record);

    int insertSelective(SysOrganization record);

    SysOrganization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysOrganization record);

    int updateByPrimaryKey(SysOrganization record);

    boolean isExistFullName(@Param("fullName") String fullName);

    boolean isExistFullNameExcludeId(@Param("id") long id, @Param("fullName") String fullName);

    int selectCounts();
}