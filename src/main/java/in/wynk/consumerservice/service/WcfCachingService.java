package in.wynk.consumerservice.service;

import in.wynk.consumerservice.dto.AllOffersResponse;
import in.wynk.consumerservice.dto.AllPlansResponse;
import in.wynk.consumerservice.dto.Offer;
import in.wynk.consumerservice.dto.PlanDTO;
import in.wynk.consumerservice.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WcfCachingService {

    @Autowired
    private WcfApiService wcfApiService;

    private static Map<Integer, Product> allProducts = new ConcurrentHashMap<>();

    private static Map<Integer, Offer> allOffers = new ConcurrentHashMap<>();

    private static Map<Integer, PlanDTO> allPlans = new ConcurrentHashMap<>();

    @PostConstruct
    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void processWcfCache() {
        //loadALlProducts();
        loadAllOffers();
        loadAllPlans();
    }

    public void loadAllPlans() {
        AllPlansResponse response = wcfApiService.getAllPlansResponse();
        if(Objects.nonNull(response) && Objects.nonNull(response.getPlans())) {
            Map<Integer, PlanDTO> map = new ConcurrentHashMap<>();
            map = response.getPlans().stream().collect(Collectors.toMap(PlanDTO::getId, Function.identity()));
            allPlans = map;
        }
    }

    public void loadAllOffers() {
        AllOffersResponse response = wcfApiService.getAllOffersResponse();
        if(Objects.nonNull(response) && Objects.nonNull(response.getAllOffers())) {
            Map<Integer, Offer> map = new ConcurrentHashMap<>();
            map = response.getAllOffers().stream().collect(Collectors.toMap(Offer::getId, Function.identity()));
            allOffers = map;
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
        return allPlans.get(planID);
    }

    public Offer getOffer(Integer offerId) {
        return allOffers.get(offerId);
    }


}
