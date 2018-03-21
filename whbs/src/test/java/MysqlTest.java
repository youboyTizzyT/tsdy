//import team.tsdy.code.utils.JdbcPoolUtil;
import team.tsdy.code.utils.MysqlConnPool;
import team.tsdy.code.utils.MysqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class MysqlTest {
    public static void main(String[] args) {
//        MysqlConnPool.initMysqlPool();
//        Connection connection = MysqlConnPool.getInstance().getConnection();
//        ResultSet rs = MysqlHelper.executeQuery(connection, "select * from user");
//        try {
//            if (rs.next()) {
//                String str = rs.getString(2);
//                System.out.println(str);
//            }
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//        }
//        //这里也可以继续使用connection这个连接，只要上面不关闭即可
//        Connection connection1 = MysqlConnPool.getInstance().getConnection();
//        int exeCount = 0;
//        try {
//            exeCount = MysqlHelper.executeUpdate(connection1, "INSERT INTO user(uid,password) VALUES  (123456,123456)");
//            connection1.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println("受影响的行数为：" + exeCount);
//
////        ResultSet rs = null;
//        PreparedStatement ps = null;
//
//        try {
//            connection = MysqlConnPool.getInstance().getConnection();
//            ps = connection.prepareStatement("select * from user");
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                String s = rs.getString(2);
//                System.out.println(s);
//            }
//            connection.close();
////            connection = MysqlConnPool.getInstance().getConnection();
////            ps = connection.prepareStatement("select * from product");
////            rs = ps.executeQuery();
////            if(rs.next()){
////                String s = rs.getString(2);
////                System.out.println(s);
////            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

}
}
