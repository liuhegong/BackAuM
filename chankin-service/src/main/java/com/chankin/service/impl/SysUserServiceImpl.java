package com.chankin.service.impl;

import com.chankin.dao.*;
import com.chankin.model.dto.LoginInfo;
import com.chankin.model.dto.PageInfo;
import com.chankin.model.dto.SysUserDto;
import com.chankin.model.entity.*;
import com.chankin.service.SysUserService;
import com.github.pagehelper.PageHelper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    @Autowired
    private SysLoginStatusMapper sysLoginStatusMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    //插入用户需要储存用户角色组织和用户权限
    @Override
    public long insertUser(SysUser user, String jobIds, String permissionIds) {
        sysUserMapper.insert(user);
        String[] jobIdArray = jobIds.split(",");
        for (String jobId : jobIdArray) {
            SysUserRoleOrganization sysUserRoleOrganization = new SysUserRoleOrganization();
            sysUserRoleOrganization.setSysUserId(user.getId());
            sysUserRoleOrganization.setSysRoleOrganizationId(Long.valueOf(jobId));
            sysUserRoleOrganization.setIsFinal(1);
            sysUserRoleOrganizationMapper.insert(sysUserRoleOrganization);
        }
        if (StringUtils.hasText(permissionIds)) {
            String[] permissionIdArray = permissionIds.split(",");
            for (String permissionId : permissionIdArray) {
                SysUserPermission userPermission = new SysUserPermission();
                userPermission.setSysUserId(user.getId());
                userPermission.setSysPermissionId(Long.valueOf(permissionId));
                userPermission.setIsFinal(1);
                sysUserPermissionMapper.insert(userPermission);
            }
        }
        return user.getId();
    }

    @Override
    public boolean isExistLoginName(String loginName) {
        return sysUserMapper.selectByLoginName(loginName);
    }

    //将状态设置为2
    @Override
    public void deleteById(long id) {
        sysUserMapper.deleteByPrimaryKey(id);
        sysUserPermissionMapper.deleteByUserId(id);
        sysUserRoleOrganizationMapper.deleteByUserId(id);
    }

    @Override
    public SysUser selectById(long id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean isExistLoginNameExcludeId(long id, String loginName) {
        return sysUserMapper.isExistLoginNameExcludeId(id, loginName);
    }

    @Override
    public void updateUser(SysUser user, String jobIds, String permissionIds) {
        sysUserMapper.updateByPrimaryKeySelective(user);
        sysUserPermissionMapper.deleteByUserId(user.getId());
        sysUserRoleOrganizationMapper.deleteByPrimaryKey(user.getId());
        String[] jobIdArray = jobIds.split(",");
        for (String jobId : jobIdArray) {
            SysUserRoleOrganization sysUserRoleOrganization = new SysUserRoleOrganization();
            sysUserRoleOrganization.setSysUserId(user.getId());
            sysUserRoleOrganization.setSysRoleOrganizationId(Long.valueOf(jobId));
            sysUserRoleOrganization.setIsFinal(1);
            sysUserRoleOrganizationMapper.insert(sysUserRoleOrganization);
        }
        if (StringUtils.hasText(permissionIds)) {
            String[] permissiongIdArray = permissionIds.split(",");
            for (String permissionId : permissiongIdArray) {
                SysUserPermission sysUserPermission = new SysUserPermission();
                sysUserPermission.setSysUserId(user.getId());
                sysUserPermission.setSysPermissionId(Long.valueOf(permissionId));
                sysUserPermission.setIsFinal(1);
                sysUserPermissionMapper.insert(sysUserPermission);
            }
        }


    }

    @Override
    public PageInfo selectPage(int page, int rows, String sort, String order, String loginName, String zhName, String email, String phone, String address) {
        System.out.println("page = {" + page + "],rows = [" + rows + "], sort = [" + sort + "], order = [" + order + "], loginName = [" + loginName + "], zhName = [" + zhName + "], email = [" + email + "], phone = [" + phone + "], address = [" + address + "]");
        int counts = sysUserMapper.selectCounts();

        PageHelper.startPage(page, rows);
        //获取排序后的user
        List<SysUser> sysUsers = sysUserMapper.selectAll(sort, order, loginName, zhName, email, phone, address);

        List<SysUserDto> sysUserDtos = new ArrayList<>();
        //将页面中的所有user信息封装到userdto中
        for (SysUser sysUser : sysUsers) {
            SysUserDto sysUserDto = new SysUserDto();
            BeanUtils.copyProperties(sysUser, sysUserDto);
            sysUserDto.setPassword("");
            sysUserDto.setPasswordSalt("");
            //获取用户权限列表
            List<SysUserPermission> userPermissions = sysUserPermissionMapper.selectByUserId(sysUser.getId());
            List<SysPermission> permissions = new ArrayList<>();
            for (SysUserPermission userPermission : userPermissions) {
                //通过权限id获取权限对象
                SysPermission sysPermission = sysPermissionMapper.selectById(userPermission.getSysPermissionId());
                //添加系统权限
                permissions.add(sysPermission);
            }
            List<SysUserRoleOrganization> userRoleOrganizations = sysUserRoleOrganizationMapper.selectByUserId(sysUser.getId());
            //添加用户角色机构到userdto中
            sysUserDto.setUserRoleOrganizations(userRoleOrganizations);
            sysUserDto.setPermissions(permissions);
            sysUserDtos.add(sysUserDto);
        }
        PageInfo pageInfo = new PageInfo(counts, sysUserDtos);

        return pageInfo;
    }

    @Override
    public void updateUser(SysUser user) {
        sysUserMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public SysUser selectUserByLoginName(String loginName) {
        return sysUserMapper.selectUserByLoginName(loginName);
    }

    @Override
    public boolean selectByLoginName(String loginName) {
        return sysUserMapper.selectByLoginName(loginName);
    }

    /*
     *  业务层登录逻辑
     *
     * */
    @Override
    public LoginInfo login(SysUser user, Serializable id, int platform) {
        log.debug("sessionId===:{}", id.toString());
        //t封装到dto中
        LoginInfo loginInfo = new LoginInfo();
        //通过反射将user的值赋值给loginInfo（前提是对象中属性的名字相同）
        BeanUtils.copyProperties(user, loginInfo);
        //获取该用户权限
        System.out.println("==========用户id============" + user.getId());
        List<SysUserPermission> userPermissions = sysUserPermissionMapper.selectByUserId(user.getId());
        List<SysPermission> permissions = new ArrayList<>();
        for (SysUserPermission userPermission : userPermissions) {
            SysPermission sysPermission = sysPermissionMapper.selectById(userPermission.getSysPermissionId());
            //将用户权限添加系统功能中
            permissions.add(sysPermission);
        }
        //通过用户id 查找角色组织
        List<SysUserRoleOrganization> userRoleOrganizations = sysUserRoleOrganizationMapper.selectByUserId(user.getId());
        loginInfo.setJobs(userRoleOrganizations);
        SysLoginStatus newLoginStatus = new SysLoginStatus();
        newLoginStatus.setSysUserId(user.getId());
        newLoginStatus.setSysUserZhName(user.getZhName());
        newLoginStatus.setSysUserLoginName(user.getLoginName());
        newLoginStatus.setSessionId(id.toString());
        newLoginStatus.setSessionExpires(new DateTime().plusDays(30).toDate());
        newLoginStatus.setPlatform(platform);
        //查询过往登录平台
        SysLoginStatus oldLoginStatus = sysLoginStatusMapper.selectByUserIdAndPlatform(user.getId(), platform);
        if (oldLoginStatus != null) {
            //将id和缓存比较,不同则删除缓存sessionid
            if (!oldLoginStatus.getSessionId().equals(id.toString())) {
                redisTemplate.opsForValue().getOperations().delete(oldLoginStatus.getSessionId());
            }
            oldLoginStatus.setStatus(2);
            //更新旧的登录状态为删除
            sysLoginStatusMapper.updateByPrimaryKeySelective(oldLoginStatus);
            newLoginStatus.setLastLoginTime(oldLoginStatus.getCreateTime());
        }
        //储存登录信息到数据库中
        sysLoginStatusMapper.insertSelective(newLoginStatus);
        return loginInfo;
    }


}
