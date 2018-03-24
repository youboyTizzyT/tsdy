package team.tsdy.code.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import team.tsdy.code.dao.user.UserDAOImpl;
import team.tsdy.code.model.User;

import java.sql.SQLException;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class LoginService {
    public static void login(int uid, int password, User user){
        UserDAOImpl dao=new UserDAOImpl();
        JSONArray jsonArray=dao.getUserPassword(uid);
        JSONObject jsonObject;
        if (jsonArray.size()==1){
            jsonObject=jsonArray.getJSONObject(0);
        }else {
            return;
        }
        if (jsonObject.get("password").equals(password)){
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("1","1");
            // 发送给客户端数据。完全以json的形式发送。
            user.send(jsonObject1);
        }
        try {
            // 对数据库操作完成后别忘了关闭连接
            dao.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void sign(int uid,int password, User user) {
        UserDAOImpl dao=new UserDAOImpl();
        dao.addUser(uid,password);
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("1","1");
        user.send(jsonObject1);

        try {
            dao.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void changePassword(int uid,int password, User user)  {
        UserDAOImpl dao=new UserDAOImpl();
        dao.changeUserPassword(uid,password);
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("1","1");
        user.send(jsonObject1);
        try {
            dao.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void removeUser(int uid, User user) {
        UserDAOImpl dao=new UserDAOImpl();
        dao.removeUser(uid);
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("1","1");
        user.send(jsonObject1);
        try {
            dao.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
