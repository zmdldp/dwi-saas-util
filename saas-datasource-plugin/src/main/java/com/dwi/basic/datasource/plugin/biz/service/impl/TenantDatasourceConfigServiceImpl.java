package com.dwi.basic.datasource.plugin.biz.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.dwi.basic.base.service.SuperServiceImpl;
import com.dwi.basic.datasource.plugin.biz.dao.TenantDatasourceConfigMapper;
import com.dwi.basic.datasource.plugin.biz.service.TenantDatasourceConfigService;
import com.dwi.basic.datasource.plugin.domain.entity.TenantDatasourceConfig;

import org.springframework.stereotype.Service;

/**
 * 租户数据源关系
 *
 * @author dwi
 * @date 2020/8/27 下午4:51
 */
@Service
@DS("master")
public class TenantDatasourceConfigServiceImpl extends SuperServiceImpl<TenantDatasourceConfigMapper, TenantDatasourceConfig> implements TenantDatasourceConfigService {
}
