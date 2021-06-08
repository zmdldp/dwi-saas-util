package com.dwi.basic.base.request;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dwi.basic.base.R;
import com.dwi.basic.base.entity.Entity;
import com.dwi.basic.base.entity.SuperEntity;
import com.dwi.basic.boot.utils.ServletUtils;
import com.dwi.basic.utils.AntiSqlFilterUtils;
import com.dwi.basic.utils.DateUtils;
import com.dwi.basic.utils.StrPool;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 分页工具类
 *
 * @author dwi
 * @date 2020/11/26 10:25 上午
 */
public class PageUtil {
    private PageUtil() {
    }

    /**
     * 重置时间区间参数
     *
     * @param params 分页参数
     */
    public static <T> void timeRange(PageParams<T> params) {
        if (params == null) {
            return;
        }
        Map<String, Object> extra = params.getExtra();
        if (CollUtil.isEmpty(extra)) {
            return;
        }
        for (Map.Entry<String, Object> field : extra.entrySet()) {
            String key = field.getKey();
            Object value = field.getValue();
            if (ObjectUtil.isEmpty(value)) {
                continue;
            }
            if (key.endsWith("_st")) {
                extra.put(key, DateUtils.getStartTime(value.toString()));
            }
            if (key.endsWith("_ed")) {
                extra.put(key, DateUtils.getEndTime(value.toString()));
            }
        }
    }
    
    /**
     * 兼容使用PageHelper分页插件
     * 
     * 设置请求分页数据
     */
    public static void startPage()
    {
        Integer pageNum =ServletUtils.getParamToInt("current");
        Integer pageSize = ServletUtils.getParamToInt("size");
        String sort = ServletUtils.getParam("sort"); 
        String order = ServletUtils.getParam("order");
        List<String> orders = new ArrayList<>();
        String[] sortArr = StrUtil.split(sort, StrPool.COMMA);
        String[] orderArr = StrUtil.split(order, StrPool.COMMA);
        int len = Math.min(sortArr.length, orderArr.length);
        for (int i = 0; i < len; i++) {
            String humpSort = sortArr[i];
            // 简单的 驼峰 转 下划线
            String underlineSort = StrUtil.toUnderlineCase(humpSort);

            // 除了create_time 和 update_time 都过滤sql关键字
            if (!StrUtil.equalsAny(humpSort, SuperEntity.CREATE_TIME, Entity.UPDATE_TIME)) {
                underlineSort = AntiSqlFilterUtils.getSafeValue(underlineSort);
            }
            // 排序支持antd
            String orderBy = StrUtil.equalsAny(orderArr[i], "ascending", "ascend", "asc") ? 
            		underlineSort.concat(StrPool.SPACE).concat("asc") : underlineSort.concat(StrPool.SPACE).concat("desc");
            orders.add(orderBy);
        }
        
        if (pageNum != null && pageSize !=null)
        {
            String orderBy = CollUtil.join(orders, StrPool.COMMA.concat(StrPool.SPACE));
            PageMethod.startPage(pageNum, pageSize, orderBy);
        }
    }
    
    /** Pagehelper响应请求分页数据,兼容MP分页响应对象
     * @param <T>
     * @param list
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Page<T> buildPage(List<T> list) 
    {
    	Page<T> page = new Page<>();
    	page.setRecords(list);
    	page.setTotal(new PageInfo(list).getTotal());
    	page.setCurrent(ServletUtils.getParamToInt("current"));
    	page.setSize(ServletUtils.getParamToInt("size"));
    	return page;
    }

    /**
     * Pagehelper响应请求分页数据
     * @param <T>
     * @param list
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> TableDataInfo getDataTable(List<T> list) 
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(R.SUCCESS_CODE);
        rspData.setRecords(list);
        rspData.setMsg("ok");
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }
}
