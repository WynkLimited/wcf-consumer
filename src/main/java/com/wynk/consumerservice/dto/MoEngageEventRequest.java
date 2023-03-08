package com.wynk.consumerservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MoEngageEventRequest {

    @JsonProperty("customer_id")
    private String uid;

    @JsonProperty("type")
    private String type;

    @JsonProperty("actions")
    private List<MoEngageEvent> eventList;
}
