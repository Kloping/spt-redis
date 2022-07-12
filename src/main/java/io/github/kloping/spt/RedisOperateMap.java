package io.github.kloping.spt;

import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import redis.clients.jedis.Jedis;

import java.util.*;

import static io.github.kloping.spt.SptRedis.l2s;
import static io.github.kloping.spt.SptRedis.ls2o;

/**
 * @author github.kloping
 */
public class RedisOperateMap<T extends Map> extends RedisOperate<T> {
    private Class valueType;
    private Class c0;
    private Class c1;

    public RedisOperateMap(ContextManager contextManager, Class valueType, Class c0, Class c1) {
        super(contextManager);
        this.valueType = valueType;
        this.c0 = c0;
        this.c1 = c1;
    }

    @Override
    T getValue0(String key, Jedis jedis) {
        Map map = null;
        if (valueType == Map.class) {
            map = new HashMap();
        } else {
            try {
                map = (Map) valueType.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, String> m0 = jedis.hgetAll(key);
        LinkedList list0 = (LinkedList) ls2o(LinkedList.class, m0.keySet(), c0);
        LinkedList list1 = (LinkedList) ls2o(LinkedList.class, m0.values(), c1);
        for (int i = 0; i < list0.size(); i++) {
            map.put(list0.get(i), list1.get(i));
        }
        return (T) map;
    }

    @Override
    RedisOperate setValue0(String key, T o, Jedis jedis) {
        Map map = o;
        List<String> list0 = Arrays.asList(l2s(LinkedList.class, map.keySet()));
        List<String> list1 = Arrays.asList(l2s(LinkedList.class, map.values()));
        Map<String, String> m0 = new HashMap<>();
        for (int i = 0; i < list0.size(); i++) {
            m0.put(list0.get(i), list1.get(i));
        }
        jedis.hset(key, m0);
        return this;
    }
}
