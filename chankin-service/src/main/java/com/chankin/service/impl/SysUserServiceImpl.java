package com.chankin.service.impl;

import com.chankin.dao.SysPermissionMapper;
import com.chankin.dao.SysUserMapper;
import com.chankin.dao.SysUserPermissionMapper;
import com.chankin.dao.SysUserRoleOrganizationMapper;
import com.chankin.model.dto.LoginInfo;
import com.chankin.model.entity.SysPermission;
import com.chankin.model.entity.SysUser;
import com.chankin.model.entity.SysUserPermission;
import com.chankin.model.entity.SysUserRoleOrganization;
import com.chankin.service.SysUserService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserPermissionMapper sysUserPermissionMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysUserRoleOrganizationMapper sysUserRoleOrganizationMapper;

    @Override
    public long insertUser(SysUser user, String jobIds, String permissionIds) {
        return 0;
    }

    @Override
    public boolean isExistLoginName(String loginName) {
        return false;
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public SysUser selectById(long id) {
        return null;
    }

    @Override
    public boolean isExistLoginNameExcludeId(long id, String loginName) {
        return false;
    }

    @Override
    public void updateUser(SysUser user, String jobIds, String permissionIds) {

    }

    @Override
    public PageInfo selectPage(int page, int rows, String sort, String order, String loginName, String zhName, String email, String phone, String address) {
        return null;
    }

    @Override
    public void updateUser(SysUser user) {

    }

    @Override
    public SysUser selectByLoginName(String loginName) {
        return sysUserMapper.selectUserByLoginName(loginName);
    }

    @Override
    public LoginInfo login(SysUser user, Serializable id, int platform) {
        log.debug("sessionId===:{}", id.toString());
        //todo 封装到dto中
        LoginInfo loginInfo = new LoginInfo();
        //通过反射将user的值赋值给loginInfo（前提是对象中属性的名字相同）
        BeanUtils.copyProperties(user, loginInfo);
        //获取该用户权限
        List<SysUserPermission> userPermissions = sysUserPermissionMapper.selectByUserId(user.getId());
        List<SysPermission> permissions = new ArrayList<>();
        for (SysUserPermission userPermission : userPermissions) {
            SysPermission sysPermission = sysPermissionMapper.selectById(userPermission.getSysPermissionId());
            //将用户权限添加系统功能中
            permissions.add(sysPermission);
        }
        //通过用户id 查找角色组织
        List<SysUserRoleOrganization> userRoleOrganizations = sysUserRoleOrganizationMapper.selectByUserId(user.getId());


        return loginInfo;
    }
}
