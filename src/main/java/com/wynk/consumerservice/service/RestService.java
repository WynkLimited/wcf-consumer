package com.wynk.consumerservice.service;

import com.wynk.consumerservice.constants.Constants;
import com.wynk.consumerservice.dto.SubscriptionEvent;
import com.wynk.consumerservice.proxy.HttpProxy;
import com.wynk.consumerservice.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author : Kunal Sharma
 * @since : 08/02/23, Wednesday
 **/


@Service
@Slf4j
public class RestService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpProxy proxy;

    @Value("${sapi.wcf.domain}")
    private String sapiWcfDomain;

    @Value("${secretKey}")
    private String secretKey;

    @Value("${appKey}")
    private String appKey;


    public static final String TP_CALLBACK_ENDPOINT  = "/music/wcf/cb/subscribe/tp/callback";
    public void processRecord(SubscriptionEvent subEvent, String jsonPayload) {
        if (Objects.isNull(jsonPayload) || Objects.isNull(subEvent)) {
            return;
        }
        log.info("Going to hit sapi with payload {} ", jsonPayload);
        try {
            StringBuilder url = new StringBuilder(sapiWcfDomain);
            url.append(TP_CALLBACK_ENDPOINT);
            log.info("Final Url is {}", url);
            Map<String, String> headerMap = getHeaderMap("POST", TP_CALLBACK_ENDPOINT, AppUtils.toJson(subEvent));
            log.info("Header map is {}", headerMap);
            log.info("Going to hit to sapi...");
//            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(uri, httpEntity, String.class);
            String response = proxy.postData(url.toString(), AppUtils.toJson(subEvent), headerMap, 2000);
            log.info("Response received is : {}", response);
        } catch (Exception e) {
            log.info("Some exception occur {}, {}", e.getMessage(), e);
        }
        // logic need to add here to hit api
    }

    public Map<String,String> getHeaderMap(String httpMethod , String requestUri, String requestBody){
        Map<String,String> headerMap = new HashMap();
        String timestamp = String.valueOf(System.currentTimeMillis());
        requestBody = StringUtils.isBlank(requestBody) ? "" : requestBody;
        String signature = createSignature(httpMethod,requestUri,requestBody,timestamp, secretKey);
        headerMap.put(Constants.X_BSY_DATE_KEY,timestamp);
        headerMap.put(Constants.X_BSY_ATKN_KEY, appKey.concat(":").concat(signature));
        headerMap.put("Content-Type", "application/json");
        return headerMap;
    }

    public String createSignature(String httpMethod , String requestUri, String requestBody, String timestamp, String secretKey){
        String digestString = new StringBuilder(httpMethod).append(requestUri).append(requestBody).append(timestamp).toString();
        String computedSignature ="";
        try {
            computedSignature = calculateRFC2104HMAC(digestString, secretKey);
        }catch (Throwable th){
            log.error("exception occuured in calculating signature for the requestBody {}",requestBody);
        }
        return computedSignature;
    }

    public static String calculateRFC2104HMAC(String data, String secretKey) throws java.security.SignatureException {
        String result;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
            mac.init(key);
            byte[] authentication = mac.doFinal(data.getBytes());
            result = new String(org.apache.commons.codec.binary.Base64.encodeBase64(authentication));

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
}

