package team.tsdy.code.config;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class ErrorCode {
    // 报错
    public static final int ERRNO_SUCCESS_DEFAULT = 1;                          // 所有的成功状态

    public static final int USER_AUTH_FAILED = 101;                             // 验证失败
    public static final int AUTH_FAILED_SERVER_ERROR = 102;                     // 服务器出错
    public static final int ERRNO_LOGIN_AGAIN = 103;                            // 登录信息异常，请重新登录
    public static final int ERRNO_LOGIN_TIMEOUT = 104;                          // 离线时间过长，请重新登录
    public static final int AUTH_FAILED_TOKEN_ERROR = 105;                      // uctoken有误
    public static final int AUTH_FAILED_SERVER_STOP = 106;                      // 服务器维护
    public static final int AUTH_FAILED_KICK_ROLE = 107;                        // 离线时间过长
    public static final int ERRNO_USER_OTHER_LOGIN = 108;                       // 账号在别处登录

    // 玩家创建相关    10001 - 10050
    public static final int ERRNO_ROLENAME_INVALID = 10001;                     // 非法角色名
    public static final int ERRNO_CREATE_ROLE_INTERL = 10002;                   // 创建失败
    public static final int ERRNO_AUTH_ROLE_SERVER_ERROR = 10003;               // 服务器数据异常
    public static final int ERRNO_JOIN_GAME_START_ERROR = 10004;                // 创建玩家进程失败
    public static final int ERRNO_JOIN_GAME_STOP_ERROR = 10005;                 // 停止玩家进程失败
    public static final int ERRNO_REQUEST_SIGN_ERROR = 10006;                   // sign签名错误
    public static final int ERRNO_REQUEST_MSG_UNKNOW = 10007;                   // 未知的请求信息
    public static final int ERRNO_REQUIRE_TIMEOUT = 10008;                      // 请求超时，请稍后重试
    public static final int ERRNO_REQUEST_MSG_ERROR = 10009;                    // 请求信息有误
    public static final int ERRNO_REQUEST_TOO_FAST = 10010;                     // 请求服务太频繁
    public static final int ERRNO_MSG_DUPLICATES = 10011;                       // 请求信息异常
    public static final int ERRNO_ROLENAME_TOO_LONG = 10012;                    // 角色名过长
    public static final int ERRNO_ROLENAME_TOO_SHORT = 10013;                   // 角色名太短
    public static final int ERRNO_AUTH_CARDS_ERR = 10014;                       // 对不起，您输入的身份证号码有误，请重新输入!
    public static final int ERRNO_AUTH_NAME_ERR = 10015;                        // 对不起，您输入的名字有误，请重新输入!
    public static final int ERRNO_ROLESIGN_TOO_LONG = 10016;                    // 签名过长
    public static final int ERRNO_NAME_BE_USED = 10017;                        // 名字已被使用
    public static final int ERRNO_LOGIN_LIMIT_FOREVER = 10018;                  // 账号已被封禁
}
