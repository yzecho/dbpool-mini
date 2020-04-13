package io.yzecho.dbpool.config;

/**
 * @author yzecho
 * @desc 数据库连接池中存放的就是数据库操作管道，不仅仅是存放，而且应该是管理这些管道；
 * @date 13/04/2020 09:26
 */
public class DbConfig {
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public static final String JDBC_USERNAME = "root";
    public static final String JDBC_PASSWORD = "root";

    /**
     * 数据库连接池初始化大小
     */
    public static final int INIT_COUNT = 10;
    /**
     * 连接池不足的时候增长的步进值
     */
    public static final int STEP = 2;
    /**
     * 连接池的最大数量
     */
    public static final int MAX_COUNT = 50;
}
