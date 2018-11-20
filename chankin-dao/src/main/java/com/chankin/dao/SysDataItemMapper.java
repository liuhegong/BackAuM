package com.chankin.dao;

import com.chankin.model.entity.SysDataItem;
import org.apache.ibatis.annotations.Param;

public interface SysDataItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDataItem record);

    int insertSelective(SysDataItem record);

    SysDataItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDataItem record);

    int updateByPrimaryKey(SysDataItem record);

    String selectByKey(@Param("key") String key, @Param("groupId") Long groupId);

}