package in.wynk.consumerservice.dto;

public enum PlanType {
  FREE("Free"),
  ONE_TIME_SUBSCRIPTION("OneTimeSubscription"),
  FREE_TRIAL("FreeTrial"),
  SUBSCRIPTION("Subscription");


  private String value;

  PlanType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}