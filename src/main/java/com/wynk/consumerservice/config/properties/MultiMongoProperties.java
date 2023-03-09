package com.wynk.consumerservice.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;

@Data
@ConfigurationProperties(prefix = "mongodb")
public class MultiMongoProperties {

    private int minConnectionsPerHost;
    private int maxConnectionsPerHost;
    private int threadsAllowedToBlockForConnectionMultiplier;
    private int maxWaitTimeMS;
    private int connectTimeout;
    private int serverSelectionTimeoutMs;
    private int socketTimeout;
    private int maxIdleTimeMs;
    private String readPreference;
    private String replicaSet;
    private MongoDataSource source;

    @Data
    public static class MongoDataSource {
        private String nodes;
        private String username;
        private String password;
        private String defaultDatabase;
        private String authSource;
        private Boolean primary;

        public String getConnectionUri() {
            StringBuilder builder = new StringBuilder("mongodb://");
            if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
                builder.append(username);
                builder.append(":");
                builder.append(URLEncoder.encode(password));
                builder.append("@");
            }
            builder.append(nodes);
            builder.append("/");
            builder.append(defaultDatabase);
            builder.append("?");
            if (authSource != null) {
                builder.append("authSource=")
                        .append(authSource);
            }
            return builder.toString();
        }
    }

    public String getConnectionSettings() {
        String builder = "";
        if (getConnectTimeout() > 0)
            builder = builder + "&connectTimeoutMS=" + getConnectTimeout();
        if (getSocketTimeout() > 0)
            builder = builder + "&socketTimeoutMS=" + getSocketTimeout();
        if (getMinConnectionsPerHost() > 0)
            builder = builder + "&minPoolSize=" + getMinConnectionsPerHost();
        if (getMaxConnectionsPerHost() > 0)
            builder = builder + "&maxPoolSize=" + getMaxConnectionsPerHost();
        if (getServerSelectionTimeoutMs() > 0)
            builder = builder + "&serverselectiontimeoutms=" + getServerSelectionTimeoutMs();
        if (getThreadsAllowedToBlockForConnectionMultiplier() > 0)
            builder = builder + "&waitqueuemultiple=" + getThreadsAllowedToBlockForConnectionMultiplier();
        if (getMaxWaitTimeMS() > 0)
            builder = builder + "&waitqueuetimeoutms=" + getMaxWaitTimeMS();
        if (getReadPreference() != null )
            builder = builder + "&readpreference=" + getReadPreference();
        if (getReplicaSet() != null )
            builder = builder + "&replicaSet=" + getReplicaSet();
        return builder;
    }
}
