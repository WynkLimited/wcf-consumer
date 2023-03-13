package in.wynk.consumerservice.service;

import in.wynk.consumerservice.dto.AllOffersResponse;
import in.wynk.consumerservice.dto.AllPlansResponse;
import in.wynk.consumerservice.dto.Offer;
import in.wynk.consumerservice.dto.PlanDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WcfCachingService {

    @Autowired
    private WcfApiService wcfApiService;
    @Caching(evict = {
            @CacheEvict(value = "wcf-cache", key = "wcfAllPlans", allEntries = true),
            @CacheEvict(value = "wcf-cache", key = "wcfAllOffers", allEntries = true),
            @CacheEvict(value = "wcf-cache", key = "wcfAllProducts", allEntries = true),
    })
    public void evictCache() {}

    @Cacheable(value = "wcf-cache", key = "'wcfAllPlans'", cacheManager = "redisCacheManager")
    public Map<Integer, PlanDTO> loadAllPlans() {
        AllPlansResponse response = wcfApiService.getAllPlansResponse();
        if(Objects.nonNull(response) && Objects.nonNull(response.getPlans())) {
            Map<Integer, PlanDTO> map = response.getPlans().stream().collect(Collectors.toMap(PlanDTO::getId, Function.identity()));
            return map;
        } else {
            return Collections.emptyMap();
        }
    }

    @Cacheable(value = "wcf-cache", key = "'wcfAllOffers'", cacheManager = "redisCacheManager")
    public Map<Integer, Offer> loadAllOffers() {

        AllOffersResponse response = wcfApiService.getAllOffersResponse();
        if(Objects.nonNull(response) && Objects.nonNull(response.getAllOffers())) {
            Map<Integer, Offer> map = response.getAllOffers().stream().collect(Collectors.toMap(Offer::getId, Function.identity()));
            return map;
        } else {
            return Collections.emptyMap();
        }
    }

/*    public void loadALlProducts() {
        AllProductsResponse response = wcfApiService.getAllProductsResponse();
        if(Objects.nonNull(response) && Objects.nonNull(response.getAllProducts())) {
            Map<Integer, Offer> map = new ConcurrentHashMap<>();
            map = response.getAllProducts().stream().collect(Collectors.toMap(Product::getId, Function.identity()));
            allOffers = map;
        }
    }*/

    public PlanDTO getPlan(Integer planID) {
        return loadAllPlans().get(planID);
    }

    public Offer getOffer(Integer offerId) {
        return loadAllOffers().get(offerId);
    }


}
