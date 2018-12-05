package com.chankin.service;

import com.chankin.model.dto.PageInfo;
import com.chankin.model.entity.*;
import org.apache.shiro.subject.Subject;

import java.util.List;

public interface SystemService {

    void logout(String loginName, Subject subject);

    //强制退出
    void forceLogout(long userId);

    //清除授权缓存信息
    void clearAuthorizationInfoAll();

    //更新角色时，通过角色id清除授权信息
    void clearAuthorizationInfoByRoleId(long roleId);

    void clearAuthorizationInfoCacheByUserId(long userId);

    //用户登录状态
    PageInfo selectLogStatus(int page, int rows);

    //查询日志列表
    PageInfo selectLog(int page, int rows, String s, String order, String method, String url, String param, String result);

    //储存请求信息
    void isnertSysControllerLog(SysLog runningLog);

    //储存系统验证信息分组
    Long insertSysDataGroup(SysDataGroup sysDataGroup);

    //判断是否数据组名是否存在
    boolean isExistDataGroupName(String name);

    List<SysDataGroup> selectDataGroupList();

    //插入数据字典
    long insertSysDataItem(SysDataItem sysDataItem);

    boolean isExistDataItemKeyName(String key, long groupId);

    void deleteDataItemById(Long id);

    void updateDateItem(SysDataItem sysDataItem);

    boolean isExistDataItemNameExcludeId(Long id, String key, long groupId);

    PageInfo selectDataItemPage(int page, int rows);

    SysDataItem selectDataItemById(Long id);

    //通过key和组id查询数据字典
    String selectDataItemByKey(String key, Long groupId);

    Long insertIp(SysIpForbidden sysIpForbidden);

    void deleteIp(long id);

    PageInfo selectIp(int page, int rows);

    boolean isExistIp(String ip);

    void updateIp(SysIpForbidden sysIpForbidden);

    boolean isExistIpExcludeId(String ip, long id);

    boolean isForbiddenIp(String remoteAddr);

    void openIpIntercept();

    void closeIpIntercept();

    boolean selectIPForbiddenStatus();

    void insertSysControllerLog(SysLogWithBLOBs runningLog);


}
