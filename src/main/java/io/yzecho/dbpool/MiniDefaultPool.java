package io.yzecho.dbpool;

import io.yzecho.dbpool.config.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/**
 * @author yzecho
 * @desc
 * @date 13/04/2020 09:37
 */
public class MiniDefaultPool implements MiniPool {

    private Vector<MiniPooledConnection> miniPooledConnections = new Vector<>();
    private static String url;
    private static String username;
    private static String password;
    private static int initCount;
    private static int step;
    private static int maxCount;

    public MiniDefaultPool() {
        init();
        try {
            Class.forName(DbConfig.JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        createConnection(initCount);
    }

    public void init() {
        url = DbConfig.JDBC_URL;
        username = DbConfig.JDBC_USERNAME;
        password = DbConfig.JDBC_PASSWORD;
        initCount = DbConfig.INIT_COUNT;
        step = DbConfig.STEP;
        maxCount = DbConfig.MAX_COUNT;
    }

    @Override
    public MiniPooledConnection getConnection() {
        if (miniPooledConnections.size() < 1) {
            throw new RuntimeException("连接池初始化失败");
        }
        MiniPooledConnection miniPooledConnection = null;
        try {
            miniPooledConnection = getRealConnectionFromPool();
            while (miniPooledConnection == null) {
                createConnection(step);
                miniPooledConnection = getRealConnectionFromPool();
                return miniPooledConnection;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return miniPooledConnection;
    }

    @Override
    public void createConnection(int count) {
        if (miniPooledConnections.size() > maxCount || miniPooledConnections.size() + count > maxCount) {
            throw new RuntimeException("连接池已满");
        }

        for (int i = 0; i < count; i++) {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                MiniPooledConnection miniPooledConnection = new MiniPooledConnection(connection, false);
                miniPooledConnections.add(miniPooledConnection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized MiniPooledConnection getRealConnectionFromPool() {
        for (MiniPooledConnection miniPooledConnection : miniPooledConnections) {
            if (!miniPooledConnection.getIsBusy()) {
                try {
                    if (miniPooledConnection.getConnection().isValid(3000)) {
                        miniPooledConnection.setBusy(true);
                        return miniPooledConnection;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Connection connection = DriverManager.getConnection(url, username, password);
                    miniPooledConnection.setConnection(connection);
                    miniPooledConnection.setBusy(true);
                    return miniPooledConnection;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
