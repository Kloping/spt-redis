package io.github.kloping.spt;

import com.alibaba.fastjson.JSON;
import io.github.kloping.MySpringTool.Setting;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Controller;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.MySpringTool.interfaces.Extension;
import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import io.github.kloping.MySpringTool.interfaces.component.up0.ClassAttributeManager;
import io.github.kloping.clasz.ClassUtils;
import io.github.kloping.object.ObjectUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author github.kloping
 */
public class SptRedis implements Extension.ExtensionRunnable, ClassAttributeManager {
    public static final String NAME = "spt-redis";
    private ContextManager contextManager;
    private Set<Field> fields = new HashSet<>();
    private Setting setting;

    @Override
    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    @Override
    public void manager(AccessibleObject accessibleObject, ContextManager contextManager) throws InvocationTargetException, IllegalAccessException {
        if (accessibleObject instanceof Field) {
            Field field = (Field) accessibleObject;
            if (!field.isAnnotationPresent(AutoStand.class)) return;
            if (ObjectUtils.isSuperOrInterface(field.getType(), RedisOperate.class)) {
                field.setAccessible(true);
                fields.add(field);
            }
        }
    }

    @Override
    public void manager(Class clsz, ContextManager contextManager) throws IllegalAccessException, InvocationTargetException {
        for (Field declaredField : clsz.getDeclaredFields()) {
            this.manager(declaredField, contextManager);
        }
    }

    @Override
    public void run() throws Throwable {
        setting.getPRE_SCAN_RUNNABLE().add(() -> {
            setting.getClassManager().registeredAnnotation(Controller.class, this);
            setting.getClassManager().registeredAnnotation(Entity.class, this);
        });
        setting.getPOST_SCAN_RUNNABLE().add(() -> {
            contextManager = setting.getContextManager();
            String host = contextManager.getContextEntity(String.class, "spt.redis.host");
            int port = 6379;
            Integer i = contextManager.getContextEntity(Integer.class, "spt.redis.port");
            port = i == null ? port : i.intValue();
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            JedisPool jedisPool = new JedisPool(poolConfig, host, port);
            contextManager.append(jedisPool);
            autoStands();
        });
    }

    private void autoStands() {
        for (Field field : fields) {
            try {
                autoStand(field);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private void autoStand(Field field) throws Throwable {
        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        Type type = pt.getActualTypeArguments()[0];
        RedisOperate redisOperate = null;
        if (type instanceof Class) {
            Class valueType = (Class) type;
            if (ObjectUtils.isBaseOrPack(valueType) || valueType == String.class) {
                redisOperate = new RedisOperate0(contextManager, valueType);
            } else {
                redisOperate = new RedisOperate1(contextManager, valueType);
            }
        }
        if (type instanceof ParameterizedTypeImpl) {
            Class valueType = null;
            Class c0 = null;
            Class c1 = null;
            ParameterizedTypeImpl pt0 = (ParameterizedTypeImpl) type;
            valueType = pt0.getRawType();
            if (pt0.getActualTypeArguments().length > 0) {
                Type t0 = pt0.getActualTypeArguments()[0];
                if (t0 != null && t0 instanceof Class) {
                    c0 = (Class) t0;
                }
            }
            if (pt0.getActualTypeArguments().length > 1) {
                Type t1 = pt0.getActualTypeArguments()[1];
                if (t1 != null && t1 instanceof Class) {
                    c1 = (Class) t1;
                }
            }
            Class finalC0 = c0;
            Class finalC1 = c1;
            Class finalValueType = valueType;
            if (ObjectUtils.isSuperOrInterface(valueType, List.class)) {
                redisOperate = new RedisOperateList(contextManager, valueType, finalC0);
            } else if (ObjectUtils.isSuperOrInterface(valueType, Set.class)) {
                redisOperate = new RedisOperateSet(contextManager, valueType, finalC0);
            } else if (ObjectUtils.isSuperOrInterface(valueType, Map.class)) {
                redisOperate = new RedisOperateMap(contextManager, valueType, finalC0, finalC1);
            }
        }
        if (redisOperate == null) return;
        AutoStand autoStand = field.getDeclaredAnnotation(AutoStand.class);
        String id = autoStand.id();
        Integer dbIndex = 0;
        try {
            dbIndex = Integer.valueOf(id.trim());
        } catch (NumberFormatException e) {

        }
        redisOperate.setDbIndex(dbIndex);
        if (ClassUtils.isStatic(field)) {
            field.set(null, redisOperate);
        } else {
            field.set(contextManager.getContextEntity(field.getDeclaringClass()), redisOperate);
        }
    }

    public static String[] l2s(Class cla, Object o) {
        Collection<String> c0 = create(cla);
        Collection c1 = (Collection) o;
        for (Object o1 : c1) {
            c0.add(o2s(o1));
        }
        return c0.toArray(new String[0]);
    }

    private static Collection create(Class cla) {
        Collection<String> c0 = null;
        if (cla == List.class) {
            c0 = new LinkedList();
        } else if (cla == Set.class) {
            c0 = new HashSet();
        } else {
            try {
                c0 = (Collection) cla.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return c0;
    }

    public static Collection ls2o(Class clz, Collection<String> c, Class t0) {
        Collection collection = create(clz);
        for (String s : c) {
            Object o = s2o(s, t0);
            collection.add(o);
        }
        return collection;
    }

    public static String o2s(Object o) {
        Class valueType = o.getClass();
        if (ObjectUtils.isBaseOrPack(valueType) || valueType == String.class) {
            return o.toString();
        } else {
            return JSON.toJSONString(o);
        }
    }

    public static <T> T s2o(String s, Class<T> valueType) {
        if (ObjectUtils.isBaseOrPack(valueType) || valueType == String.class) {
            return ObjectUtils.asPossible(valueType, s);
        } else {
            return JSON.parseObject(s).toJavaObject(valueType);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
