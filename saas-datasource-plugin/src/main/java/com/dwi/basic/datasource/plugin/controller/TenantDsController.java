package com.dwi.basic.datasource.plugin.controller;

import com.dwi.basic.base.R;
import com.dwi.basic.datasource.plugin.context.DataSourceService;
import com.dwi.basic.datasource.plugin.domain.dto.DataSourcePropertyDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 数据源初始化
 *
 * @author dwi
 * @date 2020年04月05日16:34:04
 */
@Slf4j
@RestController
@RequestMapping("/ds")
@Api(value = "TenantDs", tags = "数据源")
@RequiredArgsConstructor
public class TenantDsController {

    private final DataSourceService dataSourceService;
    
	@Value("${spring.application.name:saas-authority-server}")
    private String applicationName;

    @ApiOperation(value = "初始化数据源", notes = "初始化数据源")
    @PostMapping(value = "/initConnect")
    public R<Boolean> initConnect(@RequestBody DataSourcePropertyDTO dataSourceProperty) {
        return R.success(dataSourceService.initConnect(dataSourceProperty));
    }

    @GetMapping(value = "/remove")
    @ApiOperation("删除数据源")
    public R<Set<String>> remove(@RequestParam("tenant") String tenant) {
        return R.success(dataSourceService.remove(tenant));
    }   
    
//  @ApiOperation(value = "初始化数据源", notes = "初始化数据源")
//  @GetMapping(value = "/init")
//  public R<Boolean> init(@RequestParam("tenant") String tenant) {
//      dataSourceService.initDataSource(tenant);
//      return R.success(true);
//  }
//    @GetMapping
//    @ApiOperation("获取当前所有数据源")
//    public R<Set<String>> list() {
//        return R.success(dataSourceService.all());
//    }
//
//    @PostMapping
//    @ApiOperation("基础Druid数据源")
//    public R<Set<String>> saveDruid(@Validated @RequestBody DataSourceSaveDTO dto) {
//        return R.success(dataSourceService.saveDruid(dto));
//    }

}
