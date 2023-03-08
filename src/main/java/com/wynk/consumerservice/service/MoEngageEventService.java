package com.wynk.consumerservice.service;

import com.github.annotation.analytic.core.annotations.AnalyseTransaction;
import com.google.gson.JsonObject;
import com.wynk.consumerservice.constants.MusicConstants;
import com.wynk.consumerservice.dto.MoEngageEventRequest;
import com.wynk.consumerservice.exception.BaseLoggingMarkers;
import com.wynk.consumerservice.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.wynk.consumerservice.constants.MusicConstants.CONTENT_TYPE;
import static com.wynk.consumerservice.constants.MusicConstants.MOE_APP_KEY;

@Service
@Slf4j
public class MoEngageEventService {

    private final Logger logstashLogger = LoggerFactory.getLogger("moEngageLogs");

    @Autowired
    private RestTemplate restTemplate;

    @Value("${moengage.event.url}")
    private String moengageEventUrl;

    @Value("${moengage.event.authorization}")
    private String moengageEventAuth;

    @AnalyseTransaction(name = "moengageEvent")
    public void sendEvent(MoEngageEventRequest moEngageEventRequest) {
        JsonObject json = AppUtils.gson.toJsonTree(moEngageEventRequest).getAsJsonObject();
        String res = "";
        try {
            HttpEntity<?> httpEntity = new HttpEntity<>(json.toString(), getMoengageHeaders());
            ResponseEntity<JsonObject> response = restTemplate.exchange(moengageEventUrl, HttpMethod.POST, httpEntity, JsonObject.class);

            JsonObject obj = response.getBody();
            String requestStatus = obj.get("status").toString();
            String responseMsg = obj.get("message").toString();
            if(MusicConstants.SUCCESS.equals(requestStatus)) {
                log.info(BaseLoggingMarkers.MO_ENGAGE_PUSH_SUCCESS, "Moengage event push success, {}", moEngageEventRequest);
            } else {
                log.error(
                        BaseLoggingMarkers.MO_ENGAGE_PUSH_ERROR,
                        "Event push to MoEngage is unsuccessful for msg: {}. The error message is {}",
                        moEngageEventRequest,
                        responseMsg);
            }

            moEngageBatchEventLogger(AppUtils.writeValueAsString(moEngageEventRequest), response.getBody().toString(), requestStatus, false);

        } catch (Exception e) {
            moEngageBatchEventLogger(AppUtils.writeValueAsString(moEngageEventRequest), e.getMessage(), "Error", true);
        }
    }

    private HttpHeaders getMoengageHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", CONTENT_TYPE);
        headers.set("Authorization", moengageEventAuth);
        headers.set("MOE-APPKEY", MOE_APP_KEY);
        return headers;
    }

    private void moEngageBatchEventLogger(String payload, String response, String status, boolean isException) {
        try {
            Map<String, Object> logObject = new HashMap<>();
            logObject.put("payload", payload);
            logObject.put("logType", "MOENGAGE_BULK_EVENT_LOG");
            logObject.put("url", moengageEventUrl);
            logObject.put("response", response);
            if (isException) {
                logObject.put("status", "failed");
                logstashLogger.error(AppUtils.writeValueAsString(logObject));
            }
            else {
                logObject.put("status", "success");
                logstashLogger.info(AppUtils.writeValueAsString(logObject));
            }
        } catch (Exception e) {
            log.error(
                    "Exception while logging to logstash {}",
                    e.getMessage());
        }
    }
}
