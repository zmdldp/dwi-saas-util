package com.dwi.basic.datasource.plugin.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dwi.basic.base.R;
import com.dwi.basic.datasource.plugin.component.ApplicationEnvironment;
import com.dwi.basic.datasource.plugin.context.DataSourceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 应用信息
 *
 * @author dwi
 * @date 2021年03月05日16:30:24
 */
@Slf4j
@RestController
@RequestMapping("/service")
@Api(value = "Service", tags = "服务信息")
@RequiredArgsConstructor
public class ServiceController {
	
	private final ApplicationEnvironment applicationEnvironment;
	
    
    @GetMapping(value = "/environment")
    @ApiOperation("应用配置环境信息")
    public R<ApplicationEnvironment> getEnvironment(){
    	return R.success(applicationEnvironment);
    }
    

}
