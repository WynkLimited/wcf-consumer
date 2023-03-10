package in.wynk.consumerservice.config.properties;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ShardedRedisConfig {
    private ArrayList<RedisHostConfig> redisHosts;
    private int maxActive;
    private int maxIdle;
}
