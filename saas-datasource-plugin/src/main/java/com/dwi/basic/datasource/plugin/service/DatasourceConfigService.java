package com.dwi.basic.datasource.plugin.service;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.dwi.basic.base.service.SuperService;
import com.dwi.basic.datasource.plugin.domain.entity.DatasourceConfig;

/**
 * <p>
 * 业务接口
 * 数据源
 * </p>
 *
 * @author dwi
 * @date 2020-08-21
 */
public interface DatasourceConfigService extends SuperService<DatasourceConfig> {

	/**
     * 测试数据源链接
     *
     * @param dataSourceProperty 数据源信息
     * @return
     */
    Boolean testConnection(DataSourceProperty dataSourceProperty);

}
