package in.wynk.consumerservice.service;

import in.wynk.consumerservice.constants.MoEngageEventAttributes;
import in.wynk.consumerservice.constants.MoEngageEventNames;
import in.wynk.consumerservice.dto.MoEngageEvent;
import in.wynk.consumerservice.dto.MoEngageEventRequest;
import in.wynk.consumerservice.entity.Product;
import in.wynk.consumerservice.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MoEngageService {

    @Autowired
    private MoEngageEventService moEngageEventService;

    @Autowired
    private WcfCachingService wcfCachingService;

    public void sendEventToMoEngage(User user, String eventName, String purchasedPack) {
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
            Product userLatestSubscription =
                    user.getNewSubscription().getProdIds().get(indexOfLatestOffer(user));
            attributes.put(
                    MoEngageEventAttributes.OldPlanID.name(),
                    String.valueOf(userLatestSubscription.getPlanId()));
            attributes.put(
                    MoEngageEventAttributes.OldOfferID.name(),
                    String.valueOf(userLatestSubscription.getOfferId()));
        }
        return attributes;
    }

    private int indexOfLatestOffer(User user) {
        List<Integer> listOfHierarchy =
                user.getNewSubscription().getProdIds().stream()
                        .map(
                                i -> Objects.requireNonNull(wcfCachingService.getOffer(i.getOfferId())).getHierarchy())
                        .collect(Collectors.toList());
        int max = Collections.max(listOfHierarchy);
        return listOfHierarchy.indexOf(max);
    }


    private MoEngageEvent createEvent(String eventName, Map<String, String> attributes) {
        return MoEngageEvent.builder().eventName(eventName).attributes(attributes).build();
    }

    private void pushEventsToMoEngage(String uid, List<MoEngageEvent> eventsList) {
        MoEngageEventRequest moEngageEventRequest = MoEngageEventRequest.builder().uid(uid).eventList(eventsList).type("event").build();

        try {
            moEngageEventService.sendEvent(moEngageEventRequest);
        } catch (Exception e) {
            log.error("Error sending HT Event to MoEngage for user {} ", uid);
        }
    }
}
