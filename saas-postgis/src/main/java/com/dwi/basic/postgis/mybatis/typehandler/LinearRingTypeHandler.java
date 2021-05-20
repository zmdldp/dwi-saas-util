package com.dwi.basic.postgis.mybatis.typehandler;

import org.apache.ibatis.type.MappedTypes;
import org.locationtech.jts.geom.LinearRing;

/**
 * @author dwi
 */
@MappedTypes(LinearRing.class)
public class LinearRingTypeHandler extends AbstractJtsGeometryTypeHandler<LinearRing>{
}
