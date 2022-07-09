package p0;

import io.github.kloping.MySpringTool.StarterApplication;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.CommentScan;
import p0.p1.Controller0;
import redis.clients.jedis.JedisPool;

import java.util.Map;


/**
 * @author github.kloping
 */
@CommentScan(path = "p0.p1")
public class T0 implements Runnable {
    @AutoStand
    private static Controller0 source;
    @AutoStand
    private static JedisPool jedisPool;

    public static void main(String[] args) throws Throwable {
        StarterApplication.addConfFile("conf/conf.txt");
        StarterApplication.run(T0.class);
        test();
    }

    private static final String KEY0 = "temp_ghost";

    private static void test() {
//        Map<String, E0> e0Map = new HashMap<>();
//        e0Map.put("1", new E0(4));
//        source.redisOperate3.setValue(KEY0, e0Map);

        Map map = source.redisOperate3.getValue(KEY0);
        System.out.println("======");
    }

    @Override
    public void run() {

    }
}
