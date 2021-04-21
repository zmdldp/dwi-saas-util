package com.dwi.basic.validator.extract;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.dwi.basic.utils.StrPool;
import com.dwi.basic.validator.mateconstraint.IConstraintConverter;
import com.dwi.basic.validator.mateconstraint.impl.MaxMinConstraintConverter;
import com.dwi.basic.validator.mateconstraint.impl.NotNullConstraintConverter;
import com.dwi.basic.validator.mateconstraint.impl.OtherConstraintConverter;
import com.dwi.basic.validator.mateconstraint.impl.RangeConstraintConverter;
import com.dwi.basic.validator.mateconstraint.impl.RegExConstraintConverter;
import com.dwi.basic.validator.model.ConstraintInfo;
import com.dwi.basic.validator.model.FieldValidatorDesc;
import com.dwi.basic.validator.model.ValidConstraint;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.hibernate.validator.internal.metadata.BeanMetaDataManager;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;

import javax.validation.Validator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dwi.basic.utils.StrPool.BOOLEAN_TYPE_NAME;
import static com.dwi.basic.utils.StrPool.COLLECTION_TYPE_NAME;
import static com.dwi.basic.utils.StrPool.DATE_TYPE_NAME;
import static com.dwi.basic.utils.StrPool.DOUBLE_TYPE_NAME;
import static com.dwi.basic.utils.StrPool.FLOAT_TYPE_NAME;
import static com.dwi.basic.utils.StrPool.INTEGER_TYPE_NAME;
import static com.dwi.basic.utils.StrPool.LIST_TYPE_NAME;
import static com.dwi.basic.utils.StrPool.LOCAL_DATE_TIME_TYPE_NAME;
import static com.dwi.basic.utils.StrPool.LOCAL_DATE_TYPE_NAME;
import static com.dwi.basic.utils.StrPool.LOCAL_TIME_TYPE_NAME;
import static com.dwi.basic.utils.StrPool.LONG_TYPE_NAME;
import static com.dwi.basic.utils.StrPool.SET_TYPE_NAME;
import static com.dwi.basic.utils.StrPool.SHORT_TYPE_NAME;

/**
 * 缺省的约束提取器
 *
 * @author dwi
 * @date 2020-07-14 12:12
 */
@Slf4j
public class DefaultConstraintExtractImpl implements IConstraintExtract {

    private final Map<String, Map<String, FieldValidatorDesc>> CACHE = new HashMap<>();

    private final Validator validator;
    private BeanMetaDataManager beanMetaDataManager;
    private List<IConstraintConverter> constraintConverters;

    public DefaultConstraintExtractImpl(final Validator validator) {
        this.validator = validator;
        init();
    }

    public final void init() {
        try {
            Field beanMetaDataManagerField = ValidatorImpl.class.getDeclaredField("beanMetaDataManager");
            beanMetaDataManagerField.setAccessible(true);
            beanMetaDataManager = (BeanMetaDataManager) beanMetaDataManagerField.get(validator);
            constraintConverters = new ArrayList<>(10);
            constraintConverters.add(new MaxMinConstraintConverter());
            constraintConverters.add(new NotNullConstraintConverter());
            constraintConverters.add(new RangeConstraintConverter());
            constraintConverters.add(new RegExConstraintConverter());
            constraintConverters.add(new OtherConstraintConverter());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("初始化验证器失败", e);
        }
    }

    @Override
    public Collection<FieldValidatorDesc> extract(List<ValidConstraint> constraints) throws Exception {
        if (constraints == null || constraints.isEmpty()) {
            return Collections.emptyList();
        }
        Map<String, FieldValidatorDesc> fieldValidatorDesc = new HashMap((int) (constraints.size() / 0.75 + 1));
        for (ValidConstraint constraint : constraints) {
            doExtract(constraint, fieldValidatorDesc);
        }

        return fieldValidatorDesc.values();
    }


    private void doExtract(ValidConstraint constraint, Map<String, FieldValidatorDesc> fieldValidatorDesc) throws Exception {
        Class<?> targetClazz = constraint.getTarget();
        Class<?>[] groups = constraint.getGroups();

        String key = targetClazz.getName() + StrPool.COLON +
                Arrays.stream(groups).map(Class::getName).collect(Collectors.joining(StrPool.COLON));
        if (CACHE.containsKey(key)) {
            fieldValidatorDesc.putAll(CACHE.get(key));
            return;
        }

        //测试一下这个方法
        //validator.getConstraintsForClass(targetClazz).getConstrainedProperties()

        BeanMetaData<?> res = beanMetaDataManager.getBeanMetaData(targetClazz);
        Set<MetaConstraint<?>> r = res.getMetaConstraints();
        for (MetaConstraint<?> metaConstraint : r) {
            builderFieldValidatorDesc(metaConstraint, groups, fieldValidatorDesc);
        }

        CACHE.put(key, fieldValidatorDesc);
    }


    private void builderFieldValidatorDesc(MetaConstraint<?> metaConstraint, Class<?>[] groups,
                                           Map<String, FieldValidatorDesc> fieldValidatorDesc) throws Exception {
        //字段上的组
        Set<Class<?>> groupsMeta = metaConstraint.getGroupList();
        boolean isContainsGroup = false;

        //需要验证的组
        for (Class<?> group : groups) {
            if (groupsMeta.contains(group)) {
                isContainsGroup = true;
                break;
            }
            for (Class<?> g : groupsMeta) {
                if (g.isAssignableFrom(group)) {
                    isContainsGroup = true;
                    break;
                }
            }
        }
        if (!isContainsGroup) {
            return;
        }

        ConstraintLocation con = metaConstraint.getLocation();
        String domainName = con.getDeclaringClass().getSimpleName();
        String fieldName = con.getConstrainable().getName();
        String key = domainName + fieldName;

        FieldValidatorDesc desc = fieldValidatorDesc.get(key);
        if (desc == null) {
            desc = new FieldValidatorDesc();
            desc.setField(fieldName);
            desc.setFieldType(getType(con.getConstrainable().getType().getTypeName()));
            desc.setConstraints(new ArrayList<>());
            fieldValidatorDesc.put(key, desc);
        }
        ConstraintInfo constraint = builderConstraint(metaConstraint.getDescriptor().getAnnotation());
        desc.getConstraints().add(constraint);

        if ("Pattern".equals(metaConstraint.getDescriptor().getAnnotationType().getSimpleName())) {
            ConstraintInfo notNull = new ConstraintInfo();
            notNull.setType("NotNull");
            Map<String, Object> attrs = new HashMap<>();
            attrs.put("message", "不能为空");
            notNull.setAttrs(attrs);
            desc.getConstraints().add(notNull);
        }
    }


    private String getType(String typeName) {
        if (StrUtil.startWithAny(typeName, SET_TYPE_NAME, LIST_TYPE_NAME, COLLECTION_TYPE_NAME)) {
            return "Array";
        } else if (StrUtil.equalsAny(typeName, LONG_TYPE_NAME, INTEGER_TYPE_NAME, SHORT_TYPE_NAME)) {
            return "Integer";
        } else if (StrUtil.equalsAny(typeName, DOUBLE_TYPE_NAME, FLOAT_TYPE_NAME)) {
            return "Float";
        } else if (StrUtil.equalsAny(typeName, LOCAL_DATE_TIME_TYPE_NAME, DATE_TYPE_NAME)) {
            return "DateTime";
        } else if (StrUtil.equalsAny(typeName, LOCAL_DATE_TYPE_NAME)) {
            return "Date";
        } else if (StrUtil.equalsAny(typeName, LOCAL_TIME_TYPE_NAME)) {
            return "Time";
        } else if (StrUtil.equalsAny(typeName, BOOLEAN_TYPE_NAME)) {
            return "Boolean";
        }
        return StrUtil.subAfter(typeName, CharUtil.DOT, true);
    }

    private ConstraintInfo builderConstraint(Annotation annotation) throws Exception {
        for (IConstraintConverter constraintConverter : constraintConverters) {
            if (constraintConverter.support(annotation.annotationType())) {
                return constraintConverter.converter(annotation);
            }
        }
        return null;
    }
}
