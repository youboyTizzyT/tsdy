package team.tsdy.code.dao.user;

import com.alibaba.fastjson.JSONArray;
import team.tsdy.code.dao.AbsDao;
import team.tsdy.code.utils.MysqlConnPool;
import team.tsdy.code.utils.MysqlHelper;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 每一张表对应一个DAOImpl
 * @author weicong
 * @date 2018/3/21 0021
 */
public class UserDAOImpl {
    /**
     * 记得回收连接
     */
    private Connection connection = MysqlConnPool.getInstance().getConnection();
    public int addUser(int uid,int password){
        int changeNum= MysqlHelper.executeUpdate(connection,"INSERT INTO user(uid,password) VALUES ("+uid+","+password+")");
        return changeNum;
    }
    public JSONArray getUserPassword(int uid){
        JSONArray jsonArray=MysqlHelper.executeQuery(connection,"SELECT password from user where uid="+uid);
        return jsonArray;
    }
    public int removeUser(int uid){
        int changeNum=MysqlHelper.executeUpdate(connection,"DELETE FROM user WHERE  uid="+uid);
        return changeNum;
    }
    public int changeUserPassword(int uid,int password){
        int changeNum=MysqlHelper.executeUpdate(connection,"UPDATE user SET  password="+password+" WHERE uid="+uid);
        return changeNum;
    }
    public Connection getConnection()  {
        return connection;
    }

}
