package com.dwi.basic.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dwi.basic.base.R;
import com.dwi.basic.base.request.PageParams;
import com.dwi.basic.base.request.PageUtil;
import com.dwi.basic.base.request.TableDataInfo;
import com.dwi.basic.boot.utils.ServletUtils;
import com.dwi.basic.context.ContextUtil;
import com.dwi.basic.exception.BizException;
import com.dwi.basic.exception.code.BaseExceptionCode;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;

/**
 * 不基于MP无泛型的基础接口
 *
 * @author dwi
 * @date 2020年03月07日21:56:32
 */
public interface BaseSimpleController {
	
	/**
	 * 简单导出
	 * 
	 * @param <T>
	 * @param records
	 * @param clazz
	 * @param fileName
	 */
	default <T> void exportExcel(List<T> records, Class<T> clazz, String...fileName) {
		Map<String, Object> map = new HashMap<>(7);
		map.put(NormalExcelConstants.DATA_LIST, records);
        map.put(NormalExcelConstants.CLASS, clazz);    
        map.put(NormalExcelConstants.PARAMS, getExportParams());
        map.put(NormalExcelConstants.FILE_NAME, fileName.length>0 ? fileName[0]:"临时文件");
		PoiBaseView.render(map, ServletUtils.getRequest(), ServletUtils.getResponse(), NormalExcelConstants.EASYPOI_EXCEL_VIEW);
	}
	
	/**
     * 构建导出参数
     * 子类可以重写
     *
     * @param params 分页参数
     * @return 导出参数
     */
    default ExportParams getExportParams() {
        Object title = ServletUtils.getParam("title");
        Object type = ServletUtils.getParam("type", ExcelType.XSSF.name());
        Object sheetName = ServletUtils.getParam("sheetName", "SheetName");

        ExcelType excelType = ExcelType.XSSF.name().equals(type) ? ExcelType.XSSF : ExcelType.HSSF;
        ExportParams ep = new ExportParams(title == null ? null : String.valueOf(title), sheetName.toString(), excelType);
        enhanceExportParams(ep);
        return ep;
    }
    
    /**
     * 子类增强ExportParams
     *
     * @param ep ep
     * @author dwi
     * @date 2021/5/23 10:27 下午
     * @create [2021/5/23 10:27 下午 ] [dwi] [初始创建]
     */
    default void enhanceExportParams(ExportParams ep) {
    }
	
	/**
	 * pageHelper分页
	 */
	default void startPage() {
		PageUtil.startPage();
	}
	
	/**
	 * 构建基于MP的Page分页查询结果
	 * @param <T>
	 * @param list
	 * @return
	 */
	default <T> Page<T> buildPage(List<T> list){
		return PageUtil.buildPage(list);
	}
	
	/**
	 * Pagehelper响应请求分页数据
	 * 
	 * @param <T>
	 * @param list
	 * @return
	 */
	default <T> TableDataInfo getDataTable(List<T> list){
		return PageUtil.getDataTable(list);
	}


    /**
     * 成功返回
     *
     * @param data 返回内容
     * @param <T>  返回类型
     * @return R 成功
     */
    default <T> R<T> success(T data) {
        return R.success(data);
    }

    /**
     * 成功返回
     *
     * @return R.true
     */
    default R<Boolean> success() {
        return R.success();
    }

    /**
     * 失败返回
     *
     * @param msg 失败消息
     * @param <T> 返回类型
     * @return 失败
     */
    default <T> R<T> fail(String msg) {
        return R.fail(msg);
    }

    /**
     * 失败返回
     *
     * @param msg  失败消息
     * @param args 动态参数
     * @param <T>  返回类型
     * @return 失败
     */
    default <T> R<T> fail(String msg, Object... args) {
        return R.fail(msg, args);
    }

    /**
     * 失败返回
     *
     * @param code 失败编码
     * @param msg  失败消息
     * @param <T>  返回类型
     * @return 失败
     */
    default <T> R<T> fail(int code, String msg) {
        return R.fail(code, msg);
    }

    /**
     * 失败返回
     *
     * @param exceptionCode 失败异常码
     * @return 失败
     */
    default <T> R<T> fail(BaseExceptionCode exceptionCode) {
        return R.fail(exceptionCode);
    }

    /**
     * 失败返回
     *
     * @param exception 异常
     * @return 失败
     */
    default <T> R<T> fail(BizException exception) {
        return R.fail(exception);
    }

    /**
     * 失败返回
     *
     * @param throwable 异常
     * @return 失败
     */
    default <T> R<T> fail(Throwable throwable) {
        return R.fail(throwable);
    }

    /**
     * 参数校验失败返回
     *
     * @param msg 错误消息
     * @return 失败
     */
    default <T> R<T> validFail(String msg) {
        return R.validFail(msg);
    }

    /**
     * 参数校验失败返回
     *
     * @param msg  错误消息
     * @param args 错误参数
     * @return 失败
     */
    default <T> R<T> validFail(String msg, Object... args) {
        return R.validFail(msg, args);
    }

    /**
     * 参数校验失败返回
     *
     * @param exceptionCode 错误编码
     * @return 失败
     */
    default <T> R<T> validFail(BaseExceptionCode exceptionCode) {
        return R.validFail(exceptionCode);
    }

    /**
     * 获取当前id
     *
     * @return userId
     */
    default Long getUserId() {
        return ContextUtil.getUserId();
    }

    /**
     * 当前请求租户
     *
     * @return 租户编码
     */
    default String getTenant() {
        return ContextUtil.getTenant();
    }

    /**
     * 登录人账号
     *
     * @return 账号
     */
    default String getAccount() {
        return ContextUtil.getAccount();
    }

    /**
     * 登录人姓名
     *
     * @return 姓名
     */
    default String getName() {
        return ContextUtil.getName();
    }

}
