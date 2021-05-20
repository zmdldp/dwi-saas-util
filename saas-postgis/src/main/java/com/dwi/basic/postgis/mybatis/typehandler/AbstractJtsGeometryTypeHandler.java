package com.dwi.basic.postgis.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.locationtech.jts.geom.Geometry;
import org.postgis.jts.JtsGeometry;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dwi
 */
public abstract class AbstractJtsGeometryTypeHandler<T extends Geometry> extends BaseTypeHandler<T> {

    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, new JtsGeometry(parameter));
    }

    @SuppressWarnings("unchecked")
	public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        JtsGeometry jtsGeometry = (JtsGeometry) rs.getObject(columnName);
        if (jtsGeometry == null) {
            return null;
        }
        return (T) jtsGeometry.getGeometry();
    }

    @SuppressWarnings("unchecked")
	public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        JtsGeometry jtsGeometry = (JtsGeometry) rs.getObject(columnIndex);
        if (jtsGeometry == null) {
            return null;
        }
        return (T) jtsGeometry.getGeometry();
    }

    @SuppressWarnings("unchecked")
	public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        JtsGeometry jtsGeometry = (JtsGeometry) cs.getObject(columnIndex);
        if (jtsGeometry == null) {
            return null;
        }
        return (T) jtsGeometry.getGeometry();
    }
}