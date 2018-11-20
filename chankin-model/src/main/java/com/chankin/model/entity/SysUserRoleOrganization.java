package com.chankin.model.entity;

import java.util.Date;

public class SysUserRoleOrganization {
    private Long id;

    private Long sysUserId;

    private Long sysRoleOrganizationId;

    private Long rank;

    private Date createTime;

    private Date updateTime;

    private Long createBy;

    private Long updateBy;

    private Byte status;

    private Byte isFinal;

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

    public Long getSysRoleOrganizationId() {
        return sysRoleOrganizationId;
    }

    public void setSysRoleOrganizationId(Long sysRoleOrganizationId) {
        this.sysRoleOrganizationId = sysRoleOrganizationId;
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

    public Byte getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Byte isFinal) {
        this.isFinal = isFinal;
    }
}