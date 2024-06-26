package p0;

import io.github.kloping.spt.StarterObjectApplication;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.ComponentScan;
import p0.p1.Controller0;
import redis.clients.jedis.JedisPool;

import java.util.Map;


/**
 * @author github.kloping
 */
@ComponentScan(path = "p0.p1")
public class T0 implements Runnable {
    @AutoStand
    private static Controller0 source;
    @AutoStand
    private static JedisPool jedisPool;

    public static void main(String[] args) throws Throwable {
        StarterObjectApplication objectApplication = new StarterObjectApplication(T0.class);
        objectApplication.addConfFile("conf/conf.txt");
        objectApplication.run(T0.class);
        test();
    }

    private static final String KEY0 = "temp_ghost";

    private static void test() {
//        List<String> list = source.redisOperate2.getValue(KEY0);
//        list.add("a" + list.size());
//        source.redisOperate2.setValue(KEY0, list);
//        System.out.println("======");

        Map<String, String> map = source.redisOperate4.getValue(KEY0);
        map.put("a" + map.size(), "b" + map.size());
        map.put("a0", "b1");
        source.redisOperate4.setValue(KEY0, map);
    }

    @Override
    public void run() {

    }
}
