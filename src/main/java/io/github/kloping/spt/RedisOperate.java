package io.github.kloping.spt;

import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.LinkedList;
import java.util.List;

/**
 * @author github.kloping
 */
public abstract class RedisOperate<T> {
    private Class<T> valueClass;
    private ContextManager contextManager;
    private List list = new LinkedList();

    public RedisOperate(ContextManager contextManager) {
        this.contextManager = contextManager;
    }

    public T getValue(String key) {
        Jedis jedis = contextManager.getContextEntity(JedisPool.class).getResource();
        T t = getValue0(key, jedis);
        jedis.close();
        int i = list.indexOf(t);
        if (i >= 0) {
            t = (T) list.get(i);
        }
        return t;
    }

    public RedisOperate<T> setValue(String key, T t) {
        Jedis jedis = contextManager.getContextEntity(JedisPool.class).getResource();
        setValue0(key, t, jedis);
        jedis.close();
        if (!list.contains(t)) {
            list.add(t);
        }
        return this;
    }

    public boolean delKey(String key) {
        Jedis jedis = contextManager.getContextEntity(JedisPool.class).getResource();
        long n = jedis.del(key);
        jedis.close();
        return n > 0;
    }

    public void execute(RedisExecute execute) {
        Jedis jedis = contextManager.getContextEntity(JedisPool.class).getResource();
        execute.execute(jedis);
        jedis.close();
    }

    /**
     * get Value
     *
     * @param jedis
     * @param key
     * @return
     */
    abstract T getValue0(String key, Jedis jedis);

    /**
     * set Value
     *
     * @param key
     * @param jedis
     * @param t
     * @return
     */
    abstract RedisOperate<T> setValue0(String key, T t, Jedis jedis);
}
