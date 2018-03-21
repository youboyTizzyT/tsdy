package team.tsdy.code.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public abstract class AbsDao {
    Connection conn=null;
    PreparedStatement pstmt=null;
    ResultSet rs=null;
}
