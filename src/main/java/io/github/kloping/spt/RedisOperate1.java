package io.github.kloping.spt;

import com.alibaba.fastjson.JSON;
import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import redis.clients.jedis.Jedis;

/**
 * @author github.kloping
 */
public class RedisOperate1<T> extends RedisOperate<T> {
    private Class<? extends T> valueType;

    public RedisOperate1(ContextManager contextManager, Class valueType) {
        super(contextManager);
        this.valueType = valueType;
    }

    @Override
    T getValue0(String key, Jedis jedis) {
        String str = jedis.get(key);
        T o = null;
        if (valueType.isArray()) {
            o = (T) JSON.parseArray(str).toJavaObject(valueType.getComponentType());
        } else {
            o = (T) JSON.parseObject(str).toJavaObject(valueType);
        }
        return o;
    }

    @Override
    RedisOperate setValue0(String key, Object o, Jedis jedis) {
        jedis.set(key, JSON.toJSONString(o));
        return this;
    }
}
