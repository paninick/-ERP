package com.ruoyi.erp.config;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.ruoyi.erp.utils.AesEncryptUtil;

/**
 * MyBatis TypeHandler: VARCHAR 列读写自动 AES 加解密
 * 用法: 在 Mapper XML 的 resultMap/insert/update 中指定 typeHandler
 * 或通过 application.yml 的 mybatis.type-handlers-package 自动发现
 */
@MappedTypes(String.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class EncryptTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, AesEncryptUtil.encrypt(parameter));
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return AesEncryptUtil.decrypt(rs.getString(columnName));
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return AesEncryptUtil.decrypt(rs.getString(columnIndex));
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return AesEncryptUtil.decrypt(cs.getString(columnIndex));
    }
}
