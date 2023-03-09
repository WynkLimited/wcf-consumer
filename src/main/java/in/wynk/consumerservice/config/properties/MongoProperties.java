package in.wynk.consumerservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mongodb")
public class MongoProperties {
    private String hosts;
    private String mongodbHost;
    private int port;
    private String slaveHosts;
    private int slavePort;
    private String arbiterHosts;
    private int arbiterPort;
    private String dbName;
    private String dbPrefix;
    private Boolean loggingEnabled;
    private boolean readPrimary;
    private int threadsAllowedToBlock;
    private int connectionsPerHost;
}
