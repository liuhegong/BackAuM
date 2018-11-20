package com.chankin.dao;

import com.chankin.model.entity.SysLog;
import com.chankin.model.entity.SysLogWithBLOBs;

public interface SysLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLogWithBLOBs record);

    int insertSelective(SysLogWithBLOBs record);

    SysLogWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    int updateByPrimaryKey(SysLog record);
}