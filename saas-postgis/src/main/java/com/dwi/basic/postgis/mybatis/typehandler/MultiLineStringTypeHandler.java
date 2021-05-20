package com.dwi.basic.postgis.mybatis.typehandler;

import org.apache.ibatis.type.MappedTypes;
import org.locationtech.jts.geom.MultiLineString;

/**
 * @author dwi
 */
@MappedTypes(MultiLineString.class)
public class MultiLineStringTypeHandler extends AbstractJtsGeometryTypeHandler<MultiLineString>{
}
