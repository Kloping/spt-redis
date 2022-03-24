package io.github.kloping.spt;

import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import io.github.kloping.object.ObjectUtils;
import redis.clients.jedis.Jedis;

/**
 * @author github.kloping
 */
public class RedisOperate0<T> extends RedisOperate<T> {
    private Class valueType;

    public RedisOperate0(ContextManager contextManager, Class valueType) {
        super(contextManager);
        this.valueType = valueType;
    }

    @Override
    T getValue0(String key, Jedis jedis) {
        String str = jedis.get(key);
        T o = (T) ObjectUtils.asPossible(valueType, str);
        return o;
    }

    @Override
    RedisOperate setValue0(String key, Object o, Jedis jedis) {
        jedis.set(key, o.toString());
        return this;
    }
}
