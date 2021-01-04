package com.dwi.basic.database.injector.method;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import lombok.NoArgsConstructor;

import java.util.function.Predicate;

/**
 * 修改所有的字段
 *
 * @author dwi
 * @date 2020年05月08日16:05:19
 */
@NoArgsConstructor
public class UpdateAllById extends AlwaysUpdateSomeColumnById {

    public UpdateAllById(Predicate<TableFieldInfo> predicate) {
        super(predicate);
    }

    @Override
    public String getMethod(SqlMethod sqlMethod) {
        // 自定义 mapper 方法名
        return "updateAllById";
    }
}
