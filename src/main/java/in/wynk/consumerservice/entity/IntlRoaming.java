package in.wynk.consumerservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;

public class IntlRoaming {

  @Field("ipd")
  @JsonProperty("ipd")
  private long initialProvisionedDate; // date on which initial provisioning had started

  @Field("pd")
  @JsonProperty("pd")
  private long provisionedDate; // date on which current provisioning has started

  @Field("ire")
  @JsonProperty("ire")
  private boolean intlRoamingEnabled; // current international roaming activated

  @Field("cct")
  @JsonProperty("cct")
  private long countConsumedTime; // total count of consumed time while on international roaming

  @Field("lcut")
  @JsonProperty("lcut")
  private long lastConsumptionUpdateTime; // last timestamp of consumed time update to db

  public IntlRoaming() {}

  public IntlRoaming(
      long initialProvisionedDate,
      long provisionedDate,
      boolean intlRoamingEnabled,
      long countConsumedTime,
      long lastConsumptionUpdateTime) {
    this.initialProvisionedDate = initialProvisionedDate;
    this.provisionedDate = provisionedDate;
    this.intlRoamingEnabled = intlRoamingEnabled;
    this.countConsumedTime = countConsumedTime;
    this.lastConsumptionUpdateTime = lastConsumptionUpdateTime;
  }

  public long getInitialProvisionedDate() {
    return initialProvisionedDate;
  }

  public long getProvisionedDate() {
    return provisionedDate;
  }

  public boolean isIntlRoamingEnabled() {
    return intlRoamingEnabled;
  }

  public long getCountConsumedTime() {
    return countConsumedTime;
  }

  public long getLastConsumptionUpdateTime() {
    return lastConsumptionUpdateTime;
  }

  public void setInitialProvisionedDate(long initialProvisionedDate) {
    this.initialProvisionedDate = initialProvisionedDate;
  }

  public void setProvisionedDate(long provisionedDate) {
    this.provisionedDate = provisionedDate;
  }

  public void setIntlRoamingEnabled(boolean intlRoamingEnabled) {
    this.intlRoamingEnabled = intlRoamingEnabled;
  }

  public void setCountConsumedTime(long countConsumedTime) {
    this.countConsumedTime = countConsumedTime;
  }

  public void setLastConsumptionUpdateTime(long lastConsumptionUpdateTime) {
    this.lastConsumptionUpdateTime = lastConsumptionUpdateTime;
  }
}
