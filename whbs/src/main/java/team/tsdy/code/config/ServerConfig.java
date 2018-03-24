package team.tsdy.code.config;

/**
 * @author weicong
 * @date 2018/3/21 0021
 */
public class ServerConfig {
    public static final int PORT = Integer.parseInt(System.getProperty("port", "8999"));
    public static final String PACKAGE_NAME = "team.tsdy.code.action";

    public static final int TCP_READ_TIMEOUT = 30;
    public static final int TCP_WRITE_TIMEOUT = 0;

}
