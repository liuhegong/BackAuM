package com.chankin.system.security.shiro;

import com.chankin.dao.*;
import com.chankin.model.entity.*;
import com.chankin.util.SystemConstant;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {
    private static Logger log = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private SysUserPermissionMapper sysUserPermissionMapper;
    @Autowired
    private SysUserMapper sysUserMapperpper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysUserRoleOrganizationMapper sysUserRoleOrganizationMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleOrganizationMapper sysRoleOrganizationMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /*
     *
     *  流程 --通过principal 获得登录名-1、查询数据库用户 - 根据用户名查用户权限(获取用户id-获取用户权限列表 - 迭代 ->(查找每个权限id-在系统中添加每个权限))
     *                               2、查询数据库用户id - 获得用户角色组织- 。。。根据角色权限 添加系统权限
     *   进行授权
     * */


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) { ///获取身份授权相关,principals一个身份集合
        log.debug("查询授权信息");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String loginStr = (String) principals.getPrimaryPrincipal();
        SysUser user = sysUserMapperpper.selectUserByLoginName(loginStr);
        //根据用户id 查找 用户权限
        List<SysUserPermission> userPermissions = sysUserPermissionMapper.selectByUserId(user.getId());
        Set<String> permissions = new HashSet<String>();
        Set<String> roles = new HashSet<String>();
        //添加权限
        for (SysUserPermission userPermission : userPermissions) {
            //根据用户权限 查找 权限id
            SysPermission sysPermission = sysPermissionMapper.selectById(userPermission.getSysPermissionId());
            //系统添加权限代码
            permissions.add(sysPermission.getCode());
        }
        //根据用户id 查找 用户角色组织
        List<SysUserRoleOrganization> userRoleOrganizations = sysUserRoleOrganizationMapper.selectByUserId(user.getId());
        for (SysUserRoleOrganization sysUserRoleOrganization : userRoleOrganizations) {
            //根据用户角色组织id 查找 角色组织
            SysRoleOrganization sysRoleOrganization = sysRoleOrganizationMapper.selectByPrimaryKey(sysUserRoleOrganization.getSysRoleOrganizationId());
            //根据角色id 查找 角色名
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sysRoleOrganization.getSysRoleId());
            //系统添加角色
            roles.add(sysRole.getName());
            //根据角色id 查找 角色权限
            List<SysRolePermission> sysRolePermissions = sysRolePermissionMapper.selectByRoleId(sysRole.getId());
            for (SysRolePermission sysRolePermission : sysRolePermissions) {
                //根据角色权限id 查找 权限
                SysPermission sysPermission = sysPermissionMapper.selectById(sysRolePermission.getSysPermissionId());
                //系统添加权限
                permissions.add(sysPermission.getCode());
            }
        }

        info.addRoles(roles);
        info.addStringPermissions(permissions);
        log.debug("角色信息: \n {}", roles.toString());
        log.debug("权限信息: \n{}", permissions.toString());
        return info;
    }


    /*
     *  登入验证
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException { //获取身份验证相关信息
        log.debug("登录验证");
        String loginName = (String) token.getPrincipal();
        SysUser sysUser = sysUserMapperpper.selectUserByLoginName(loginName);
        //获取到的加盐密码和盐值
        AuthenticationInfo anthenticationInfo = new SimpleAuthenticationInfo(loginName, sysUser.getPassword(), ByteSource.Util.bytes(sysUser.getPasswordSalt()), getName());
        return anthenticationInfo;
    }

    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        redisTemplate.delete(SystemConstant.shiro_cache_prefix + principals.getPrimaryPrincipal().toString());
    }

    @Override
    protected void doClearCache(PrincipalCollection principals) {
        log.debug("clearCachedAuthorizationInfo");
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        CredentialsMatcher cm = getCredentialsMatcher();
        if (cm != null) {
            if (!cm.doCredentialsMatch(token, info)) {
                //not successful - throw an exception to indicate this:
                String msg = "Submitted credentials for token [" + token + "] did not match the expected credentials.";
                throw new IncorrectCredentialsException(msg);
            }
        } else {
            throw new AuthenticationException("A CredentialsMatcher must be configured in order to verify " +
                    "credentials during authentication.  If you do not wish for credentials to be examined, you " +
                    "can configure an " + AllowAllCredentialsMatcher.class.getName() + " instance.");
        }
    }
}
