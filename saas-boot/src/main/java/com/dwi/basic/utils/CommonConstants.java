package com.dwi.basic.utils;

/**
 * 公共常量
 * 
 * @author dwi
 *
 */
public class CommonConstants {
	
    /**
     * 本地默认数据库名
     */
	public static String DEF_DATASOURCE_NAME = "-本地数据库";   
    /**
     * 平台租户默认企业ID
     */
	public static Long DEF_TENANT_ID = 1L;
    /**
     *  平台租户默认编码
     */
	public static  String DEF_TENANT_CODE = "0000";
    
    /**
	 * 应用名配置key
	 */
	public static String APPLICATION_NAME_KEY = "spring.application.name";
	
	/**
	 * 应用名配置变量
	 */
	public static String APPLICATION_NAME_VAR = "applicationName";
	
	/**
	 * 多租户类型配置key
	 */
	public static String MULTI_TENANT_TYPE_KEY = "saas.database.multiTenantType";
	
	public static String MULTI_TENANT_TYPE_VAR = "multiTenantType";
	
	/**
	 * 应用根路径配置key
	 */
	public static String CONTEXT_PATH_KEY = "server.servlet.context-path";
	
	public static String CONTEXT_PATH_VAR = "contextPath";
	
	
	public static String TENANT_SERVER = "saas-tenant-server";

    /**
     * 初始化数据源时json的参数，
     * method 的可选值为 {INIT_DS_PARAM_METHOD_INIT} 和 {INIT_DS_PARAM_METHOD_REMOVE}
     */
	public static String INIT_DS_PARAM_METHOD = "method";
    /**
     * 初始化数据源时json的参数，
     * datasource 的值为 需要初始化的租户数据源信息/移除的数据源名称
     */
	public static String INIT_DS_PARAM_TENANT = "datasource";
    /**
     * 初始化数据源时，需要执行的方法
     * init 表示初始化数据源
     * remove 表示删除数据源
     */
	public static String INIT_DS_PARAM_METHOD_INIT = "init";
    /**
     * 初始化数据源时，需要执行的请求映射
     */
	public static String INIT_DS_REQUESTMAPPING_INIT = "/ds/initConnect";
    /**
     * 初始化数据源时，需要执行的方法
     * init 表示初始化数据源
     * remove 表示删除数据源
     */
	public static String INIT_DS_PARAM_METHOD_REMOVE = "remove";
    /**
     * 删除数据源时，需要执行的请求映射
     */
	public static String INIT_DS_REQUESTMAPPING_REMOVE = "/ds/remove";
    
    /**
     * 应用上下文路径:server.servlet.context-path
     * 如有上下文路径配置应同时配置到nacos服务的Metadata中
     * 使用该名称作为KEY
     */
	public static String SERVER_METADATA_CONTEXT_PATH_NAME = "context.path";
    
    /**
     * 获取应用配置信息URI
     */
	public static String APPLICATION_ENVIRONMENT_URI = "/service/environment";

}
