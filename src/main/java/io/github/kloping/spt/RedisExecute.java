package io.github.kloping.spt;

import redis.clients.jedis.Jedis;

/**
 * @author github.kloping
 */
public interface RedisExecute {
    /**
     * operate jedis
     *
     * @param jedis
     */
    void execute(Jedis jedis);
}
