package com.chankin.dao;

import com.chankin.model.entity.SysDataItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDataItemMapper {
    int selectCounts();
    int deleteByPrimaryKey(Long id);

    int insert(SysDataItem record);

    int insertSelective(SysDataItem record);

    SysDataItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDataItem record);

    int updateByPrimaryKey(SysDataItem record);

    String selectByKey(@Param("key") String key, @Param("groupId") long groupId);

    boolean isExistName(@Param("key") String key, @Param("groupId") long groupId);

    boolean isExistDataItemNameExcludeId(@Param("id") Long id, @Param("key") String key, @Param("groupId") long groupId);

    void deleteById(@Param("id") Long id);

    List<SysDataItem> selectAll();

}