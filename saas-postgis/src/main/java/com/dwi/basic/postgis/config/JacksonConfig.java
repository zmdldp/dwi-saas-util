package com.dwi.basic.postgis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bedatadriven.jackson.datatype.jts.JtsModule;

@Configuration
public class JacksonConfig {


    /**
     * jackson-datatype-jts 的库可以完成org.locationtech.jts.geom.Geometry到GeoJson的转换
     * 
     * @return
     */
    @Bean
    public JtsModule jtsModule(){
        return new JtsModule();
    }
}
