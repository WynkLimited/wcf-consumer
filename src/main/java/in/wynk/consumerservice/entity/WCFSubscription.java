package in.wynk.consumerservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WCFSubscription {

  @Field("ets")
  @JsonProperty("ets")
  public final Long expireTS;

  @Field("ots")
  @JsonProperty("ots")
  public final Long offerTS;

  @Field("pdId")
  @JsonProperty("pdId")
  public final Integer productId;

  @Field("rpdId")
  @JsonProperty("rpdId")
  public final List<Integer> recommendedProductId;

  @Field("eopdId")
  @JsonProperty("eopdId")
  public final List<Integer> eligibleOfferProductId;

  @Field("isSub")
  @JsonProperty("isSub")
  public final Boolean isSubscribed;

  @Field("lts")
  @JsonProperty("lts")
  public final Long lastUpdatedTS;


  public WCFSubscription(
      @JsonProperty("ets") Long expireTS,
      @JsonProperty("ots") Long offerTS,
      @JsonProperty("pdId") Integer productId,
      @JsonProperty("rpdId") List<Integer> recommendedProductId,
      @JsonProperty("eopdId") List<Integer> eligibleOfferProductId,
      @JsonProperty("isSub") Boolean isSubscribed,
      @JsonProperty("lts") Long lastUpdatedTS) {
    this.expireTS = expireTS;
    this.offerTS = offerTS;
    this.productId = productId;
    this.recommendedProductId = recommendedProductId;
    this.eligibleOfferProductId = eligibleOfferProductId;
    this.isSubscribed = isSubscribed;
    this.lastUpdatedTS = lastUpdatedTS;
  }

  public Long getExpireTS() {
    return expireTS;
  }

  public Long getOfferTS() {
    return offerTS;
  }

  public Integer getProductId() {
    return productId;
  }

  public List<Integer> getRecommendedProductId() {
    return recommendedProductId;
  }

  public List<Integer> getEligibleOfferProductId() {
    return eligibleOfferProductId;
  }

  @JsonIgnore
  public Boolean getSubscribed() {
    return isSubscribed;
  }

  public Long getLastUpdatedTS() {
    return lastUpdatedTS;
  }

  public Boolean getIsSubscribed() {
    return this.isSubscribed;
  }

  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof WCFSubscription)) return false;
    final WCFSubscription other = (WCFSubscription) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$expireTS = this.getExpireTS();
    final Object other$expireTS = other.getExpireTS();
    if (this$expireTS == null ? other$expireTS != null : !this$expireTS.equals(other$expireTS)) return false;
    final Object this$offerTS = this.getOfferTS();
    final Object other$offerTS = other.getOfferTS();
    if (this$offerTS == null ? other$offerTS != null : !this$offerTS.equals(other$offerTS)) return false;
    final Object this$productId = this.getProductId();
    final Object other$productId = other.getProductId();
    if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) return false;
    final Object this$recommendedProductId = this.getRecommendedProductId();
    final Object other$recommendedProductId = other.getRecommendedProductId();
    if (this$recommendedProductId == null ? other$recommendedProductId != null : !this$recommendedProductId.equals(other$recommendedProductId))
      return false;
    final Object this$eligibleOfferProductId = this.getEligibleOfferProductId();
    final Object other$eligibleOfferProductId = other.getEligibleOfferProductId();
    if (this$eligibleOfferProductId == null ? other$eligibleOfferProductId != null : !this$eligibleOfferProductId.equals(other$eligibleOfferProductId))
      return false;
    final Object this$isSubscribed = this.getIsSubscribed();
    final Object other$isSubscribed = other.getIsSubscribed();
    if (this$isSubscribed == null ? other$isSubscribed != null : !this$isSubscribed.equals(other$isSubscribed))
      return false;
    final Object this$lastUpdatedTS = this.getLastUpdatedTS();
    final Object other$lastUpdatedTS = other.getLastUpdatedTS();
    if (this$lastUpdatedTS == null ? other$lastUpdatedTS != null : !this$lastUpdatedTS.equals(other$lastUpdatedTS))
      return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $expireTS = this.getExpireTS();
    result = result * PRIME + ($expireTS == null ? 43 : $expireTS.hashCode());
    final Object $offerTS = this.getOfferTS();
    result = result * PRIME + ($offerTS == null ? 43 : $offerTS.hashCode());
    final Object $productId = this.getProductId();
    result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
    final Object $recommendedProductId = this.getRecommendedProductId();
    result = result * PRIME + ($recommendedProductId == null ? 43 : $recommendedProductId.hashCode());
    final Object $eligibleOfferProductId = this.getEligibleOfferProductId();
    result = result * PRIME + ($eligibleOfferProductId == null ? 43 : $eligibleOfferProductId.hashCode());
    final Object $isSubscribed = this.getIsSubscribed();
    result = result * PRIME + ($isSubscribed == null ? 43 : $isSubscribed.hashCode());
    final Object $lastUpdatedTS = this.getLastUpdatedTS();
    result = result * PRIME + ($lastUpdatedTS == null ? 43 : $lastUpdatedTS.hashCode());
    return result;
  }

  protected boolean canEqual(Object other) {
    return other instanceof WCFSubscription;
  }

  public String toString() {
    return "WCFSubscription(expireTS=" + this.getExpireTS() + ", offerTS=" + this.getOfferTS() + ", productId=" + this.getProductId() + ", recommendedProductId=" + this.getRecommendedProductId() + ", eligibleOfferProductId=" + this.getEligibleOfferProductId() + ", isSubscribed=" + this.getIsSubscribed() + ", lastUpdatedTS=" + this.getLastUpdatedTS() + ")";
  }
}
