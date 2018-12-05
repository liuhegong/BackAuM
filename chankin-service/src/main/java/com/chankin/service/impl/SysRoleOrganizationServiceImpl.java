package com.chankin.service.impl;

import com.chankin.dao.SysOrganizationMapper;
import com.chankin.dao.SysRoleMapper;
import com.chankin.dao.SysRoleOrganizationMapper;
import com.chankin.model.dto.PageInfo;
import com.chankin.model.dto.SysRoleOrganizationTree;
import com.chankin.model.entity.SysOrganization;
import com.chankin.model.entity.SysRole;
import com.chankin.model.entity.SysRoleOrganization;
import com.chankin.service.SysRoleOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
public class SysRoleOrganizationServiceImpl implements SysRoleOrganizationService {
    private static Logger log = LoggerFactory.getLogger(SysRoleOrganizationServiceImpl.class);
    @Autowired
    private SysRoleOrganizationMapper roleOrganizationMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Override
    public boolean isExistName(String name, long parentId) {

        return roleOrganizationMapper.isExistName(name, parentId);
    }

    @Override
    public long insertRoleOrganization(SysRoleOrganization roleOrganization) {
        return roleOrganizationMapper.insertSelective(roleOrganization);
    }

    @Override
    public boolean isExistNameExcludeId(long id, String name, long parentId) {
        return roleOrganizationMapper.isExistNameExcludeId(id, name, parentId);

    }

    @Override
    public void updateRoleOrganization(SysRoleOrganization roleOrganization) {
        roleOrganizationMapper.updateByPrimaryKeySelective(roleOrganization);
    }

    @Override
    public SysRoleOrganization selectRoleOrganizationById(long id) {
        return roleOrganizationMapper.selectByPrimaryKey(id);
    }


    @Override
    public PageInfo selectPage(int page, int rows, long id) {
        int counts = roleOrganizationMapper.selectCounts();
        SysRoleOrganizationTree tree = selectSysRoleOrganizationTree(id);
        List<SysRoleOrganizationTree> list = new ArrayList<>();
        list.add(tree);
        //用PageInfo对结果进行包装
        return new PageInfo(counts, list);

    }

    @Override
    public SysRoleOrganizationTree selectSysRoleOrganizationTree(long id) {
        SysRoleOrganizationTree tree = new SysRoleOrganizationTree();
        //角色组织
        SysRoleOrganization roleOrganization = roleOrganizationMapper.selectByPrimaryKey(id);
        log.debug("roleOrganization: {}", roleOrganization);
        BeanUtils.copyProperties(roleOrganization, tree);
        if (roleOrganization == null) {
            return null;
        }
        //通过角色id获取名字
        SysRole role = sysRoleMapper.selectByPrimaryKey(roleOrganization.getSysRoleId());
        log.debug("role: {}", role);
        if (role != null) {
            tree.setSysRoleName(role.getName());
        }
        SysOrganization organization = sysOrganizationMapper.selectByPrimaryKey(roleOrganization.getSysOrganizationId());
        if (organization != null) {
            tree.setSysOrganizationName(organization.getName());
        }
        //子 角色组织 结构
        List<SysRoleOrganizationTree> childrenList = selectSysRoleOrganizationTreeChildrenList(id);
        //当前角色组织 添加子角色组织
        tree.setChildren(childrenList);
        for (int i = 0; i < childrenList.size(); i++) {
            //递归添加子树的子树
            tree.getChildren().set(i, selectSysRoleOrganizationTree(childrenList.get(i).getId()));
        }
        return tree;
    }

    /*
     *  查询子目录
     * */
    @Override
    public List<SysRoleOrganizationTree> selectSysRoleOrganizationTreeChildrenList(long id) {
        //根据父id查找 角色组织列
        List<SysRoleOrganization> childrenList = roleOrganizationMapper.selectChildren(id);
        //建立子树结构
        List<SysRoleOrganizationTree> childrenTreeList = new ArrayList<>();
        //遍历 子角色组织
        for (SysRoleOrganization s : childrenList) {
            //建立子树
            SysRoleOrganizationTree sysOrganizationTree = new SysRoleOrganizationTree();
            BeanUtils.copyProperties(s, sysOrganizationTree);
            //添加子树
            childrenTreeList.add(sysOrganizationTree);
        }
        return childrenTreeList;
    }
}
