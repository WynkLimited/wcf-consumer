package in.wynk.consumerservice.config.properties;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ShardedRedisConfig {
    private ArrayList<RedisHostConfig> redisHosts;

    // genericObject pool has 8 as default, so keeping same value
    private int maxActive = 8;
    private int maxIdle   = 8;
}
