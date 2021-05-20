package com.dwi.basic.postgis.mybatis.typehandler;

import org.apache.ibatis.type.MappedTypes;
import org.locationtech.jts.geom.Point;

/**
 * @author dwi
 */
@MappedTypes(Point.class)
public class PointTypeHandler extends AbstractJtsGeometryTypeHandler<Point> {
}
