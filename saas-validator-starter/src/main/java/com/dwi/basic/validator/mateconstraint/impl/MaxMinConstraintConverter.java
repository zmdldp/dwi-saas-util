package com.dwi.basic.validator.mateconstraint.impl;


import com.dwi.basic.validator.mateconstraint.IConstraintConverter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * 长度 转换器
 *
 * @author dwi
 * @date 2019-07-25 15:15
 */
public class MaxMinConstraintConverter extends BaseConstraintConverter implements IConstraintConverter {

    @Override
    protected List<String> getMethods() {
        return Arrays.asList("value", "message");
    }

    @Override
    protected String getType(Class<? extends Annotation> type) {
        return type.getSimpleName();
    }

    @Override
    protected List<Class<? extends Annotation>> getSupport() {
        return Arrays.asList(Max.class, Min.class, DecimalMax.class, DecimalMin.class);
    }

}
