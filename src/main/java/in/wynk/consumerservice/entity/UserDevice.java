package in.wynk.consumerservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDevice {
  @Field("dt")
  @JsonProperty("dt")
  private String deviceType = "";

  @Field("di")
  @JsonProperty("di")
  private String deviceId = "";

  @Field("dk")
  @JsonProperty("dk")
  private String deviceKey = "";

  @Field("dv")
  @JsonProperty("dv")
  private String deviceVersion = "";

  @Field("os")
  @JsonProperty("os")
  private String os = null;

  @Field("ov")
  @JsonProperty("ov")
  private String osVersion = null;

  @Field("av")
  @JsonProperty("av")
  private String appVersion = "";

  @Field("bn")
  @JsonProperty("bn")
  private int buildNumber;

  @Field("drd")
  @JsonProperty("drd")
  private long registrationDate = System.currentTimeMillis();

  @Field("dlat")
  @JsonProperty("dlat")
  private long lastActivityTime = System.currentTimeMillis();

  @Field("drs")
  @JsonProperty("drs")
  private String resolution;

  @Field("dop")
  @JsonProperty("dop")
  private String operator;

  @Field("dproem")
  @JsonProperty("dproem")
  private String preInstallOem;

  @Field("dklut")
  @JsonProperty("dklut")
  private long deviceKeyLastUpdateTime;

  @Field("dadvi")
  @JsonProperty("dadvi")
  private String advertisingId;

  @Field("dfbid")
  @JsonProperty("dfbid")
  private String fbUserId;

  @Field("dimei")
  @JsonProperty("dimei")
  private String imeiNumber;

  @Field("dsim")
  @JsonProperty("dsim")
  private Boolean isDualSim;

  @Field("sim")
  @JsonProperty("sim")
  private List<SimInfo> simInfo;

  @Field("inlr")
  @JsonProperty("inlr")
  private IntlRoaming intlRoaming;

  @Field("rc")
  @JsonProperty("rc")
  private WCFRegistrationChannel registrationChannel;


  @Field("tdi")
  @JsonProperty("tdi")
  private String totpDecviceId;


  @Field("tk")
  @JsonProperty("tk")
  private String totpKey;

  private UserDevice() {
  }

  // @Field("msisdn")
  private String msisdn;

  public String getDeviceType() {
    return deviceType;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public String getTotpKey() { return totpKey; }

  public void setTotpKey(String totKey) { this.totpKey = totKey; }

  public String getDeviceKey() {
    return deviceKey;
  }

  public String getDeviceVersion() {
    return deviceVersion;
  }

  public String getTotpDecviceId() { return totpDecviceId; }

  public void setTotpDecviceId(String totpDecviceId) { this.totpDecviceId = totpDecviceId; }

  public String getOs() {
    return os;
  }

  public String getOsVersion() {
    return osVersion;
  }

  public String getAppVersion() {
    return appVersion;
  }

  public int getBuildNumber() {
    return buildNumber;
  }

  public long getRegistrationDate() {
    return registrationDate;
  }

  public long getLastActivityTime() {
    return lastActivityTime;
  }

  public String getResolution() {
    return resolution;
  }

  public String getOperator() {
    return operator;
  }

  public String getPreInstallOem() {
    return preInstallOem;
  }

  public long getDeviceKeyLastUpdateTime() {
    return deviceKeyLastUpdateTime;
  }

  public String getAdvertisingId() {
    return advertisingId;
  }

  public String getFbUserId() {
    return fbUserId;
  }

  public String getImeiNumber() {
    return imeiNumber;
  }

  public Boolean getDualSim() {
    return isDualSim;
  }

  public List<SimInfo> getSimInfo() {
    return simInfo;
  }

  public IntlRoaming getIntlRoaming() {
    return intlRoaming;
  }

  public WCFRegistrationChannel getRegistrationChannel() {
    return registrationChannel;
  }

  public String getMsisdn() {
    return msisdn;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public void setDeviceKey(String deviceKey) {
    this.deviceKey = deviceKey;
  }

  public void setDeviceVersion(String deviceVersion) {
    this.deviceVersion = deviceVersion;
  }

  public void setOs(String os) {
    this.os = os;
  }

  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  public void setBuildNumber(int buildNumber) {
    this.buildNumber = buildNumber;
  }

  public void setRegistrationDate(long registrationDate) {
    this.registrationDate = registrationDate;
  }

  public void setLastActivityTime(long lastActivityTime) {
    this.lastActivityTime = lastActivityTime;
  }

  public void setResolution(String resolution) {
    this.resolution = resolution;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public void setPreInstallOem(String preInstallOem) {
    this.preInstallOem = preInstallOem;
  }

  public void setDeviceKeyLastUpdateTime(long deviceKeyLastUpdateTime) {
    this.deviceKeyLastUpdateTime = deviceKeyLastUpdateTime;
  }

  public void setAdvertisingId(String advertisingId) {
    this.advertisingId = advertisingId;
  }

  public void setFbUserId(String fbUserId) {
    this.fbUserId = fbUserId;
  }

  public void setImeiNumber(String imeiNumber) {
    this.imeiNumber = imeiNumber;
  }

  public void setDualSim(Boolean dualSim) {
    isDualSim = dualSim;
  }

  public void setSimInfo(List<SimInfo> simInfo) {
    this.simInfo = simInfo;
  }

  public void setIntlRoaming(IntlRoaming intlRoaming) {
    this.intlRoaming = intlRoaming;
  }

  public void setRegistrationChannel(WCFRegistrationChannel registrationChannel) {
    this.registrationChannel = registrationChannel;
  }

  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }

  public static final class UserDeviceBuilder {
    private String deviceType = ""; // model of device
    private String deviceId = ""; // GCM key
    private String deviceKey = ""; // used for push notification
    private String deviceVersion = "";
    private String os = null; // Lets club all 4 into the x-bsy-did format
    private String osVersion = null;
    private String appVersion = "";
    private int buildNumber;
    private long registrationDate = System.currentTimeMillis();
    private long lastActivityTime = System.currentTimeMillis();
    private String resolution;
    private String operator;
    private String preInstallOem; // mmx/moto/...
    private long deviceKeyLastUpdateTime;
    private String advertisingId;
    private String fbUserId;
    private String imeiNumber;
    private Boolean isDualSim;
    private List<SimInfo> simInfo;
    private IntlRoaming intlRoaming;
    private WCFRegistrationChannel registrationChannel;
    private String msisdn;

    private UserDeviceBuilder() {}

    public static UserDeviceBuilder anUserDevice() {
      return new UserDeviceBuilder();
    }

    public UserDeviceBuilder deviceType(String deviceType) {
      this.deviceType = deviceType;
      return this;
    }

    public UserDeviceBuilder deviceId(String deviceId) {
      this.deviceId = deviceId;
      return this;
    }

    public UserDeviceBuilder deviceKey(String deviceKey) {
      this.deviceKey = deviceKey;
      return this;
    }

    public UserDeviceBuilder deviceVersion(String deviceVersion) {
      this.deviceVersion = deviceVersion;
      return this;
    }

    public UserDeviceBuilder os(String os) {
      this.os = os;
      return this;
    }

    public UserDeviceBuilder osVersion(String osVersion) {
      this.osVersion = osVersion;
      return this;
    }

    public UserDeviceBuilder appVersion(String appVersion) {
      this.appVersion = appVersion;
      return this;
    }

    public UserDeviceBuilder buildNumber(int buildNumber) {
      this.buildNumber = buildNumber;
      return this;
    }

    public UserDeviceBuilder registrationDate(long registrationDate) {
      this.registrationDate = registrationDate;
      return this;
    }

    public UserDeviceBuilder lastActivityTime(long lastActivityTime) {
      this.lastActivityTime = lastActivityTime;
      return this;
    }

    public UserDeviceBuilder resolution(String resolution) {
      this.resolution = resolution;
      return this;
    }

    public UserDeviceBuilder operator(String operator) {
      this.operator = operator;
      return this;
    }

    public UserDeviceBuilder preInstallOem(String preInstallOem) {
      this.preInstallOem = preInstallOem;
      return this;
    }

    public UserDeviceBuilder deviceKeyLastUpdateTime(long deviceKeyLastUpdateTime) {
      this.deviceKeyLastUpdateTime = deviceKeyLastUpdateTime;
      return this;
    }

    public UserDeviceBuilder advertisingId(String advertisingId) {
      this.advertisingId = advertisingId;
      return this;
    }

    public UserDeviceBuilder fbUserId(String fbUserId) {
      this.fbUserId = fbUserId;
      return this;
    }

    public UserDeviceBuilder imeiNumber(String imeiNumber) {
      this.imeiNumber = imeiNumber;
      return this;
    }

    public UserDeviceBuilder isDualSim(Boolean isDualSim) {
      this.isDualSim = isDualSim;
      return this;
    }

    public UserDeviceBuilder simInfo(List<SimInfo> simInfo) {
      this.simInfo = simInfo;
      return this;
    }

    public UserDeviceBuilder intlRoaming(IntlRoaming intlRoaming) {
      this.intlRoaming = intlRoaming;
      return this;
    }

    public UserDeviceBuilder registrationChannel(WCFRegistrationChannel registrationChannel) {
      this.registrationChannel = registrationChannel;
      return this;
    }

    public UserDeviceBuilder msisdn(String msisdn) {
      this.msisdn = msisdn;
      return this;
    }

    public UserDevice build() {
      UserDevice userDevice = new UserDevice();
      userDevice.preInstallOem = this.preInstallOem;
      userDevice.fbUserId = this.fbUserId;
      userDevice.deviceId = this.deviceId;
      userDevice.lastActivityTime = this.lastActivityTime;
      userDevice.simInfo = this.simInfo;
      userDevice.registrationChannel = this.registrationChannel;
      userDevice.deviceType = this.deviceType;
      userDevice.operator = this.operator;
      userDevice.resolution = this.resolution;
      userDevice.deviceKey = this.deviceKey;
      userDevice.intlRoaming = this.intlRoaming;
      userDevice.imeiNumber = this.imeiNumber;
      userDevice.appVersion = this.appVersion;
      userDevice.deviceVersion = this.deviceVersion;
      userDevice.os = this.os;
      userDevice.osVersion = this.osVersion;
      userDevice.buildNumber = this.buildNumber;
      userDevice.deviceKeyLastUpdateTime = this.deviceKeyLastUpdateTime;
      userDevice.registrationDate = this.registrationDate;
      userDevice.advertisingId = this.advertisingId;
      userDevice.isDualSim = this.isDualSim;
      userDevice.msisdn = this.msisdn;
      return userDevice;
    }
  }
}
