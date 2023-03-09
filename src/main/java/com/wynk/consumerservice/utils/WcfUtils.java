package com.wynk.consumerservice.utils;

import com.wynk.consumerservice.constants.FupPack;
import com.wynk.consumerservice.dto.Offer;
import com.wynk.consumerservice.dto.PlanDTO;
import com.wynk.consumerservice.dto.SubscriptionEvent;
import com.wynk.consumerservice.dto.SubscriptionStatus;
import com.wynk.consumerservice.entity.NewWCFSubscription;
import com.wynk.consumerservice.entity.Product;
import com.wynk.consumerservice.entity.User;
import com.wynk.consumerservice.exception.WynkRuntimeException;
import com.wynk.consumerservice.service.WcfCachingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.wynk.consumerservice.constants.MusicConstants.X_BSY_ATKN_KEY;
import static com.wynk.consumerservice.constants.MusicConstants.X_BSY_DATE_KEY;

@Slf4j
@Service
public class WcfUtils {

    @Autowired
    private WcfCachingService wcfCachingService;

    public static Map<String, Object> getFupResetParameterMap(Long lastFUPResetDate){
        Map<String, Object> paramValues = new HashMap<>();
        paramValues.put("packs.FUPPack."+ FupPack.rentalsCount, 0);
        paramValues.put("packs.FUPPack."+ FupPack.streamedCount, 0);
        paramValues.put("packs.FUPPack."+ FupPack.shownFUPWarning, false);
        paramValues.put("packs.FUPPack."+ FupPack.shownFUP95Warning, false);
        paramValues.put("packs.FUPPack."+ FupPack.lastFUPResetDate, lastFUPResetDate);
        return paramValues;
    }

    public static HttpHeaders getHeaders(String httpMethod , String requestUri, String requestBody, String wcfApiAppId, String wcfApiSecretKey) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String timestamp = String.valueOf(System.currentTimeMillis());
        requestBody = StringUtils.isBlank(requestBody) ? "" : requestBody;
        String signature = createSignature(httpMethod,requestUri,requestBody,timestamp, wcfApiSecretKey);
        httpHeaders.add(X_BSY_DATE_KEY,timestamp);
        httpHeaders.add(X_BSY_ATKN_KEY, wcfApiAppId.concat(":").concat(signature));
        httpHeaders.add("Content-Type", "application/json");
        return httpHeaders;
    }

    public static String createSignature(String httpMethod , String requestUri, String requestBody, String timestamp, String secretKey){
        String digestString = new StringBuilder(httpMethod).append(requestUri).append(requestBody).append(timestamp).toString();
        String computedSignature ="";
        try {
            computedSignature = EncryptUtils.calculateRFC2104HMAC(digestString, secretKey);
        }catch (Throwable th){
            log.error("exception occuured in calculating signature for the requestBody {}",requestBody);
        }
        return computedSignature;
    }

    public NewWCFSubscription getSubsObjFromSubsStatus(List<SubscriptionStatus> subscriptionStatusList) throws Exception {
        NewWCFSubscription subs = new NewWCFSubscription();
        subs.setProdIds(new ArrayList<>());
        subs.setOfferTS(System.currentTimeMillis());
        for (SubscriptionStatus status : subscriptionStatusList) {
            int planId = status.getPlanId();
            int prodId = status.getProductId();
            PlanDTO planData = wcfCachingService.getPlan(planId);
            if (planData == null) {
                throw new Exception("planId " + planId + " does not exist");
            }
            int offerId = planData.getLinkedOfferId();
            long ets = status.getValidity();
            subs.getProdIds().add(Product.builder().prodId(prodId).offerId(offerId).planId(planId).ets(ets).build());
        }
        return subs;
    }

    public NewWCFSubscription getUpdatedSubscriptionObject(User user, SubscriptionEvent subscriptionEvent) {

        Integer planId = subscriptionEvent.getPlanId();
        String event = subscriptionEvent.getEvent();
        Long expireTS = subscriptionEvent.getValidTillDate();
        if (planId == null || event == null || expireTS == null) {
            throw new WynkRuntimeException("required parameters not available in callback payload");
        }

        NewWCFSubscription wcfSubscription = user.getNewSubscription();
        if (ObjectUtils.isEmpty(wcfSubscription)) {
            wcfSubscription = new NewWCFSubscription();
        }
        Long currentTime = System.currentTimeMillis();
        Long diff = currentTime - expireTS;
        if (diff < 0 && event.equalsIgnoreCase("UNSUBSCRIBE")) {
            log.info("UNSUBSCRIBE event for products expiring in future, nothing to update");
            return wcfSubscription;
        }

        PlanDTO planData = wcfCachingService.getPlan(planId);
        if (planData == null) {
            wcfCachingService.loadAllPlans();
        }
        planData = wcfCachingService.getPlan(planId);
        if (planData == null) {
            throw new WynkRuntimeException("planId " + planId + " does not exist");
        }
        Integer offerId = planData.getLinkedOfferId();
        Offer offerData = wcfCachingService.getOffer(offerId);
        if (offerData == null) {
            wcfCachingService.loadAllOffers();
        }
        offerData = wcfCachingService.getOffer(offerId);
        if (offerData == null) {
            throw new WynkRuntimeException("offerId" + offerId + " linked to planId " + planId + " does not exist");
        }

        HashSet<Integer> newProductIds = new HashSet<>();
        for (Map.Entry product : offerData.getProducts().entrySet()) {
            newProductIds.add(Integer.parseInt(product.getKey().toString()));
        }

        List<Product> updatedProductsList = wcfSubscription.getProdIds().stream()
                .filter(e -> !newProductIds.contains(e.getProdId())).collect(Collectors.toList());

        if (!event.equalsIgnoreCase("UNSUBSCRIBE")) {
            for (Integer prodId : newProductIds) {
                updatedProductsList.add(Product.builder().prodId(prodId).offerId(offerId).planId(planId).ets(expireTS).build());
            }
        }
        wcfSubscription.setProdIds(updatedProductsList);
        wcfSubscription.setOfferTS(0L);
        return wcfSubscription;
    }

}
