package in.wynk.consumerservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Field;

@JsonIgnoreProperties()
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FUPPack {

  private static final Logger logger = LoggerFactory.getLogger(FUPPack.class.getCanonicalName());

  private static final Logger mFUPReachedLogger = LoggerFactory.getLogger("mFupReached");

  @Field("cd")
  @JsonProperty("cd")
  private long creationTime;

  @Field("fw")
  @JsonProperty("fw")
  private boolean shownFUPWarning = false;

  @Field("fw95")
  @JsonProperty("fw95")
  private boolean shownFUP95Warning = false;

  @Field("sc")
  @JsonProperty("sc")
  private int streamedCount;

  @JsonIgnore
  @Field("rc")
  @JsonProperty("rc")
  private int rentalsCount;

  @Field("lrd")
  @JsonProperty("lrd")
  private long lastFUPResetDate;

  public FUPPack() {}

  public FUPPack(long creationTime, long packValidity) {
    setLastFUPResetDate(creationTime);
  }

  public boolean isShownFUPWarning() {
    return shownFUPWarning;
  }

  public void setShownFUPWarning(boolean shownFUPWarning) {
    this.shownFUPWarning = shownFUPWarning;
  }

  public boolean isShownFUP95Warning() {
    return shownFUP95Warning;
  }

  public void setShownFUP95Warning(boolean shownFUP95Warning) {
    this.shownFUP95Warning = shownFUP95Warning;
  }

  public int getStreamedCount() {
    return streamedCount;
  }

  public void setStreamedCount(int streamedCount) {
    this.streamedCount = streamedCount;
  }

  public long getLastFUPResetDate() {
    return lastFUPResetDate;
  }

  public void setLastFUPResetDate(long lastFUPResetDate) {
    this.lastFUPResetDate = lastFUPResetDate;
  }

  public boolean isWAPUser(String uid) {
    return uid.endsWith("4");
  }

  public long getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(long creationTime) {
    this.creationTime = creationTime;
  }

  public int getRentalsCount() {
    return rentalsCount;
  }

  public void setRentalsCount(int rentalsCount) {
    this.rentalsCount = rentalsCount;
  }
}
