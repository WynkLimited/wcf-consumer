package com.wynk.consumerservice;

import com.wynk.consumerservice.dto.SubscriptionEvent;
import com.wynk.consumerservice.utils.AppUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("stage")
public class AppUtilsTest {

    private SubscriptionEvent getSubscriptionEvent() {
        SubscriptionEvent subscriptionEvent = new SubscriptionEvent();
        subscriptionEvent.setEvent("dummy");
        subscriptionEvent.setActive(true);
        subscriptionEvent.setStatus("dummy");
        subscriptionEvent.setMsisdn("dummy");
        subscriptionEvent.setUid("dummy");
        subscriptionEvent.setAutoRenewal(true);
        subscriptionEvent.setPlanId(0);
        subscriptionEvent.setValidTillDate(0l);
        return subscriptionEvent;
    }

    @Test
    public void jsonTest() {
        SubscriptionEvent event = getSubscriptionEvent();
        String json =  AppUtils.toJson(event);
        Assertions.assertNotNull(json);

        Assertions.assertNotNull(AppUtils.fromJson(json, event.getClass()));
    }
}
