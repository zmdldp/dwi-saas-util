package com.dwi.basic.log.event;


import cn.hutool.core.util.StrUtil;
import com.dwi.basic.context.ContextUtil;
import com.dwi.basic.log.entity.OptLogDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;

import java.util.function.Consumer;


/**
 * 异步监听日志事件
 *
 * @author dwi
 * @date 2020-07-01 15:13
 */
@Slf4j
@RequiredArgsConstructor
public class SysLogListener {

    private final Consumer<OptLogDTO> consumer;
    @Autowired
    private Environment env;
    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        OptLogDTO sysLog = (OptLogDTO) event.getSource();
        if (sysLog == null || (!"NONE".equalsIgnoreCase(env.getProperty("saas.database.multiTenantType")) && StrUtil.isEmpty(sysLog.getTenantCode()))) {
            log.debug("租户编码不存在，忽略操作日志=={}", sysLog != null ? sysLog.getRequestUri() : "");
            return;
        }
        ContextUtil.setTenant(sysLog.getTenantCode());
        consumer.accept(sysLog);
    }

}
