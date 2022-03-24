## Redis 操作

### [my-spring-tool](https://github.com/Kloping/my-spring-tool) 扩展插件

使用方法

```xml

<dependencies>
    <dependency>
        <groupId>io.github.Kloping</groupId>
        <artifactId>spt-redis</artifactId>
        <version>0.1</version>
    </dependency>
</dependencies>

```

```java


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

```