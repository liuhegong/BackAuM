package com.chankin.service.impl;

import com.chankin.dao.SysDataItemMapper;
import com.chankin.model.entity.SysDataGroup;
import com.chankin.model.entity.SysDataItem;
import com.chankin.model.entity.SysIpForbidden;
import com.chankin.model.entity.SysLog;
import com.chankin.service.SystemService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SystemServiceImpl implements SystemService {
    private static final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private SysDataItemMapper sysDataItemMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @Override
    public void forceLogout(long userId) {

    }

    @Override
    public void clearAuthorizationInfoAll() {

    }

    @Override
    public void clearAuthorizationInfoByRoleId(long roleId) {

    }

    @Override
    public PageInfo selectLogStatus(int page, int rows) {
        return null;
    }

    @Override
    public PageInfo selectLog(int page, int rows, String s, String order, String method, String url, String param, String result) {
        return null;
    }

    @Override
    public void isnertSysControllerLog(SysLog runningLog) {

    }

    @Override
    public Long insertSysDataGroup(SysDataGroup sysDataGroup) {
        return null;
    }

    @Override
    public boolean isExistDataGroupName(String name) {
        return false;
    }

    @Override
    public List<SysDataGroup> selectDataGroupList() {
        return null;
    }

    @Override
    public long insertSysDataItem(SysDataItem sysDataItem) {
        return 0;
    }

    @Override
    public boolean isExistDataItemKeyName(String key, long groupId) {
        return false;
    }

    @Override
    public void deleteDataItemById(Long id) {

    }

    @Override
    public void updateDateItem(SysDataItem sysDataItem) {

    }

    @Override
    public boolean isExistDataItemNameExcludeId(Long id, String key, long groupId) {
        return false;
    }

    @Override
    public PageInfo selectDataItemPage(int page, int rows) {
        return null;
    }

    @Override
    public SysDataItem selectDataItemById(Long id) {
        return null;
    }

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
        return null;
    }

    @Override
    public void deleteIp(long id) {

    }

    @Override
    public PageInfo selectIp(int page, int rows) {
        return null;
    }

    @Override
    public boolean isExistIp(String ip) {
        return false;
    }

    @Override
    public boolean isExistIpExcludeId(String ip, long id) {
        return false;
    }

    @Override
    public boolean isForbiddenIp(String remoteAddr) {
        return false;
    }

    @Override
    public void openIpIntercept() {

    }

    @Override
    public void closeIpIntercept() {

    }

    @Override
    public boolean selectIPForbiddenStatus() {
        return false;
    }
}
