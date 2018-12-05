package com.chankin.service;

import com.chankin.model.dto.PageInfo;
import com.chankin.model.dto.SysRoleOrganizationTree;
import com.chankin.model.entity.SysRoleOrganization;

import java.util.List;

public interface SysRoleOrganizationService {
    boolean isExistName(String name, long parentId);

    long insertRoleOrganization(SysRoleOrganization roleOrganization);

    boolean isExistNameExcludeId(long id, String name, long parentId);

    void updateRoleOrganization(SysRoleOrganization roleOrganization);

    SysRoleOrganization selectRoleOrganizationById(long id);

    PageInfo selectPage(int page, int rows, long id);

    SysRoleOrganizationTree selectSysRoleOrganizationTree(long id);

    List<SysRoleOrganizationTree> selectSysRoleOrganizationTreeChildrenList(long id);
}
