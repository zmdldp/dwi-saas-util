package com.dwi.basic.datasource.plugin.biz.dao;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.dwi.basic.base.mapper.SuperMapper;
import com.dwi.basic.datasource.plugin.domain.entity.TenantDatasourceConfig;

import org.springframework.stereotype.Repository;

/**
 * 租户数据源关系 Mapper
 *
 * @author dwi
 * @date 2020/8/27 下午4:48
 */
@Repository
@InterceptorIgnore(tenantLine = "true", dynamicTableName = "true")
public interface TenantDatasourceConfigMapper extends SuperMapper<TenantDatasourceConfig> {
}
