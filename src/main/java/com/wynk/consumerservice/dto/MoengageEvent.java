package com.wynk.consumerservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class MoengageEvent {
    @JsonProperty("action")
    private String eventName;
    @JsonProperty("attributes")
    private Map<String, String> attributes;
}
