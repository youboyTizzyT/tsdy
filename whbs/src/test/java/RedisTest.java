import redis.clients.jedis.Jedis;
import team.tsdy.code.utils.RedisPoolUtil;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class RedisTest {
    public static void main(String[] args) {
        RedisPoolUtil.initialPool();
        Jedis jedis=RedisPoolUtil.getConn();
        jedis.set("111","222");
        System.out.println(jedis.get("111"));
        RedisPoolUtil.closePool();

    }
}
