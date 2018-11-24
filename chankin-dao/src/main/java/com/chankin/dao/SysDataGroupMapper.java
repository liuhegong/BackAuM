package com.chankin.dao;

import com.chankin.model.entity.SysDataGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDataGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDataGroup record);

    int insertSelective(SysDataGroup record);

    SysDataGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDataGroup record);

    int updateByPrimaryKey(SysDataGroup record);

    boolean isExistName(@Param("name") String name);

    List<SysDataGroup> selectAll();
}