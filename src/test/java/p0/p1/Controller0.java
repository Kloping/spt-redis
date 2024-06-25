package p0.p1;

import io.github.kloping.spt.RedisOperate;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.Controller;

import java.util.List;
import java.util.Map;

/**
 * @author github.kloping
 */
@Controller
public class Controller0 {
    @AutoStand
    public RedisOperate<String> redisOperate0;

    @AutoStand
    public RedisOperate<E0> redisOperate1;

    @AutoStand
    public RedisOperate<List<String>> redisOperate2;

    @AutoStand
    public RedisOperate<Map<String, E0>> redisOperate3;

    @AutoStand
    public RedisOperate<Map<String, String>> redisOperate4;
}
