package com.wynk.consumerservice.config;

import com.wynk.consumerservice.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Kunal Sharma
 * @since : 08/02/23, Wednesday
 **/


@Slf4j
public class KafkaConfig {

    @Autowired
    private Environment environment;

    @Value("${kafka.multi.thread.concurrency}")
    private Integer multiThreadConcurrency;

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> createKafkaListenerContainerFactory() {
        long start = System.currentTimeMillis();
        log.info("Initialising kafka consumer container factory");
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        try{
            factory.setConsumerFactory(createConsumerFactory());
            factory.setConcurrency(multiThreadConcurrency);
            factory.getContainerProperties().setPollTimeout(3000);
            log.info("Initialized kafka consumer container factory in {} ms", (System.currentTimeMillis() - start));
        }catch (Exception ex){
            log.error("Error in create createKafkaListenerContainerFactory ex.getMessage : {}, ex : {} ", ex.getMessage(), ex);
        }
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> createConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(createConsumerConfigs());
    }

    @Bean
    public Map<String, Object> createConsumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootStrapServerList());
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                environment.getProperty(Constants.ENABLE_AUTO_COMMIT_CONFIG));
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,
                Integer.parseInt(environment.getProperty(Constants.AUTO_COMMIT_INTERVAL_MS_CONFIG)));
        Integer sessionTimeOut =
                Integer.parseInt(environment.getProperty(Constants.SESSION_TIMEOUT_MS_CONFIG));
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeOut);
        propsMap.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, sessionTimeOut / 10);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG,
                environment.getProperty(Constants.GROUP_ID_CONFIG));
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                environment.getProperty(Constants.AUTO_OFFSET_RESET_CONFIG));
        propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
                environment.getProperty(Constants.KAFKA_MAX_POLL_RECORDS_CREATE_QUEUE));
        propsMap.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,
                environment.getProperty(Constants.KAFKA_MAX_POLL_INTERVAL_CREATE_QUEUE));
        return propsMap;
    }

    private List<String> getBootStrapServerList() {
        String serverStr = environment.getProperty(Constants.BOOTSTRAP_SERVERS_CONFIG);
        return getServersAsList(serverStr);
    }

    private List<String> getServersAsList(String serverStr) {
        String[] serverArr = serverStr.split(",");
        List<String> list = new ArrayList<>();
        for (String str : serverArr) {
            str = str.trim();
            if (StringUtils.isNotBlank(str)) {
                list.add(str);
            }
        }
        if (list.size() == 0) {
            log.error("No Kafka servers listed");
        }
        return list;
    }

}

