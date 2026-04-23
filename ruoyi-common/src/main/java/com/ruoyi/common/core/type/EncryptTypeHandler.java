package com.ruoyi.common.core.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import com.ruoyi.common.utils.AesUtils;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 字段加密解密 TypeHandler
 * 
 * 使用方式: 在 MyBatis XML 中指定 typeHandler="com.ruoyi.common.core.type.EncryptTypeHandler"
 * 或者在 Entity 的字段上添加 @TableField(typeHandler = EncryptTypeHandler.class) （如果使用 MyBatis-Plus）
 * 
 * @author ruoyi
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(String.class)
public class EncryptTypeHandler extends BaseTypeHandler<String>
{
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException
    {
        ps.setString(i, AesUtils.encrypt(parameter));
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException
    {
        String columnValue = rs.getString(columnName);
        return AesUtils.decrypt(columnValue);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException
    {
        String columnValue = rs.getString(columnIndex);
        return AesUtils.decrypt(columnValue);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException
    {
        String columnValue = cs.getString(columnIndex);
        return AesUtils.decrypt(columnValue);
    }
}
