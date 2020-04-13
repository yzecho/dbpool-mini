package io.yzecho.dbpool;

/**
 * @author yzecho
 * @desc
 * @date 13/04/2020 09:25
 */
public interface MiniPool {
    MiniPooledConnection getConnection();

    void createConnection(int count);
}
