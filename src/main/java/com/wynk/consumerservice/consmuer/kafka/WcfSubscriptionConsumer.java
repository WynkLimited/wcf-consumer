package com.wynk.consumerservice.consmuer.kafka;

import com.wynk.consumerservice.dto.SubscriptionEvent;
import com.wynk.consumerservice.service.RestService;
import com.wynk.consumerservice.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @author : Kunal Sharma
 * @since : 08/02/23, Wednesday
 **/

@Slf4j
public class WcfSubscriptionConsumer {

    @Autowired
    private RestService restService;

    @KafkaListener(topics = "${kafka.topic.wcf.subscriptions}",
            groupId = "${group.wcf.subscriptions}",
            containerFactory = "createKafkaListenerContainerFactory")
    public void listen(ConsumerRecord<?, ?> record) {
        try {

            log.info("Received message on consumer kafka topic is = '{}'", record);
                SubscriptionEvent subscriptionEvent =
                    AppUtils.fromJson(record.value().toString(), SubscriptionEvent.class);

            restService.processRecord(record.value().toString());
            log.info("Event in payload is {}", subscriptionEvent) ;
        }catch(Exception e) {
            log.error("Error occur while consuming {},{}",e.getMessage(),e);
        }
    }

}

