package com.chankin.dao;

import com.chankin.model.entity.SysLog;
import com.chankin.model.entity.SysLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLogWithBLOBs record);

    int insertSelective(SysLogWithBLOBs record);

    SysLogWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    int updateByPrimaryKey(SysLog record);

    int selectCounts();

    List<SysLog> selectLog(@Param("sort") String sort, @Param("order")
            String order, @Param("method") String method, @Param("url")
                                   String url, @Param("param") String param, @Param("result") String result);

}