package io.yzecho.dbpool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author yzecho
 * @desc 封装connection 提供基本sql查询功能
 * @date 13/04/2020 09:32
 */
public class MiniPooledConnection {
    private Connection connection;
    private boolean isBusy = false;

    public MiniPooledConnection(Connection connection, boolean isBusy) {
        this.connection = connection;
        this.isBusy = isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean getIsBusy() {
        return isBusy;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        this.isBusy = false;
    }

    public ResultSet query(String sql) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}
