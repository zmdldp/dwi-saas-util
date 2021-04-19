package com.dwi.basic.echo;

import com.dwi.basic.echo.aspect.EchoResultAspect;
import com.dwi.basic.echo.core.EchoService;
import com.dwi.basic.echo.properties.EchoProperties;
import com.dwi.basic.echo.typehandler.RemoteDataTypeHandler;
import com.dwi.basic.model.LoadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 关联字段数据注入工具 自动配置类
 *
 * @author dwi
 * @date 2019/09/20
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(EchoProperties.class)
public class EchoAutoConfiguration {
    private final EchoProperties remoteProperties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = EchoProperties.PREFIX, name = "aop-enabled", havingValue = "true", matchIfMissing = true)
    public EchoResultAspect getEchoResultAspect(EchoService echoService) {
        return new EchoResultAspect(echoService);
    }

    @Bean
    @ConditionalOnMissingBean
//    @ConditionalOnProperty(prefix = EchoProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
    public EchoService getEchoService(Map<String, LoadService> strategyMap) {
        return new EchoService(remoteProperties, strategyMap);
    }

    /**
     * Mybatis 类型处理器： 处理 RemoteData 类型的字段
     */
    @Bean
    @ConditionalOnMissingBean
    public RemoteDataTypeHandler getRemoteDataTypeHandler() {
        return new RemoteDataTypeHandler();
    }
}

