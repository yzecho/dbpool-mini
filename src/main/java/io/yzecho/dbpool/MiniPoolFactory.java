package io.yzecho.dbpool;

/**
 * @author yzecho
 * @desc
 * @date 13/04/2020 10:17
 */
public class MiniPoolFactory {

    public static class CreatePool {
        public static MiniPool miniPool = new MiniDefaultPool();
    }

    public static MiniPool getInstance() {
        return CreatePool.miniPool;
    }
}
