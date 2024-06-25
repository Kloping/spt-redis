package io.github.kloping.spt;

import io.github.kloping.spt.interfaces.component.ContextManager;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

import static io.github.kloping.spt.SptRedis.l2s;
import static io.github.kloping.spt.SptRedis.ls2o;

/**
 * @author github.kloping
 */
public class RedisOperateSet<T extends Set> extends RedisOperate<T> {
    private Class valueType;
    private Class c0;

    public RedisOperateSet(ContextManager contextManager, Class valueType, Class c0) {
        super(contextManager);
        this.valueType = valueType;
        this.c0 = c0;
    }

    @Override
    T getValue0(String key, Jedis jedis) {
        Set<String> list = jedis.smembers(key);
        return (T) ls2o(valueType, list, c0);
    }

    @Override
    RedisOperate setValue0(String key, T o, Jedis jedis) {
        Set list = (Set) o;
        jedis.sadd(key, l2s(valueType, list));
        return this;
    }
}
