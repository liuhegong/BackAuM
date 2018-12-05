package com.chankin.service.impl;

import com.chankin.dao.SysPermissionMapper;
import com.chankin.dao.SysRoleMapper;
import com.chankin.dao.SysRolePermissionMapper;
import com.chankin.model.dto.PageInfo;
import com.chankin.model.dto.SysRoleDto;
import com.chankin.model.entity.SysPermission;
import com.chankin.model.entity.SysRole;
import com.chankin.model.entity.SysRolePermission;
import com.chankin.service.SysRoleService;
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
public class SysRoleServiceImpl implements SysRoleService {
    private static Logger log = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;


    @Override
    public boolean isExsitRoleName(String name) {
        return sysRoleMapper.isExistName(name);
    }

    @Override
    public long insertRole(SysRole sysRole, String permissionIds) {
        sysRoleMapper.insertSelective(sysRole);
        String[] permissionIdsArray = permissionIds.split(",");
        for (int i = 0; i < permissionIdsArray.length; i++) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setSysRoleId(sysRole.getId());
            sysRolePermission.setSysPermissionId(Long.valueOf(permissionIdsArray[i]));
            sysRolePermissionMapper.insertSelective(sysRolePermission);
        }
        return sysRole.getId();
    }

    @Override
    public void updateRole(SysRole sysRole, String permissionIds) {
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        String[] permissionIdsArray = permissionIds.split(",");
        for (int i = 0; i < permissionIdsArray.length; i++) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setSysRoleId(sysRole.getId());
            sysRolePermission.setSysPermissionId(Long.valueOf(permissionIdsArray[i]));
            sysRolePermissionMapper.insertSelective(sysRolePermission);
        }
    }

    @Override
    public boolean isExsitRoleNameExcludeId(long id, String name) {
        return sysRoleMapper.isExsitNameExcludeId(id, name);
    }

    @Override
    public SysRole selectById(long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    /*
     *  将role 信息和权限封装到dto中
     * */
    @Override
    public PageInfo selectPage(int page, int row) {
        int counts = sysRoleMapper.selectCounts();
        List<SysRole> sysRoles = sysRoleMapper.selectAll();
        List<SysRoleDto> roleDtos = new ArrayList<>();
        for (int i = 0; i < sysRoles.size(); i++) {
            SysRoleDto sysRoleDto = new SysRoleDto();
            BeanUtils.copyProperties(sysRoles.get(i), sysRoleDto);
            //获取角色权限
            List<SysRolePermission> sysRolePermisions = sysRolePermissionMapper.selectByRoleId(sysRoles.get(i).getId());

            List<SysPermission> sysPermissionList = new ArrayList<>();
            for (int j = 0; j < sysRolePermisions.size(); j++) {
                SysPermission sysPermission = sysPermissionMapper.selectById(sysRolePermisions.get(j).getSysPermissionId());
                sysPermissionList.add(sysPermission);
            }
            sysRoleDto.setSysPermissions(sysPermissionList);
            roleDtos.add(sysRoleDto);
        }
        return new PageInfo(counts, roleDtos);
    }

    @Override
    public void deleteRole(SysRole sysRole) {
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        sysRolePermissionMapper.deleteByPrimaryKey(sysRole.getId());
    }
}
