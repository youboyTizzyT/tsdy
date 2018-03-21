package team.tsdy.code.net;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.tsdy.code.cache.UserCache;
import team.tsdy.code.config.ErrorCode;
import team.tsdy.code.config.ServerConfig;
import team.tsdy.code.model.User;
import team.tsdy.code.model.tsdy.CeShiUser;
import team.tsdy.code.utils.ClassFinder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class ActionManager {

    private static final Logger logger = LogManager.getLogger(ActionManager.class);

    private static final Map<String, MethodHolder> cache = new HashMap<>();

    private static final Executor executor = Executors.newCachedThreadPool();

    public static void register() {    //初始化cache中所有缓存的actions包下所有类中所有方法加载到cache中，然后等待反射调用
        List<Class> actions = ClassFinder.findClasses(ServerConfig.PACKAGE_NAME, "Action");
        System.out.println(actions);
        for (Class clazz : actions) {
            try {
                Object actionInstance = clazz.newInstance();
                for (Method method : clazz.getMethods()) {
                    Action action = method.getAnnotation(Action.class);
                    if (action == null) {
                        continue;
                    }
                    MethodHolder methodHolder = new MethodHolder();
                    methodHolder.action = actionInstance;
                    methodHolder.method = method;
                    if (cache.containsKey(method.getName())) {
                        logger.error("Handler Add Failed : " + method.getName());
                        continue;
                    }
                    //k：方法名，v：反射调用的东西
                    cache.put(method.getName(), methodHolder);
                    logger.info("Register Action : " + method.getName());
                }
            } catch (InstantiationException e) {
                logger.error("", e);
            } catch (IllegalAccessException e) {
                logger.error("", e);
            }
        }
    }

    /**
     * 使用方法的类，每次客户端发送消息过来，我都会调用我这个类去缓存中找这个名字。
     * @param json
     * @param player
     */
    private static void exec(JSONObject json, User player) {
        try {
            String type = json.getString("m");
            MethodHolder methodHolder = cache.get(type);
            if (methodHolder == null) {
                logger.error("Cant Find Handler : " + type);
                return;
            }
            //反射调用方法
            methodHolder.method.invoke(methodHolder.action, json, player);
        } catch (IllegalAccessException e) {
            logger.error("", e);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 10007);
            jsonObject.put("m", json.get("m"));
            jsonObject.put("youjson", json);
            player.getChannel().writeAndFlush(jsonObject);
            return;
        } catch (InvocationTargetException e) {
            logger.error("", e);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 10007);
            jsonObject.put("m", json.get("m"));
            jsonObject.put("youjson", json);
            player.getChannel().writeAndFlush(jsonObject);
            logger.error("", e);
            return;
        }
    }
    public static void submitAction( String method, Map<String, String> pro, ChannelHandlerContext channel){
        executor.execute(()-> {
            try {
                MethodHolder methodHolder = cache.get(method);
                if (methodHolder == null) {
                    logger.error("Cant Find Handler : " + method);
                    return;
                }
                methodHolder.method.invoke(methodHolder.action,pro , channel);//反射调用方法
            } catch (IllegalAccessException e) {
                logger.error("", e);
                channel.writeAndFlush("{\"status\":30000}");
                return;
            } catch (InvocationTargetException e) {
                channel.writeAndFlush("{\"status\":30001}");
                return;
            }

        });
    }
    public static void submitAction(JSONObject json, Channel channel) {
        executor.execute(()-> {
            try {
                Integer uid = json.getInteger("uid");
                User user = UserCache.getUser(uid);
                if (user==null){
                    user = new CeShiUser();
                    user.setUid(uid);
                    UserCache.addUser(user);
                }
                user.setChannel(channel);
                exec(json, user);
            } catch (final Exception e) {
                e.printStackTrace();
            }

        });
    }

    private static class MethodHolder {
        private Object action;
        private Method method;
    }
}
