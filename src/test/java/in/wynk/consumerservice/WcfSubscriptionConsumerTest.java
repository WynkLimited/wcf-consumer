package in.wynk.consumerservice;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//@SpringBootTest
@ActiveProfiles("stage")
public class WcfSubscriptionConsumerTest {
    /*@Mock
    private RestService restService;

    @Mock
    private HttpProxy proxy;

    @InjectMocks
    private WcfSubscriptionConsumer consumer;

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
    public void testConsumer() {

        //Mockito.doNothing().when(restService.processRecord(Mockito.any(), Mockito.any()));
        ConsumerRecord<String, Object> record = new ConsumerRecord<String, Object>("dummyTopic", 0, 0, "test", AppUtils.toJson(getSubscriptionEvent()));
        consumer.listen(record);
        Mockito.verify(restService, Mockito.times(1)).processRecord(Mockito.any(), Mockito.any());
    }*/
}
