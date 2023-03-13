package in.wynk.consumerservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@EqualsAndHashCode
@Builder
public class Product {

    @Field("ets")
    @JsonProperty("ets")
    private Long ets;

    @Field("offerId")
    @JsonProperty("offerId")
    private Integer offerId;

    @Field("planId")
    @JsonProperty("planId")
    private Integer planId;

    @Field("prodId")
    @JsonProperty("prodId")
    private Integer prodId;
}
