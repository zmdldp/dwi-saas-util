package com.dwi.basic.postgis.mybatis.typehandler;

import org.apache.ibatis.type.MappedTypes;
import org.locationtech.jts.geom.MultiPolygon;


/**
 * @author dwi
 */
@MappedTypes(MultiPolygon.class)
public class MultiPolygonTypeHandler extends AbstractJtsGeometryTypeHandler<MultiPolygon> {
}
