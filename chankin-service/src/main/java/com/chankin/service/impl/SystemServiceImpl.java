package com.chankin.service.impl;

import com.chankin.dao.*;
import com.chankin.model.dto.PageInfo;
import com.chankin.model.dto.SysDataItemDto;
import com.chankin.model.entity.*;
import com.chankin.service.SystemService;
import com.chankin.util.SystemConstant;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SystemServiceImpl implements SystemService {
    private static final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private SysDataItemMapper sysDataItemMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private SysLoginStatusMapper sysLoginStatusMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleOrganizationMapper sysUserRoleOrganizationMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private SysDataGroupMapper sysDataGroupMapper;

    @Autowired
    private SysIpForbiddenMapper sysIpForbiddenMapper;

    @Autowired
    private SysRoleOrganizationMapper sysRoleOrganizationMapper;

    @Override
    public void logout(String loginName, Subject subject) {
        SysUser sysUser = sysUserMapper.selectUserByLoginName(loginName);
        List<SysLoginStatus> list = sysLoginStatusMapper.selectByUserId(sysUser.getId());
        SysLoginStatus sysLoginStatus = list.get(0);
        sysLoginStatus.setStatus(2);
        sysLoginStatusMapper.updateByPrimaryKeySelective(sysLoginStatus);
        subject.logout();
        redisTemplate.opsForValue().getOperations().delete(sysLoginStatus.getSessionId());
        //delete authrization cache
        redisTemplate.opsForValue().getOperations().delete(SystemConstant.shiro_cache_prefix + loginName);

    }

    //强制登出
    @Override
    public void forceLogout(long userId) {
        List<SysLoginStatus> list = sysLoginStatusMapper.selectByUserId(userId);
        for (int i = 0; i < list.size(); i++) {
            SysLoginStatus sysLoginStatus = list.get(i);
            sysLoginStatus.setStatus(2);
            sysLoginStatusMapper.updateByPrimaryKeySelective(sysLoginStatus);
            //删除对话
            redisTemplate.opsForValue().getOperations().delete(sysLoginStatus.getSessionId());
            //删除授权缓存
            redisTemplate.opsForValue().getOperations().delete(SystemConstant.shiro_cache_prefix + sysLoginStatus.getSysUserLoginName());
        }
        log.debug("force logout userId: {}", userId);
    }

    @Override
    public void clearAuthorizationInfoAll() {
        Set<Object> keys = redisTemplate.keys(SystemConstant.shiro_cache_prefix_keys);
        if (keys.size() > 0) {
            redisTemplate.opsForValue().getOperations().delete(keys);
            log.debug("clear authorization info cache key: {}", keys.toArray());
        }
    }

    @Override
    public void clearAuthorizationInfoCacheByUserId(long userId) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if (sysUser != null) {
            redisTemplate.opsForValue().getOperations().delete(SystemConstant.shiro_cache_prefix + sysUser.getLoginName());
        }
        log.debug("clear authorization info cache userID: {}", userId);
    }

    @Override
    public void clearAuthorizationInfoByRoleId(long roleId) {
        log.debug("clear authorization info cache by roleId: {}", roleId);
        List<Long> list = sysRoleOrganizationMapper.selectByRoleId(roleId);
        if (list.size() > 0) {
            for (long id : list) {
                List<Long> userIds = sysUserRoleOrganizationMapper.selectByRoleOrganizationId(id);
                if (userIds.size() > 0) {
                    for (Long userId : userIds) {
                        SysUser sysUser = sysUserMapper.selectById(userId);
                        if (sysUser != null) {
                            redisTemplate.opsForValue().getOperations().delete(SystemConstant.shiro_cache_prefix + sysUser.getLoginName());
                        }
                    }
                }
            }
        }
    }

    /*
     *  查询用户登录状态
     *  封装在PageInfo中返回
     * */
    @Override
    public PageInfo selectLogStatus(int page, int rows) {
        //查询登录状态为1的个数
        int counts = sysLoginStatusMapper.selectCounts();
        PageHelper.startPage(page, rows);
        List<SysLoginStatus> sysLoginStatuses = sysLoginStatusMapper.selectAll();
        return new PageInfo(counts, sysLoginStatuses);
    }

    /*
     *
     *  查询排序日志条数
     * */
    @Override
    public PageInfo selectLog(int page, int rows, String s, String order, String method, String url, String param, String result) {
        int counts = sysLogMapper.selectCounts();
        PageHelper.startPage(page, rows);
        List<SysLog> list = sysLogMapper.selectLog(s, order, method, url, param, result);
        log.debug(list.toString());
        return new PageInfo(counts, list);
    }

    @Override
    public void isnertSysControllerLog(SysLog runningLog) {

    }

    @Override
    public Long insertSysDataGroup(SysDataGroup sysDataGroup) {
        return Long.valueOf(sysDataGroupMapper.insertSelective(sysDataGroup));

    }

    /*
     *  判断数据字典组名是否存在
     * */
    @Override
    public boolean isExistDataGroupName(String name) {
        return sysDataGroupMapper.isExistName(name);

    }

    @Override
    public List<SysDataGroup> selectDataGroupList() {
        return sysDataGroupMapper.selectAll();
    }

    @Override
    public long insertSysDataItem(SysDataItem sysDataItem) {
        return Long.valueOf(sysDataItemMapper.insertSelective(sysDataItem));

    }

    @Override
    public boolean isExistDataItemKeyName(String key, long groupId) {
        return sysDataItemMapper.isExistName(key, groupId);
    }

    //删除数据字典 - 数据库和缓存
    @Override
    public void deleteDataItemById(Long id) {
        SysDataItem sysDataItem = sysDataItemMapper.selectByPrimaryKey(id);
        redisTemplate.opsForValue().getOperations().delete(sysDataItem.getSysDataGroupId() + "--" + sysDataItem.getKeyName());
        sysDataItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateDateItem(SysDataItem sysDataItem) {
        redisTemplate.opsForValue().getOperations().delete(sysDataItem.getSysDataGroupId() + "--" + sysDataItem.getKeyName());
        sysDataItemMapper.updateByPrimaryKeySelective(sysDataItem);
    }

    @Override
    public boolean isExistDataItemNameExcludeId(Long id, String key, long groupId) {
        return sysDataItemMapper.isExistDataItemNameExcludeId(id, key, groupId);
    }

    @Override
    public PageInfo selectDataItemPage(int page, int rows) {
        int counts = sysDataItemMapper.selectCounts();
        PageHelper.startPage(page, rows);
        List<SysDataItem> sysDataItems = sysDataItemMapper.selectAll();
        List<SysDataItemDto> resultList = new ArrayList<>();
        for (SysDataItem sysDataItem : sysDataItems) {
            SysDataItemDto sysDataItemDto = new SysDataItemDto();
            BeanUtils.copyProperties(sysDataItem, sysDataItemDto);
            SysDataGroup sysDataGroup = sysDataGroupMapper.selectByPrimaryKey(sysDataItem.getSysDataGroupId());
            sysDataItemDto.setSysDataGroupName(sysDataGroup.getName());
            resultList.add(sysDataItemDto);
        }
        return new PageInfo(counts, resultList);
    }

    @Override
    public SysDataItem selectDataItemById(Long id) {
        return sysDataItemMapper.selectByPrimaryKey(id);
    }


    //查询 数据字典 无则生存存到reids中
    @Override
    public String selectDataItemByKey(String key, Long groupId) {
        //从缓存中获取key和id
        String value = (String) redisTemplate.opsForValue().get(groupId + "--" + key);
        //初始化
        if (value == null) {
            log.debug("get groupId:{} key:{} value from DB", groupId, key);
            value = sysDataItemMapper.selectByKey(key, groupId);
            //添加到redis缓存中
            redisTemplate.opsForValue().set(groupId + "--" + key, value);
        }
        return value;
    }

    @Override
    public Long insertIp(SysIpForbidden sysIpForbidden) {
        sysIpForbiddenMapper.insertSelective(sysIpForbidden);
        return sysIpForbidden.getId();
    }

    @Override
    public void deleteIp(long id) {
        sysIpForbiddenMapper.deleteById(id);
    }

    @Override
    public PageInfo selectIp(int page, int rows) {
        int counts = sysIpForbiddenMapper.selectCounts();
        PageHelper.startPage(page, rows);
        List<SysIpForbidden> list = sysIpForbiddenMapper.selectAll();
        return new PageInfo(counts, list);
    }

    @Override
    public boolean isExistIp(String ip) {
        return sysIpForbiddenMapper.isExistIP(ip);

    }

    @Override
    public void updateIp(SysIpForbidden sysIpForbidden) {
        sysIpForbiddenMapper.updateByPrimaryKeySelective(sysIpForbidden);
    }

    @Override
    public boolean isExistIpExcludeId(String ip, long id) {
        return false;
    }

    @Override
    public boolean isForbiddenIp(String remoteAddr) {
        Boolean result = redisTemplate.opsForSet().isMember("ip_intercepter", remoteAddr);
        log.debug("isForbiddenIP result: {}", result);
        return result;
    }

    @Override
    public void insertSysControllerLog(SysLogWithBLOBs runningLog) {
        sysLogMapper.insertSelective(runningLog);
    }

    //更新字典 删除缓存
    @Override
    public void openIpIntercept() {
        SysDataItem sysDataItem = new SysDataItem();
        sysDataItem.setId(4L);
        sysDataItem.setKeyValue("true");
        sysDataItemMapper.updateByPrimaryKeySelective(sysDataItem);
        redisTemplate.opsForValue().getOperations().delete("3-ip_forbidden");
        List<SysIpForbidden> sysIpForbiddens = sysIpForbiddenMapper.selectAll();
        for (SysIpForbidden sysIpForbidden : sysIpForbiddens) {
            long time = System.currentTimeMillis() - sysIpForbidden.getExpireTime().getTime();
            log.debug("time: {}", time);
            if (time < 0) {
                redisTemplate.opsForSet().add("ip_intercepter", sysIpForbidden.getIp());
            }
        }
    }

    //关闭ip拦截器  - 更新数据库和删除缓存
    @Override
    public void closeIpIntercept() {
        SysDataItem sysDataItem = new SysDataItem();
        sysDataItem.setId(4L);
        sysDataItem.setKeyValue("false");
        sysDataItemMapper.updateByPrimaryKeySelective(sysDataItem);
        redisTemplate.opsForValue().getOperations().delete("3-ip_forbidden");
        redisTemplate.opsForSet().getOperations().delete("ip_intercepter");
    }

    @Override
    public boolean selectIPForbiddenStatus() {
        SysDataItem sysDataItem = sysDataItemMapper.selectByPrimaryKey(4L);
        if (sysDataItem.getKeyValue().equals("true")) {
            return true;
        }
        return false;
    }
}
