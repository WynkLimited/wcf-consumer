package com.wynk.consumerservice.config.properties;

import lombok.Data;

@Data
public class RedisHostConfig {
    private String redisHost;
    private int    redisPort;
    private String redisPassword;
}
