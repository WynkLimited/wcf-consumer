package in.wynk.consumerservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author : Kunal Sharma
 * @since : 08/02/23, Wednesday
 **/

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SubscriptionEvent {

    private String uid;
    private String msisdn;
    private String event;
    private String status;
    private Integer planId;
    private Long validTillDate;
    private Boolean active;
    private Boolean autoRenewal;
    private String service;
    private String referenceId;
    private String telcoUnlimited;
    private String preferredPartner;

}
