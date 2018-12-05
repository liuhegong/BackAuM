package com.chankin.service;

import com.chankin.model.dto.PageInfo;
import com.chankin.model.dto.SysOrganizationTree;
import com.chankin.model.entity.SysOrganization;

import java.util.List;


public interface SysOrganizationService {
    Long insertOrganization(SysOrganization sysOrganization);

    int deleteOrganization(long id);

    void updateOrganization(SysOrganization sysOrganization);

    PageInfo selectPage(int page, int row, long id);

    SysOrganizationTree selectSysOrganizationTree(long id);

    List<SysOrganizationTree> selectChildrenTreeList(long id);

    boolean isExistFullName(String fullName);

    SysOrganization selectOrganization(long id);

    boolean isExistFullNameExcludeId(long id, String fullName);
}
