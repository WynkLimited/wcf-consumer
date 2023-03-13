package in.wynk.consumerservice;

import in.wynk.consumerservice.config.KafkaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@ActiveProfiles(value = "stage")
public class KafkaConfigTest {

    //@Autowired
    private KafkaConfig kafkaConfig;

    //@Test
    public void testConfig() {
        Map<String, Object> config  = kafkaConfig.createConsumerConfigs();
        assertNotNull(config);
        assertFalse(config.isEmpty());
    }

    //@Test
    public void testCreateKafkaListenerContainerFactory() {
        assertNotNull(kafkaConfig.createKafkaListenerContainerFactory());
    }
}
