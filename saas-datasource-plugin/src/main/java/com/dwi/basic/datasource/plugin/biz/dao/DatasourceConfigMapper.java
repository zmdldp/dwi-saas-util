package com.dwi.basic.datasource.plugin.biz.dao;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.dwi.basic.base.mapper.SuperMapper;
import com.dwi.basic.datasource.plugin.domain.entity.DatasourceConfig;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 数据源
 * </p>
 *
 * @author dwi
 * @date 2020-08-21
 */
@Repository
@InterceptorIgnore(tenantLine = "true", dynamicTableName = "true")
public interface DatasourceConfigMapper extends SuperMapper<DatasourceConfig> {

    /**
     * 查询所有租户的数据源
     *
     * @param applicationName 服务名
     * @param status          状态
     * @param connectType     连接类型
     * @return
     */
    @Select("SELECT dc.id,dc.create_time,dc.created_by,dc.update_time,dc.updated_by, " +
            "       dc.name, dc.application, dc.connect_type, dc.username, dc.password, dc.url, dc.driver_class_name driverClassName, t.code as pool_name " +
            " from c_datasource_config dc INNER JOIN c_tenant_datasource_config tdc on dc.id = tdc.datasource_config_id " +
            "INNER JOIN c_tenant t on t.id = tdc.tenant_id " +
            "where dc.application = #{applicationName} " +
            "and t.`status` = #{status} ")
    List<DatasourceConfig> listByApplication(@Param("applicationName") String applicationName, @Param("status") String status
            );   
}
