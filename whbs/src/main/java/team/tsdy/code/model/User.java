package team.tsdy.code.model;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public abstract class User {

    private static final Logger logger = LogManager.getLogger(User.class);

    private String token;
    private Channel channel;
    private Integer uid;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void send(JSONObject jsonObject) {
        String jsonString = jsonObject.toJSONString();
        if (!channel.isActive()) {
            return;
        } else {
            this.channel.writeAndFlush(jsonString)
                    .addListener(future -> {
                        if (!future.isSuccess()) {
                            logger.error("Send Msg To [" + uid + "] " + jsonObject.toJSONString(), future.cause());
                        }
                        if (!future.isDone()) {
                            logger.error("Send Msg To [" + uid + "] " + jsonObject.toJSONString(), future.cause());
                        }
                    });
        }
    }
}
