package com.dwi.basic.msg;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * redis 配置类
 *
 * @author dwi
 * @date 2019-08-06 10:42
 */
@ConditionalOnClass(RedisConnectionFactory.class)
@EnableConfigurationProperties({RedisProperties.class})
@RequiredArgsConstructor
public class RedisAutoConfigure {

    /**
     * RedisTemplate配置
     *
     * @param factory redis链接工厂
     */
    @Bean("redisTemplate")
    @ConditionalOnMissingBean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        setSerializer(factory, template);
        return template;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void setSerializer(RedisConnectionFactory factory, RedisTemplate template) {
    	//key序列化
        RedisSerializer<?> keySerializer = new StringRedisSerializer();
        RedisSerializer<?> valueSerializer = new GenericJackson2JsonRedisSerializer();
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        template.setValueSerializer(valueSerializer);
        template.setConnectionFactory(factory);
    }

    @Bean("stringRedisTemplate")
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate();
        setSerializer(factory, template);
        return template;
    }
}
