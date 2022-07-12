package io.github.kloping.spt;

import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import io.github.kloping.MySpringTool.interfaces.component.HttpClientManager;
import redis.clients.jedis.Jedis;

import java.util.List;

import static io.github.kloping.spt.SptRedis.l2s;
import static io.github.kloping.spt.SptRedis.ls2o;

/**
 * @author github.kloping
 */
public class RedisOperateList<T extends List> extends RedisOperate<T> {
    private Class valueType;
    private Class c0;

    public RedisOperateList(ContextManager contextManager, Class valueType, Class c0) {
        super(contextManager);
        this.valueType = valueType;
        this.c0 = c0;
    }

    @Override
    T getValue0(String key, Jedis jedis) {
        List<String> list = jedis.lrange(key, 0, -1);
        return (T) ls2o(valueType, list, c0);
    }

    @Override
    RedisOperate setValue0(String key, T o, Jedis jedis) {
        List list = (List) o;
        List l0 = getValue(key);
        for (Object o1 : l0) {list.remove(o1);}
        jedis.rpush(key, l2s(valueType, list));
        return this;
    }
}
