package com.chankin.service;

import com.chankin.model.dto.PageInfo;
import com.chankin.model.entity.SysRole;

public interface SysRoleService {
    boolean isExsitRoleName(String name);

    long insertRole(SysRole sysRole, String permissionIds);

    void updateRole(SysRole sysRole, String permissionIds);

    boolean isExsitRoleNameExcludeId(long id, String name);

    SysRole selectById(long id);

    PageInfo selectPage(int page, int row);

    void deleteRole(SysRole sysRole);
}
