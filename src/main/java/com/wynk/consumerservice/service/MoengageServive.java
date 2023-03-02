package com.wynk.consumerservice.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoengageServive {

    private void sendEventToMoEngage(User user, String eventName, String purchasedPack) {
        Map<String, String> attributes = createAttributes(user, purchasedPack, eventName);
        MoEngageEvent moEvent = createEvent(eventName, attributes);
        List<MoEngageEvent> eventsList = new ArrayList<>();
        eventsList.add(moEvent);
        pushEventsToMoEngage(user.getUid(), eventsList);
    }

    private Map<String, String> createAttributes(User user, String purchasedPack, String eventName) {
        Map<String, String> attributes = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
        attributes.put(MoEngageEventAttributes.CurrentPlanID.name(), purchasedPack);
        attributes.put(MoEngageEventAttributes.Msisdn.name(), user.getMsisdn());
        attributes.put(MoEngageEventAttributes.Timestamp.name(), simpleDateFormat.format(date));
        if (eventName.equalsIgnoreCase(MoEngageEventNames.PackPurchased.name())) {
            UserSubscription.ProductMeta userLatestSubscription =
                    user.getUserSubscription().getProdIds().get(indexOfLatestOffer(user));
            attributes.put(
                    MoEngageEventAttributes.OldPlanID.name(),
                    String.valueOf(userLatestSubscription.getPlanId()));
            attributes.put(
                    MoEngageEventAttributes.OldOfferID.name(),
                    String.valueOf(userLatestSubscription.getOfferId()));
        }
        return attributes;
    }

    private MoEngageEvent createEvent(String eventName, Map<String, String> attributes) {
        return new MoEngageEvent(eventName, attributes);
    }

    private void pushEventsToMoEngage(String uid, List<MoEngageEvent> eventsList) {
        MoEngageEventRequest moEngageEventRequest =
                new MoEngageEventRequest(uid, eventsList);
        try {
            moEngageEventService.sendEvent(moEngageEventRequest);
        } catch (Exception e) {
            logger.error("Error sending HT Event to MoEngage for user " + uid);
        }
    }
}
