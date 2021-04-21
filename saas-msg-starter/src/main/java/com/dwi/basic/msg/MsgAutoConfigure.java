package com.dwi.basic.msg;

import com.dwi.basic.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 缓存配置
 *
 * @author dwi
 * @date 2020/08/07
 */
@Slf4j
@EnableCaching
@Import({
         RedisAutoConfigure.class
})
public class MsgAutoConfigure {

    

}
