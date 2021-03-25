package com.dwi.basic.datasource.plugin.context.strategy;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.dwi.basic.database.properties.DatabaseProperties;
import com.dwi.basic.datasource.plugin.context.DataSourceService;
import com.dwi.basic.datasource.plugin.domain.dto.DataSourcePropertyDTO;
import com.dwi.basic.exception.BizException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * 数据源管理
 * <p>
 * saas.database.multiTenantType != DATASOURCE 时，该类才会生效
 *
 * @author dwi
 * @date 2020年03月15日11:35:08
 */
@Service
@ConditionalOnExpression("!'DATASOURCE'.equals('${saas.database.multiTenantType}')")
@Slf4j
@RequiredArgsConstructor
public class DefaultDataSourceServiceImpl implements DataSourceService {

    private final DatabaseProperties databaseProperties;

    @Override
    public Set<String> findAll() {
        throw BizException.wrap("%s(%s)模式不允许该操作", databaseProperties.getMultiTenantType().name(), databaseProperties.getMultiTenantType().getDescribe());
    }

    @Override
    public Set<String> remove(String name) {
        throw BizException.wrap("%s(%s)模式不允许该操作", databaseProperties.getMultiTenantType().name(), databaseProperties.getMultiTenantType().getDescribe());
    }

//    @Override
//    public boolean testConnection(DataSourceProperty dataSourceProperty) {
//    	 throw BizException.wrap("%s(%s)模式不允许该操作", databaseProperties.getMultiTenantType().name(), databaseProperties.getMultiTenantType().getDescribe());
//    	//return false;
//    }


    @Override
    public Set<String> addDynamicRoutingDataSource(DataSourceProperty dto) {
    	 throw BizException.wrap("%s(%s)模式不允许该操作", databaseProperties.getMultiTenantType().name(), databaseProperties.getMultiTenantType().getDescribe());
    	//return null;
    }

//    @Override
//    public boolean addLocalDynamicRoutingDataSource(String tenant) {
//    	 throw BizException.wrap("%s(%s)模式不允许该操作", databaseProperties.getMultiTenantType().name(), databaseProperties.getMultiTenantType().getDescribe());
//    	//return false;
//    }

    @Override
    public boolean initConnect(DataSourcePropertyDTO dataSourceProperty) {
    	 throw BizException.wrap("%s(%s)模式不允许该操作", databaseProperties.getMultiTenantType().name(), databaseProperties.getMultiTenantType().getDescribe());
    	//return false;
    }

	@Override
	public boolean saveLocalDefaultDatasource() {
		throw BizException.wrap("%s(%s)模式不允许该操作", databaseProperties.getMultiTenantType().name(), databaseProperties.getMultiTenantType().getDescribe());
		//return false;
	}
}
