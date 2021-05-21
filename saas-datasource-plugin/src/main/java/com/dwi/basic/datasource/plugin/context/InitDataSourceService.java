package com.dwi.basic.datasource.plugin.context;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.dwi.basic.datasource.plugin.dao.DatasourceConfigMapper;
import com.dwi.basic.datasource.plugin.dao.InitDatabaseMapper;
import com.dwi.basic.datasource.plugin.domain.entity.DatasourceConfig;
import com.dwi.basic.datasource.plugin.domain.enumeration.TenantConnectTypeEnum;
import com.dwi.basic.datasource.plugin.domain.enumeration.TenantStatusEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dwi
 * @date 2020/12/30 8:57 下午
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class InitDataSourceService {

    private final DatasourceConfigMapper datasourceConfigMapper;
    private final DataSourceService dataSourceService;
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 启动项目时，调用初始化数据源
     *
     * @return
     */
    @DS("master")
    public boolean initDataSource() {
    	//保存or更新服务默认数据源
    	dataSourceService.saveLocalDefaultDatasource();
        // LOCAL 类型的数据源初始化
//        List<String> list = initDatabaseMapper.selectTenantCodeList(TenantStatusEnum.NORMAL.name(), TenantConnectTypeEnum.LOCAL.name());
//        list.forEach(dataSourceService::addLocalDynamicRoutingDataSource);

        // REMOTE 类型的数据源初始化
    	
    	//租户数据源初始化
        List<DatasourceConfig> dcList = datasourceConfigMapper.listByApplication(applicationName, TenantStatusEnum.NORMAL.name());
        dcList.forEach(dc -> {
            DataSourceProperty dataSourceProperty = new DataSourceProperty();
            BeanUtils.copyProperties(dc, dataSourceProperty);
            dataSourceService.addDynamicRoutingDataSource(dataSourceProperty);
        });
        log.info("初始化租户数据源成功");
        return true;
    }
}
