package in.wynk.consumerservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubscriptionStatusResponse {

    private boolean success;
    private String rid;
    private List<SubscriptionStatus> data;
}