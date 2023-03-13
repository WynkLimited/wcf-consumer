package in.wynk.consumerservice.config;

import in.wynk.consumerservice.config.properties.RedisHostConfig;
import in.wynk.consumerservice.config.properties.ShardedRedisConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Configuration
public class CachingConfig {


    private static final Logger logger = Logger.getLogger(CachingConfig.class.getCanonicalName());

    @Value(value = "${redis.hosts}")
    private String redisHostsConfig = "localhost:6379";

    @Value(value = "${redis.auth}")
    private String redisPassword;

    @Value(value = "${redis.pool.max.active}")
    private String maxActive = "8";

    @Value(value = "${redis.pool.max.idle}")
    private String maxIdle = "8";

    @Value(value = "${redis.timeout}")
    private int timeout = 1000;

    @Bean(name = "redisCacheManager")
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableKeyPrefix()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues();
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(factory).cacheDefaults(config)
                .build();
        return redisCacheManager;
    }

    //@Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableKeyPrefix()
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public ShardedRedisConfig shardedRedisConfig() {
        ShardedRedisConfig shardedRedisConfig = new ShardedRedisConfig();
        ArrayList<RedisHostConfig> redisHosts = new ArrayList<RedisHostConfig>();
        String[] hostsConfig = redisHostsConfig.split(",");
        for (String hostConfig : hostsConfig) {
            String[] hostAndPort = hostConfig.split(":");
            if (hostAndPort.length > 1) {
                RedisHostConfig redisHostConfig = new RedisHostConfig();
                redisHostConfig.setRedisHost(hostAndPort[0]);
                redisHostConfig.setRedisPort(Integer.parseInt(hostAndPort[1]));
                if (StringUtils.isNotBlank(redisPassword)) {
                    redisHostConfig.setRedisPassword(redisPassword);
                }
                redisHosts.add(redisHostConfig);
            }
        }
        shardedRedisConfig.setRedisHosts(redisHosts);
        shardedRedisConfig.setMaxActive(Integer.parseInt(maxActive));
        shardedRedisConfig.setMaxIdle(Integer.parseInt(maxIdle));
        return shardedRedisConfig;
    }

    @Bean
    public ShardedJedisPool shardedJedisPool() {
        ShardedRedisConfig redisConfig = shardedRedisConfig();
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisConfig.getMaxActive());
        poolConfig.setMaxIdle(redisConfig.getMaxIdle());

        List<JedisShardInfo> shards = new ArrayList<>();
        for (RedisHostConfig hostConfig : redisConfig.getRedisHosts()) {
            JedisShardInfo shardInfo = new JedisShardInfo(hostConfig.getRedisHost(), hostConfig.getRedisPort());
            if (StringUtils.isNotEmpty(hostConfig.getRedisPassword())) {
                shardInfo.setPassword(hostConfig.getRedisPassword());
            }
            shards.add(shardInfo);
        }

        return new ShardedJedisPool(poolConfig, shards);
    }

    /*@Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(new shardedj(shardedJedisPool()));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }*/

}
