package com.wynk.consumerservice.service;

import com.wynk.consumerservice.constants.Constants;
import com.wynk.consumerservice.dto.SubscriptionEvent;
import com.wynk.consumerservice.proxy.HttpProxy;
import com.wynk.consumerservice.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
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


    public static final String TP_CALLBACK_ENDPOINT  = "/music/wcf/cb/subscribe/tp/callback";
//    public static final String SAPI_WCF_DOMAIN  = "http://sapi.wynkinternal.in"; // need to given by sumit before prod
    public static final String SAPI_WCF_DOMAIN  = "http://sapi-thanks.wynkinternal.in"; // need to given by sumit before prod
    public void processRecord(SubscriptionEvent subEvent, String jsonPayload) {
        if(Objects.isNull(jsonPayload) || Objects.isNull(subEvent))
        {
            return;
        }
        log.info("Going to hit sapi with payload {} ", jsonPayload);
        try
        {
        StringBuilder url = new StringBuilder(SAPI_WCF_DOMAIN);
        url.append(TP_CALLBACK_ENDPOINT);
        log.info("Final Url is {}", url);
            Map<String, String> headerMap = getHeaderMap("POST", TP_CALLBACK_ENDPOINT, AppUtils.toJson(subEvent));
            log.info("Header map is {}", headerMap);
            log.info("Going to hit to sapi...");
//            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(uri, httpEntity, String.class);
            String response = proxy.postData(url.toString(), AppUtils.toJson(subEvent), headerMap, 2000);
            log.info("Response received is : {}", response);
        } catch (Exception e) {
            log.info("Some exception occur {}, {}", e.getMessage(),e);
        }
       // logic need to add here to hit api
    }

    public Map<String,String> getHeaderMap(String httpMethod , String requestUri, String requestBody){
        Map<String,String> headerMap = new HashMap();
        String timestamp = String.valueOf(System.currentTimeMillis());
        requestBody = org.apache.commons.lang3.StringUtils.isBlank(requestBody) ? "" : requestBody;
        String signature = createSignature(httpMethod,requestUri,requestBody,timestamp, "50de5a601c133a29c8db434fa9bf2db4");
        headerMap.put(Constants.X_BSY_DATE_KEY,timestamp);
        headerMap.put(Constants.X_BSY_ATKN_KEY, "543fbd6f96644406567079c00d8f33dc".concat(":").concat(signature));
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
// Staging
//    appId:
//    secret:

// Prod
//    appId: 897944454aaa91bc373542fd9777ee8a
//    secret: ce59d0f3b87c83224b74478672c43349
