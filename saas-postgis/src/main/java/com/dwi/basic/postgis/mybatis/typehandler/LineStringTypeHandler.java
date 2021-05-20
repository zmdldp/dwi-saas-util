package com.dwi.basic.postgis.mybatis.typehandler;

import org.apache.ibatis.type.MappedTypes;
import org.locationtech.jts.geom.LineString;

/**
 * @author dwi
 */
@MappedTypes(LineString.class)
public class LineStringTypeHandler extends AbstractJtsGeometryTypeHandler<LineString>{
}
