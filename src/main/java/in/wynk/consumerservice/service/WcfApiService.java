package in.wynk.consumerservice.service;

import in.wynk.consumerservice.annotation.TimeIt;
import in.wynk.consumerservice.dto.AllOffersResponse;
import in.wynk.consumerservice.dto.AllPlansResponse;
import in.wynk.consumerservice.dto.AllProductsResponse;
import in.wynk.consumerservice.dto.ResponseData;
import in.wynk.consumerservice.dto.SubscriptionStatusResponse;
import in.wynk.consumerservice.utils.WcfUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static in.wynk.consumerservice.constants.MusicConstants.METHOD_GET;
import static in.wynk.consumerservice.constants.MusicConstants.WCF_SUBS_STATUS_ENDPOINT;

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
        HttpHeaders headers = WcfUtils.getHeaders(METHOD_GET,
                String.format(WCF_SUBS_STATUS_ENDPOINT, "", uid), null, wcfApiAppId, wcfApiSecretKey);
        log.info("WCF subscriptionStatus Url : {} with header : {}", url, headers);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<SubscriptionStatusResponse> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, SubscriptionStatusResponse.class);
        log.debug("WCF subscriptionStatus response Data  : {}", response);
        return response.getBody();
    }

    @TimeIt
    public AllPlansResponse getAllPlansResponse() {
        String url = String.format(wcfAllPlansUrl, wcfBaseUrl);
        HttpHeaders headers =
                WcfUtils.getHeaders(
                        METHOD_GET, String.format(wcfAllPlansUrl, ""), null, wcfApiAppId, wcfApiSecretKey);
        long startTime = System.currentTimeMillis();
        log.info("All plans Request Url : {}", url);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ResponseData<AllPlansResponse>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseData<AllPlansResponse>>(){});
        log.info("All plans Response : {}", response);
        return response.getBody().getData();
    }

    @TimeIt
    public AllProductsResponse getAllProductsResponse() {
        String url = String.format(wcfAllProductsUrl, wcfBaseUrl);
        HttpHeaders headers =
                WcfUtils.getHeaders(
                        METHOD_GET, String.format(wcfAllProductsUrl, ""), null, wcfApiAppId, wcfApiSecretKey);
        log.info("All products Request Url : {}", url);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ResponseData<AllProductsResponse>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseData<AllProductsResponse>>(){});
        log.info("All products Response : {}", response);
        return response.getBody().getData();
    }

    @TimeIt
    public AllOffersResponse getAllOffersResponse() {
        String url = String.format(wcfAllOffersUrl, wcfBaseUrl);
        HttpHeaders headerMap =
                WcfUtils.getHeaders(
                        METHOD_GET, String.format(wcfAllOffersUrl, ""), null, wcfApiAppId, wcfApiSecretKey);


        log.info("All products Request Url : {}", url);
        HttpEntity<?> httpEntity = new HttpEntity<>(headerMap);
        ResponseEntity<ResponseData<AllOffersResponse>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseData<AllOffersResponse>>(){});
        log.info("All offers Response  : {}", response);
        return response.getBody().getData();
    }
}
