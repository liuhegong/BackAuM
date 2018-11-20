package com.chankin.dao;

import com.chankin.model.entity.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectUserByLoginName(@Param("loginName") String loginName);
}