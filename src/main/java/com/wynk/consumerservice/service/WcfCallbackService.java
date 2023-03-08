package com.wynk.consumerservice.service;

import com.wynk.consumerservice.constants.EventTypes;
import com.wynk.consumerservice.constants.MoEngageEventNames;
import com.wynk.consumerservice.dao.UserDao;
import com.wynk.consumerservice.dto.SubscriptionEvent;
import com.wynk.consumerservice.dto.SubscriptionStatus;
import com.wynk.consumerservice.dto.SubscriptionStatusResponse;
import com.wynk.consumerservice.dto.UserSubscription;
import com.wynk.consumerservice.entity.NewWCFSubscription;
import com.wynk.consumerservice.entity.User;
import com.wynk.consumerservice.entity.UserDevice;
import com.wynk.consumerservice.exception.WynkRuntimeException;
import com.wynk.consumerservice.utils.AppUtils;
import com.wynk.consumerservice.utils.WcfUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Slf4j
public class WcfCallbackService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MoEngageService moEngageService;

    @Autowired
    private WcfApiService wcfApiService;

    @Autowired
    private HtApiService htApiService;

    @Autowired
    private WcfUtils wcfUtils;

    public String handleCallback(SubscriptionEvent subscriptionEvent) {
        log.info("Calback recieved for uid {}, event {}", subscriptionEvent.getUid(), subscriptionEvent.getEvent());

        if(StringUtils.isAnyBlank(subscriptionEvent.getEvent(), subscriptionEvent.getUid())) {
            log.info("Event or uid is missing in event");
            throw new WynkRuntimeException("Event or uid is missing in event");
        }

        if(! isValidEvent(subscriptionEvent.getEvent())) {
            log.info("Invalid event type : {}", subscriptionEvent.getEvent());
            return HttpStatus.OK.toString();
        }

        String uid = subscriptionEvent.getUid();
        User user = userService.getUserFromUid(uid);
        if(Objects.isNull(user)) {
            log.info("User not found uid {}", uid);
            throw new WynkRuntimeException("User not found");
        }
        //send moengage event
        sendEventsToMoEngage(subscriptionEvent, user);

        //refresh subscription data
        //refreshSubsData(subscriptionEvent, user);

        Map<String, Object> paramValues = WcfUtils.getFupResetParameterMap(subscriptionEvent.getValidTillDate());
        NewWCFSubscription userSubscription = null;
        try {
            SubscriptionStatusResponse subsStatusResponse = wcfApiService.getSubscriptionStatus(uid);
            List<SubscriptionStatus> subscriptionStatusList = subsStatusResponse.getData();
            if (subscriptionStatusList.isEmpty()) {
                log.info("empty response from WCF");
                throw new WynkRuntimeException("Subscription list empty response");
            }
            userSubscription = wcfUtils.getSubsObjFromSubsStatus(subscriptionStatusList);
        } catch (Exception e) {
            userSubscription = wcfUtils.getUpdatedSubscriptionObject(user,subscriptionEvent);
        }
        paramValues.put(User.MongoUserEntityKey.newSubscription, AppUtils.gson.toJsonTree(userSubscription));
        userService.updateUser(uid, paramValues);

        try {
            htApiService.autoActivateHellotunes(user.getUid());
        } catch (Exception e) {
            log.error("Error while auto activating hellotunes for the uid : {}", user.getUid());
        }

        return HttpStatus.OK.toString();
    }

    private boolean isValidEvent(String event) {
        return Stream.of(EventTypes.values()).anyMatch(val -> event.equalsIgnoreCase(val.name()));
    }

    private void sendEventsToMoEngage(SubscriptionEvent subscriptionEvent, User user) {
        switch (EventTypes.valueOf(subscriptionEvent.getEvent())) {
            case SUBSCRIBE :
            case PURCHASE:
                moEngageService.sendEventToMoEngage(user, MoEngageEventNames.PackPurchased.name(),
                        subscriptionEvent.getPlanId().toString());
                break;

            case RENEW:
                moEngageService.sendEventToMoEngage(user, MoEngageEventNames.PackRenewed.name(),
                        subscriptionEvent.getPlanId().toString());
                break;

            default:
                log.info("No Moengage Event");
                break;
        }
    }

    /*private void refreshSubsData(SubscriptionEvent subscriptionEvent, User user) {
        try {
            String deviceId = "";
            String appVersion = "";
            int buildNo = 0;
            String os = "Android";
            if (CollectionUtils.isNotEmpty(user.getDevices())) {
                List<UserDevice> devices = user.getDevices();
                Collections.reverse(devices);
                for (UserDevice device : devices) {
                    if (device != null && device.getOs() != null) {
                        os = device.getOs();
                        buildNo = device.getBuildNumber();
                        if (device.getDeviceId() != null) {
                            deviceId = device.getDeviceId();
                        }
                        if (device.getAppVersion() != null) {
                            appVersion = device.getAppVersion();
                        }
                        break;
                    }
                }
            }
            OfferProvisionRequest offerProvisionRequest = OfferProvisionRequest.builder()
                    .appId(APPID_MOBILITY)
                    .deviceId(deviceId)
                    .appVersion(appVersion)
                    .msisdn(user.getMsisdn())
                    .service(MUSIC)
                    .buildNo(buildNo)
                    .os(os)
                    .geoLocation(new GeoLocation(user.getCountryId()))
                    .uid(uid).build();
            logger.info("Refreshing Subs : Again hitting offerProvisionRequest with body {}", offerProvisionRequest.toString());
            UserSubscription latestUserSubs = wcfApisService.getUserSubscription(offerProvisionRequest);
            if (latestUserSubs != null) {
                Map<String, Object> paramValues = new HashMap<>();
                paramValues.put(UserEntityKey.userSubscription, JsonUtils.getJsonObjectFromString(latestUserSubs.toJson()));
                accountService.updateSubscriptionForUserId(uid, paramValues, Boolean.FALSE, latestUserSubs);
            }
        } catch (Exception e) {
            logger.error("Error while refreshing subscription data for uid : {}", uid);
            LogstashLoggerUtils.createAccessLogLite("SubsRefreshError", e.getMessage(), uid);
        }
    }*/
}
