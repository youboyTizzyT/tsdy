package team.tsdy.code.action;

import com.alibaba.fastjson.JSONObject;
import team.tsdy.code.model.User;
import team.tsdy.code.net.Action;
import team.tsdy.code.service.LoginService;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class LogAction {
    /**
     * 登录
     *
     */
    @Action
    public void loginIn(JSONObject jsonObject, User user) {
        LoginService.login(jsonObject.getInteger("uid"),jsonObject.getInteger("password"),user);
    }
    @Action
    public void signUp(JSONObject jsonObject, User user) {
        LoginService.sign(jsonObject.getInteger("uid"),jsonObject.getInteger("password"),user);
    }
    @Action
    public void changePassword(JSONObject jsonObject, User user) {
        LoginService.changePassword(jsonObject.getInteger("uid"),jsonObject.getInteger("password"),user);
    }
    @Action
    public void delUser(JSONObject jsonObject, User user) {
        LoginService.removeUser(jsonObject.getInteger("uid"),user);
    }
}
