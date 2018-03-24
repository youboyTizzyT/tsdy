package team.tsdy.code.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import java.util.Properties;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class RedisPoolUtil {
    private static final Logger logger = LogManager.getLogger(RedisPoolUtil.class);

    private static JedisPool jedisPool = null;
    /**
     *  把redis连接对象放到本地线程中
     */
    private static ThreadLocal<Jedis> local = new ThreadLocal<>();
    public static void remove(){
        local.remove();
    }
    /**
     * 不允许通过new创建该类的实例
     */
    private RedisPoolUtil() {
    }

    /**
     * 初始化Redis连接池
     */
    public static void initialPool() {
        try {
            Properties props = new Properties();
            //加载连接池配置文件
            String redisConfigFile = "redis.properties";
            props.load(RedisPoolUtil.class.getClassLoader().getResourceAsStream(redisConfigFile));
            // 创建jedis池配置实例
            JedisPoolConfig config = new JedisPoolConfig();
            // 设置池配置项值
            config.setMaxTotal(Integer.valueOf(props.getProperty("jedis.pool.maxActive")));
            config.setMaxIdle(Integer.valueOf(props.getProperty("jedis.pool.maxIdle")));
            config.setMaxWaitMillis(Long.valueOf(props.getProperty("jedis.pool.maxWait")));
            config.setTestOnBorrow(Boolean.valueOf(props.getProperty("jedis.pool.testOnBorrow")));
            config.setTestOnReturn(Boolean.valueOf(props.getProperty("jedis.pool.testOnReturn")));
            // 根据配置实例化jedis池
            jedisPool = new JedisPool(config, props.getProperty("redis.ip"),
                    Integer.valueOf(props.getProperty("redis.port")),
                    Integer.valueOf(props.getProperty("redis.timeout")));
            int activeNum=jedisPool.getNumActive();
            logger.info("redis线程池被成功初始化 activeNum="+activeNum);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获得连接
     * @return Jedis
     */
    public static Jedis getConn() {
        // Redis对象
        Jedis jedis =local.get();
        if(jedis==null){
            if (jedisPool == null) {
                initialPool();
            }
            jedis = jedisPool.getResource();
            local.set(jedis);
        }
        return jedis;
    }

    /**
     * 新版本用close归还连接
     */
    public static void closeConn(){
        //从本地线程中获取
        Jedis jedis =local.get();
        if(jedis!=null){
            jedis.close();
        }
        local.set(null);
    }

    //关闭池
    public static void closePool(){
        if(jedisPool!=null){
            jedisPool.close();
        }
    }
}