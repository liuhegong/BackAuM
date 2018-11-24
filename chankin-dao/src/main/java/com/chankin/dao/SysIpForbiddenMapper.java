package com.chankin.dao;

import com.chankin.model.entity.SysIpForbidden;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysIpForbiddenMapper {
    int selectCounts();


    List<SysIpForbidden> selectAll();

    int deleteByPrimaryKey(Long id);

    int insert(SysIpForbidden record);

    int insertSelective(SysIpForbidden record);

    SysIpForbidden selectByPrimaryKey(Long id);

    void updateByPrimaryKeySelective(SysIpForbidden record);

    int updateByPrimaryKey(SysIpForbidden record);

    boolean isExistIP(@Param("ip") String ip);

    void deleteById(@Param("id") long id);
}