package com.chankin.dao;

import com.chankin.model.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int selectCounts();

    List<SysUser> selectAll(@Param("sort") String sort, @Param("order") String order, @Param("loginName") String loginName, @Param("zhName") String zhName, @Param("email") String email, @Param("phone") String phone, @Param("address") String address);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    void updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectUserByLoginName(@Param("loginName") String loginName);

    boolean selectByLoginName(@Param("loginName") String loginName);

    boolean isExistLoginNameExcludeId(@Param("id") long id, @Param("loginName") String loginName);

    public SysUser selectById(@Param("id") Long id);

}