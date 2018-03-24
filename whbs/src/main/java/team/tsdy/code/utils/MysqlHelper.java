package team.tsdy.code.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class MysqlHelper {
    /**
     * 执行update、insert、delete等语句，返回值为受影响的行数
     *
     * @param connection 与数据库的连接
     * @param sql 执行的sql
     * @return int 返回影响行数
     */
    public static int executeUpdate(Connection connection, String sql) {
        int resCount = 0;
        if (StringUtils.isBlank(sql)) {
            System.out.println("sql语句不能为空");
            return resCount;
        }
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            resCount = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        return resCount;

    }

    /**
     * 执行能够返回结果集的查询语句
     *
     * @param connection 与数据库的连接
     * @param sql 执行的sql
     * @return JSONArray 返回一个JsonArray
     */
    public static JSONArray executeQuery(Connection connection, String sql) {
        JSONArray array = new JSONArray();
        if (StringUtils.isBlank(sql)) {
            System.out.println("sql语句不为空");
            return null;
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject();
                // 遍历每一列
                for (int i = 1; i <= columnCount; i++) {
                    String columnName =metaData.getColumnLabel(i);
                    String value = rs.getString(columnName);
                    jsonObj.put(columnName, value);
                }
                array.add(jsonObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }

}
