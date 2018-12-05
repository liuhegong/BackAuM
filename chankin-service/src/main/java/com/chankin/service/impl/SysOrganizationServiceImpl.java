package com.chankin.service.impl;

import com.chankin.dao.SysOrganizationMapper;
import com.chankin.model.dto.PageInfo;
import com.chankin.model.dto.SysOrganizationTree;
import com.chankin.model.entity.SysOrganization;
import com.chankin.service.SysOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class SysOrganizationServiceImpl implements SysOrganizationService {
    private static Logger log = LoggerFactory.getLogger(SysOrganizationServiceImpl.class);

    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Override
    public Long insertOrganization(SysOrganization sysOrganization) {
        return (long) sysOrganizationMapper.insertSelective(sysOrganization);

    }

    @Override
    public int deleteOrganization(long id) {
        SysOrganization sysOrganization = sysOrganizationMapper.selectByPrimaryKey(id);
        sysOrganization.setStatus(2);
        sysOrganizationMapper.updateByPrimaryKeySelective(sysOrganization);
        return 1;
    }

    @Override
    public void updateOrganization(SysOrganization sysOrganization) {
        sysOrganizationMapper.updateByPrimaryKeySelective(sysOrganization);
    }

    @Override
    public PageInfo selectPage(int page, int row, long id) {
        SysOrganizationTree sysOrganizationTree = selectSysOrganizationTree(id);
        List<SysOrganizationTree> list = new ArrayList<>();
        list.add(sysOrganizationTree);
        return new PageInfo(sysOrganizationMapper.selectCounts(), list);
    }

    @Override
    public SysOrganizationTree selectSysOrganizationTree(long id) {
        SysOrganizationTree tree = new SysOrganizationTree();
        SysOrganization sysOrganization = sysOrganizationMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(sysOrganization, tree);
        List<SysOrganizationTree> treeList = selectChildrenTreeList(id);
        tree.setChildren(treeList);
        for (int i = 0; i < treeList.size(); i++) {
            tree.getChildren().set(i, selectSysOrganizationTree(treeList.get(i).getId()));
        }
        return tree;
    }

    @Override
    public List<SysOrganizationTree> selectChildrenTreeList(long id) {
        List<SysOrganization> childrenList = sysOrganizationMapper.selectChildren(id);
        List<SysOrganizationTree> childrenTreeList = new ArrayList<>();
        for (SysOrganization s : childrenList) {
            SysOrganizationTree sysOrganizationTree = new SysOrganizationTree();
            BeanUtils.copyProperties(s, sysOrganizationTree);
            childrenTreeList.add(sysOrganizationTree);
        }
        return childrenTreeList;
    }

    @Override
    public boolean isExistFullName(String fullName) {
        return sysOrganizationMapper.isExistFullName(fullName);
    }

    @Override
    public SysOrganization selectOrganization(long id) {
        return sysOrganizationMapper.selectByPrimaryKey(id);

    }

    @Override
    public boolean isExistFullNameExcludeId(long id, String fullName) {

        return sysOrganizationMapper.isExistFullNameExcludeId(id, fullName);
    }
}
