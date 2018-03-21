package team.tsdy.code.cache;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.tsdy.code.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class UserCache {

    private static final Logger logger = LogManager.getLogger(UserCache.class);


    private static final Map<Integer, User> userCache = new ConcurrentHashMap<>();
    private static final Map<String, Integer> channelId2UserId = new ConcurrentHashMap<>();

    public static User getUser(Integer uid) {
        if (uid == null || uid == 0) {
            logger.error("Cant Find User : " +uid);
            return null;
        }
        return userCache.get(uid);
    }

    public static void addUser(User user) {
        userCache.put(user.getUid(), user);
    }

    public static void addUserChannel(User user) {
        channelId2UserId.put(user.getChannel().id().asLongText(), user.getUid());
    }

    public static User getUserByChannelId(String channelId) {
        if (!channelId2UserId.containsKey(channelId)) {
            return null;
        }
        Integer UserId = channelId2UserId.get(channelId);
        return getUser(UserId);
    }


    public static Map<Integer, User> getUserCache() {
        return userCache;
    }

    public static void remveUser(Integer uid){
        userCache.remove(uid);

    }

    public static String getallUser() {
        JSONObject jsonObject = new JSONObject();
        for (int a : userCache.keySet()) {

            jsonObject.put(String.valueOf(a), userCache.get(a));

        }
        return String.valueOf(jsonObject);
    }

    public static  Map<Integer, User> getLogger() {
        return userCache;
    }

}
