package com.chankin.service;

import com.chankin.model.dto.LoginInfo;
import com.chankin.model.dto.PageInfo;
import com.chankin.model.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

public interface SysUserService {
    long insertUser(SysUser user, String jobIds, String permissionIds);

    boolean isExistLoginName(String loginName);

    void deleteById(long id);

    SysUser selectById(long id);

    boolean isExistLoginNameExcludeId(long id, String loginName);

    void updateUser(SysUser user, String jobIds, String permissionIds);

    PageInfo selectPage(int page, int rows, String sort, String order, String loginName, String zhName, String email, String phone, String address);

    void updateUser(SysUser user);

    SysUser selectUserByLoginName(String loginName);

    LoginInfo login(SysUser user, Serializable id, int platform);

    boolean selectByLoginName(@Param("loginName") String loginName);


}
