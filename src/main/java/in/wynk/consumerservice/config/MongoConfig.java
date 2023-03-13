package in.wynk.consumerservice.config;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import in.wynk.consumerservice.config.properties.MultiMongoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableConfigurationProperties(MultiMongoProperties.class)
public class MongoConfig {

    private ConfigurableApplicationContext beanFactory;

    private MultiMongoProperties properties;

    @Autowired
    public MongoConfig(ApplicationContext applicationContext, MultiMongoProperties properties) {
        this.properties = properties;
        this.beanFactory = ((ConfigurableApplicationContext) applicationContext);
    }

    @Bean
    @Primary
    public MongoTemplate mongoTemplate() {
        String dbName = properties.getSource().getDefaultDatabase();
        return new MongoTemplate(mongoClient(), dbName);
    }

    @Bean
    @Primary
    public MongoClient mongoClient() {
        String connectionString = properties.getSource().getConnectionUri();
        connectionString = connectionString + properties.getConnectionSettings();
        return MongoClients.create(new ConnectionString(connectionString));
    }

    @Bean
    @Primary
    public String databaseName() {
        return properties.getSource().getDefaultDatabase();
    }

}