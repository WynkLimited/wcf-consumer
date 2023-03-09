package in.wynk.consumerservice.service;

import in.wynk.consumerservice.annotation.TimeIt;
import in.wynk.consumerservice.constants.MusicConstants;
import in.wynk.consumerservice.utils.WcfUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static in.wynk.consumerservice.constants.MusicConstants.AUTO_ACTIVATE_ENDPOINT;

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
        HttpHeaders headers = WcfUtils.getHeaders(MusicConstants.METHOD_POST,
                String.format(AUTO_ACTIVATE_ENDPOINT, uid), null, htApiAppId, htApiSecretKey);
        long startTime = System.currentTimeMillis();
        log.info("Ht auto activate Url : {} with header : {}", url, headers);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        log.info("HT auto activate Api response Data  : {}", response);
        return response.getBody();
    }
}
