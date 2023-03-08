package com.wynk.consumerservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class MoEngageEvent {
    @JsonProperty("action")
    private String eventName;
    @JsonProperty("attributes")
    private Map<String, String> attributes;
}
