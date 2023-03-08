package com.wynk.consumerservice.service;

import com.wynk.consumerservice.annotation.TimeIt;
import com.wynk.consumerservice.dto.AllOffersResponse;
import com.wynk.consumerservice.dto.AllPlansResponse;
import com.wynk.consumerservice.dto.AllProductsResponse;
import com.wynk.consumerservice.dto.SubscriptionStatusResponse;
import com.wynk.consumerservice.utils.WcfUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.wynk.consumerservice.constants.MusicConstants.METHOD_GET;
import static com.wynk.consumerservice.constants.MusicConstants.WCF_SUBS_STATUS_ENDPOINT;

@Service
@Slf4j
public class WcfApiService {

    @Value("${music.wcf.apibaseurl}")
    private String wcfBaseUrl;

    @Value("${wcf.api.app.id}")
    private String wcfApiAppId;

    @Value("${wcf.api.secret.key}")
    private String wcfApiSecretKey;

    @Value("${wcf.all.plans.endpoint}")
    private String wcfAllPlansUrl;

    @Value("${wcf.all.products.endpoint}")
    private String wcfAllProductsUrl;

    @Value("${wcf.all.offers.endpoint}")
    private String wcfAllOffersUrl;

    @Autowired
    private RestTemplate restTemplate;

    @TimeIt
    public SubscriptionStatusResponse getSubscriptionStatus(String uid) {
        String url = String.format(WCF_SUBS_STATUS_ENDPOINT, wcfBaseUrl, uid);
        Map<String, String> headerMap = WcfUtils.getHeaderMap(METHOD_GET,
                String.format(WCF_SUBS_STATUS_ENDPOINT, "", uid), null, wcfApiAppId, wcfApiSecretKey);
        long startTime = System.currentTimeMillis();
        log.info("WCF subscriptionStatus Url : {} with header : {}", url, headerMap);
        HttpEntity<?> httpEntity = new HttpEntity<>(headerMap);
        ResponseEntity<SubscriptionStatusResponse> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, SubscriptionStatusResponse.class);
        log.debug("WCF subscriptionStatus response Data  : {}", response);
        return response.getBody();
    }

    @TimeIt
    public AllPlansResponse getAllPlansResponse() {
        String url = String.format(wcfAllPlansUrl, wcfBaseUrl);
        Map<String, String> headerMap =
                WcfUtils.getHeaderMap(
                        METHOD_GET, String.format(wcfAllPlansUrl, ""), null, wcfApiAppId, wcfApiSecretKey);
        long startTime = System.currentTimeMillis();
        log.info("All plans Request Url : {}", url);
        HttpEntity<?> httpEntity = new HttpEntity<>(headerMap);
        ResponseEntity<AllPlansResponse> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, AllPlansResponse.class);
        log.info("All plans Response : {}", response);
        return response.getBody();
    }

    @TimeIt
    public AllProductsResponse getAllProductsResponse() {
        String url = String.format(wcfAllProductsUrl, wcfBaseUrl);
        Map<String, String> headerMap =
                WcfUtils.getHeaderMap(
                        METHOD_GET, String.format(wcfAllProductsUrl, ""), null, wcfApiAppId, wcfApiSecretKey);
        log.info("All products Request Url : {}", url);
        HttpEntity<?> httpEntity = new HttpEntity<>(headerMap);
        ResponseEntity<AllProductsResponse> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, AllProductsResponse.class);
        log.info("All products Response : {}", response);
        return response.getBody();
    }

    @TimeIt
    public AllOffersResponse getAllOffersResponse() {
        String url = String.format(wcfAllOffersUrl, wcfBaseUrl);
        Map<String, String> headerMap =
                WcfUtils.getHeaderMap(
                        METHOD_GET, String.format(wcfAllOffersUrl, ""), null, wcfApiAppId, wcfApiSecretKey);
        log.info("All products Request Url : {}", url);
        HttpEntity<?> httpEntity = new HttpEntity<>(headerMap);
        ResponseEntity<AllOffersResponse> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, AllOffersResponse.class);
        log.info("All offers Response  : {}", response);
        return response.getBody();
    }
}
