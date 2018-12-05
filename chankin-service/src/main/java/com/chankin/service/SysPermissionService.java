package com.chankin.service;

import com.chankin.model.dto.PageInfo;
import com.chankin.model.entity.SysPermission;
import com.chankin.model.entity.SysPermissionGroup;

import java.util.List;

public interface SysPermissionService {
    boolean isExistName(long groupId, String name);

    boolean isExistCode(long groupId, String code);

    long insertPermission(SysPermission sysPermission);


    SysPermission selectById(long id);

    void update(SysPermission sysPermission);

    boolean isExistNameExcludeId(long id, long groupId, String name);

    boolean isExistCodeExcludeId(long id, long groupId, String code);

    PageInfo selectPage(int page, int rows);

    boolean isExistGroupName(String name);

    long insertPermissionGroup(SysPermissionGroup sysPermissionGroup);

    List<SysPermissionGroup> selectGroup();
}

