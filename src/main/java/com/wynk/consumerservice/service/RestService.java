package com.wynk.consumerservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : Kunal Sharma
 * @since : 08/02/23, Wednesday
 **/


@Service
@Slf4j
public class RestService {
    public void processRecord(String jsonPayload) {
        log.info("Going to hit sapi with payload {} ", jsonPayload);

       // logic need to add here to hit api






    }
}
