package com.chankin.model.entity;

import java.util.Date;

public class SysLoginStatus {
    private Long id;

    private Long sysUserId;

    private String sessionId;

    private Date sessionExpires;

    private String sysUserLoginName;

    private String sysUserZhName;

    private Date lastLoginTime;

    private Byte platform;

    private Long rank;

    private Date createTime;

    private Date updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    public Date getSessionExpires() {
        return sessionExpires;
    }

    public void setSessionExpires(Date sessionExpires) {
        this.sessionExpires = sessionExpires;
    }

    public String getSysUserLoginName() {
        return sysUserLoginName;
    }

    public void setSysUserLoginName(String sysUserLoginName) {
        this.sysUserLoginName = sysUserLoginName == null ? null : sysUserLoginName.trim();
    }

    public String getSysUserZhName() {
        return sysUserZhName;
    }

    public void setSysUserZhName(String sysUserZhName) {
        this.sysUserZhName = sysUserZhName == null ? null : sysUserZhName.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Byte getPlatform() {
        return platform;
    }

    public void setPlatform(Byte platform) {
        this.platform = platform;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}