package in.wynk.consumerservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;

public class WCFRegistrationChannel {

  @Field("wcf_cn")
  @JsonProperty("wcf_cn")
  public final String channelName;

  @Field("wcf_ivu")
  @JsonProperty("wcf_ivu")
  public final Boolean isVerifiedUser;

  public WCFRegistrationChannel(
      @JsonProperty("wcf_cn") String channelName, @JsonProperty("wcf_ivu") Boolean isVerifiedUser) {
    this.channelName = channelName;
    this.isVerifiedUser = isVerifiedUser;
  }
}
