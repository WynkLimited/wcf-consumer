package com.wynk.consumerservice.constants;

/**
 * @author : Kunal Sharma
 * @since : 08/02/23, Wednesday
 **/
public class Constants {

    public static final String BOOTSTRAP_SERVERS_CONFIG = "kafka.producer.bootstrap.servers";

    public static final String GROUP_ID_CONFIG = "group.wcf.subscriptions";

    public static final String AUTO_OFFSET_RESET_CONFIG = "auto.offset.reset";

    public static final String KAFKA_MAX_POLL_RECORDS_CREATE_QUEUE =
            "kafka.max.poll.records.create.queue";
    public static final String KAFKA_MAX_POLL_INTERVAL_CREATE_QUEUE =
            "kafka.max.poll.interval.create.queue";

    public static final String ENABLE_AUTO_COMMIT_CONFIG = "enable.auto.commit";

    public static final String AUTO_COMMIT_INTERVAL_MS_CONFIG = "auto.commit.interval.ms";

    public static final String SESSION_TIMEOUT_MS_CONFIG = "session.timeout.ms";

    public static final String X_BSY_ATKN_KEY = "x-bsy-atkn";
    public static final String X_BSY_DATE_KEY = "x-bsy-date";

}
