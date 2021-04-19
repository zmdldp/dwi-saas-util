package com.dwi.basic.model;

import java.util.Map;

/**
 * 注入VO 父类
 *
 * @author dwi
 * 
 */
public interface EchoVO {

    /**
     * 回显值 集合
     *
     * @return 回显值 集合
     */
    Map<String, Object> getEchoMap();
}
