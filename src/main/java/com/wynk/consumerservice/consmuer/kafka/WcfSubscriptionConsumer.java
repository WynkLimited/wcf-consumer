package com.wynk.consumerservice.consmuer.kafka;

import com.wynk.consumerservice.dto.SubscriptionEvent;
import com.wynk.consumerservice.service.RestService;
import com.wynk.consumerservice.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author : Kunal Sharma
 * @since : 08/02/23, Wednesday
 **/

@Slf4j
@Component
public class WcfSubscriptionConsumer {

    @Autowired
    private RestService restService;

    @KafkaListener(topics = "${kafka.topic.wcf.subscriptions}",
            groupId = "${group.wcf.subscriptions}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<?, ?> record) {
            log.info("****************** START *************************");
        try {

            log.info("Received message on consumer kafka topic is = '{}'", record);
                SubscriptionEvent subscriptionEvent =
                    AppUtils.fromJson(record.value().toString(), SubscriptionEvent.class);

            restService.processRecord(subscriptionEvent,  record.value().toString());
            log.info("Event in payload is {}", subscriptionEvent) ;
        }catch(Exception e) {
            log.error("Error occur while consuming {},{}",e.getMessage(),e);
        }

        log.info("****************** END *************************");
    }

}

