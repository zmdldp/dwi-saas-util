package com.dwi.basic.postgis.mybatis.typehandler;

import org.apache.ibatis.type.MappedTypes;
import org.locationtech.jts.geom.MultiPoint;



/**
 * @author dwi
 */
@MappedTypes(MultiPoint.class)
public class MultiPointTypeHandler extends AbstractJtsGeometryTypeHandler<MultiPoint>{
}
