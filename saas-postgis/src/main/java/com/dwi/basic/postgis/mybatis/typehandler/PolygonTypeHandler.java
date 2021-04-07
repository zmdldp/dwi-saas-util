package com.dwi.basic.postgis.mybatis.typehandler;

import org.apache.ibatis.type.MappedTypes;
import org.locationtech.jts.geom.Polygon;


/**
 * @author dwi
 */
@MappedTypes(Polygon.class)
public class PolygonTypeHandler extends AbstractJtsGeometryTypeHandler<Polygon> {
}
