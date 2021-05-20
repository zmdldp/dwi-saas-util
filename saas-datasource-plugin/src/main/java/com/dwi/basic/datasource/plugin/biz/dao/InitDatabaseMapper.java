package com.dwi.basic.datasource.plugin.biz.dao;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 初始化数据库DAO
 *
 * @author dwi
 * @date 2020/09/02
 */
@Repository
@InterceptorIgnore(tenantLine = "true", dynamicTableName = "true")
public interface InitDatabaseMapper {
    /**
     * 创建数据库
     *
     * @param database
     * @return
     */
    int createDatabase(@Param("database") String database);


    /**
     * 删除数据库
     *
     * @param database
     * @return
     */
    int dropDatabase(@Param("database") String database);

    /**
     * 根据条件查询租户列表
     *
     * @param status      状态
     * @param connectType 连接类型
     * @return 租户编码
     */
     List<String> selectTenantCodeList(@Param("status") String status);           
}
