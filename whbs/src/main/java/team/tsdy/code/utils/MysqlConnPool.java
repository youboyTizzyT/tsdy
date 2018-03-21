package team.tsdy.code.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class MysqlConnPool {
    public static final MysqlConnPool instance = new MysqlConnPool();
    private static ComboPooledDataSource comboPooledDataSource;
    public static void initMysqlPool(){
        Properties props = new Properties();
        //加载连接池配置文件
        String redisConfigFile = "mysql.properties";
        try {
            props.load(RedisPoolUtil.class.getClassLoader().getResourceAsStream(redisConfigFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                Class.forName(props.getProperty("mysql.driver"));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass(props.getProperty("mysql.driver"));
            comboPooledDataSource.setJdbcUrl(props.getProperty("mysql.url"));
            comboPooledDataSource.setUser(props.getProperty("mysql.username"));
            comboPooledDataSource.setPassword(props.getProperty("mysql.password"));
            //下面是设置连接池的一配置
            comboPooledDataSource.setMaxPoolSize(Integer.valueOf(props.getProperty("C3p0.pool.MaxPoolSize")));
            comboPooledDataSource.setMinPoolSize(Integer.valueOf(props.getProperty("C3p0.pool.MinPoolSize")));
            comboPooledDataSource.setInitialPoolSize(Integer.valueOf(props.getProperty("C3p0.pool.InitPoolSize")));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    public synchronized static Connection getConnection() {
        Connection connection = null;
        try {
            connection = comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;

    }

    private MysqlConnPool() {
    }

    public static MysqlConnPool getInstance(){
        return instance;
    }
}
