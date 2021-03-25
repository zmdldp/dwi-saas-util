package com.dwi.basic.datasource.plugin.context;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.dwi.basic.datasource.plugin.domain.dto.DataSourcePropertyDTO;

import java.util.Set;

/**
 * 数据源管理
 *
 * @author dwi
 * @date 2020年03月15日11:31:57
 */
public interface DataSourceService {
	
	/**
	 * 保存服务本地数据源配置
	 * @return
	 */
	boolean saveLocalDefaultDatasource();
    /**
     * 查询所有的数据源
     *
     * @return
     */
    Set<String> findAll();

    /**
     * 新增Druid数据源
     *
     * @param dto
     * @return
     */
    Set<String> addDynamicRoutingDataSource(DataSourceProperty dto);

    /**
     * 新增Druid数据源
     *
     * @param tenant
     * @return
     */
   // boolean addLocalDynamicRoutingDataSource(String tenant);

    /**
     * 删除指定的数据源
     *
     * @param name 租户编码
     * @return
     */
    Set<String> remove(String name);

    /**
     * 测试链接
     *
     * @param dataSourceProperty
     * @return
     */
   // boolean testConnection(DataSourceProperty dataSourceProperty);

    /**
     * 初始化 数据源链接
     *
     * @param dataSourceProperty
     * @return
     */
    boolean initConnect(DataSourcePropertyDTO dataSourceProperty);
}
