package com.wynk.consumerservice.service;

import com.wynk.consumerservice.annotation.TimeIt;
import com.wynk.consumerservice.constants.MusicConstants;
import com.wynk.consumerservice.utils.WcfUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.wynk.consumerservice.constants.MusicConstants.AUTO_ACTIVATE_ENDPOINT;

@Service
@Slf4j
public class HtApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${ht.auto.activate.endpoint}")
    private String htAutoActivateEndpoint;

    @Value("${ht.api.app.id}")
    private String htApiAppId;

    @Value("${ht.api.secret.key}")
    private String htApiSecretKey;

    @TimeIt
    public String autoActivateHellotunes(String uid) {
        String url = String.format(htAutoActivateEndpoint, uid);
        Map<String, String> headerMap = WcfUtils.getHeaderMap(MusicConstants.METHOD_POST,
                String.format(AUTO_ACTIVATE_ENDPOINT, uid), null, htApiAppId, htApiSecretKey);
        long startTime = System.currentTimeMillis();
        log.info("Ht auto activate Url : {} with header : {}", url, headerMap);
        HttpEntity<?> httpEntity = new HttpEntity<>(headerMap);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        log.info("HT auto activate Api response Data  : {}", response);
        return response.getBody();
    }
}
