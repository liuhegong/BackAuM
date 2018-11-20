package com.chankin.service;

import com.chankin.model.dto.LoginInfo;
import com.chankin.model.entity.SysUser;
import com.github.pagehelper.PageInfo;

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

    SysUser selectByLoginName(String loginName);

    LoginInfo login(SysUser user, Serializable id, int platform);
}
