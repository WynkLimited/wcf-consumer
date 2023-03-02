package com.wynk.consumerservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MoengageEventRequest {

    @JsonProperty("customer_id")
    private String uid;

    @JsonProperty("type")
    private String type;

    @JsonProperty("actions")
    private List<MoengageEvent> eventList;
}
