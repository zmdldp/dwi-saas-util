package com.dwi.basic.mq.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 操作日志配置类
 *
 * @author dwi
 * @date 2020年03月09日15:00:47
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = MqProperties.PREFIX)
public class MqProperties {
    public static final String PREFIX = "saas.rabbitmq";

    /**
     * 是否启用
     */
    private Boolean enabled = true;

}
