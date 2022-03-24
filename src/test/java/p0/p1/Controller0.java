package p0.p1;

import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Controller;
import io.github.kloping.spt.RedisOperate;

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

}
