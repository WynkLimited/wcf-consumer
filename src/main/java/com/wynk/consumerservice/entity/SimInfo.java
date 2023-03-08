package com.wynk.consumerservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;

public class SimInfo {

  @Field("nm")
  @JsonProperty("nm")
  private String name;

  @Field("cr")
  @JsonProperty("cr")
  private String carrier;

  @Field("rm")
  @JsonProperty("rm")
  private Boolean roaming;

  @Field("nw")
  @JsonProperty("nw")
  private String network;

  @Field("imei")
  @JsonProperty("imei")
  private String imeiNumber;

  @Field("mcc")
  @JsonProperty("mcc")
  private String mcc;

  @Field("mnc")
  @JsonProperty("mnc")
  private String mnc;

  @Field("imsi")
  @JsonProperty("imsi")
  private String imsiNumber;

  public SimInfo() {
  }

  public SimInfo(
      String name,
      String carrier,
      Boolean roaming,
      String network,
      String imeiNumber,
      String mcc,
      String mnc,
      String imsiNumber) {
    this.name = name;
    this.carrier = carrier;
    this.roaming = roaming;
    this.network = network;
    this.imeiNumber = imeiNumber;
    this.mcc = mcc;
    this.mnc = mnc;
    this.imsiNumber = imsiNumber;
  }

  public String getMcc() {
    return mcc;
  }

  public void setMcc(String mcc) {
    this.mcc = mcc;
  }

  public String getMnc() {
    return mnc;
  }

  public void setMnc(String mnc) {
    this.mnc = mnc;
  }

  public Boolean getRoaming() {
    return roaming;
  }

  public void setRoaming(Boolean roaming) {
    this.roaming = roaming;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCarrier() {
    return carrier;
  }

  public void setCarrier(String carrier) {
    this.carrier = carrier;
  }

  public String getNetwork() {
    return network;
  }

  public void setNetwork(String network) {
    this.network = network;
  }

  public String getImeiNumber() {
    return imeiNumber;
  }

  public void setImeiNumber(String imeiNumber) {
    this.imeiNumber = imeiNumber;
  }

  public String getImsiNumber() {
    return imsiNumber;
  }

  public void setImsiNumber(String imsiNumber) {
    this.imsiNumber = imsiNumber;
  }
}
