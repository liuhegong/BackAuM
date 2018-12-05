package com.chankin.service.impl;

import com.chankin.dao.SysPermissionGroupMapper;
import com.chankin.dao.SysPermissionMapper;
import com.chankin.model.dto.PageInfo;
import com.chankin.model.dto.SysPermissionDto;
import com.chankin.model.entity.SysPermission;
import com.chankin.model.entity.SysPermissionGroup;
import com.chankin.service.SysPermissionService;
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
public class SysPermissionServiceImpl implements SysPermissionService {
    private static Logger log = LoggerFactory.getLogger(SysPermissionServiceImpl.class);
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysPermissionGroupMapper sysPermissionGroupMapper;

    @Override
    public boolean isExistName(long groupId, String name) {
        return sysPermissionGroupMapper.isExistName(groupId, name);

    }

    @Override
    public boolean isExistCode(long groupId, String code) {
        return sysPermissionGroupMapper.isExistCode(groupId, code);
    }

    @Override
    public long insertPermission(SysPermission sysPermission) {
        return sysPermissionMapper.insertSelective(sysPermission);
    }

    @Override
    public SysPermission selectById(long id) {
        return sysPermissionMapper.selectById(id);
    }

    @Override
    public void update(SysPermission sysPermission) {
        sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);
    }

    @Override
    public boolean isExistNameExcludeId(long id, long groupId, String name) {
        return sysPermissionMapper.isExistNameExcludeId(id, groupId, name);
    }

    @Override
    public boolean isExistCodeExcludeId(long id, long groupId, String code) {
        return sysPermissionMapper.isExistCodeExcludeId(id, groupId, code);
    }

    @Override
    public PageInfo selectPage(int page, int rows) {
        int count = sysPermissionMapper.selectCounts();
        List<SysPermission> list = sysPermissionMapper.selectAll();
        List<SysPermissionDto> listResult = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SysPermissionDto sysPermissionDto = new SysPermissionDto();
            BeanUtils.copyProperties(list.get(i), sysPermissionDto);
            long groupId = sysPermissionDto.getSysPermissionGroupId();
            SysPermissionGroup sysPermissionGroup = sysPermissionGroupMapper.selectById(groupId);
            sysPermissionDto.setSysPermissionGroupName(sysPermissionGroup != null ? sysPermissionGroup.getName() : "");
            listResult.add(sysPermissionDto);
        }
        //对页面进行封装
        return new PageInfo(count, listResult);
    }

    @Override
    public boolean isExistGroupName(String name) {
        return sysPermissionGroupMapper.isExistGroupName(name);
    }

    @Override
    public long insertPermissionGroup(SysPermissionGroup sysPermissionGroup) {
        return sysPermissionGroupMapper.insertSelective(sysPermissionGroup);
    }

    @Override
    public List<SysPermissionGroup> selectGroup() {
        List<SysPermissionGroup> sysPermissionGroups = sysPermissionGroupMapper.selectAll();
        return sysPermissionGroups;
    }
}
