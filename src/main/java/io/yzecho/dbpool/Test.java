package io.yzecho.dbpool;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yzecho
 * @desc
 * @date 13/04/2020 10:19
 */
public class Test {
    public static MiniPool miniPool = MiniPoolFactory.getInstance();

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            MiniPooledConnection connection = miniPool.getConnection();
            ResultSet query = connection.query("select * from user");
            try {
                while (query.next()) {
                    System.out.println(query.getString("name") + ","
                            + query.getString("address") + ",使用管道：" + connection.getConnection());

                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
