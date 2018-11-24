package com.chankin.dao;

import com.chankin.model.entity.SysLoginStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLoginStatusMapper {
    int selectCounts();
    int deleteByPrimaryKey(Long id);

    int insert(SysLoginStatus record);

    int insertSelective(SysLoginStatus record);

    SysLoginStatus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLoginStatus record);

    int updateByPrimaryKey(SysLoginStatus record);

    List<SysLoginStatus> selectByUserId(@Param("userId") Long userId);

    SysLoginStatus selectByUserIdAndPlatform(@Param("userId") Long userId, @Param("platform") int platform);

    List<SysLoginStatus> selectAll();
}