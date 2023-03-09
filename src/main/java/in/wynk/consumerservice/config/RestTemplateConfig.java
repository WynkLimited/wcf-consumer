package in.wynk.consumerservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author : Kunal Sharma
 * @since : 08/02/23, Wednesday
 **/

@Configuration
public class RestTemplateConfig {

    @Value("${resttemplate.connect.timeout}")
    private long connectTimeout;

    @Value("${resttemplate.read.timeout}")
    private long readTimeout;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }
}
