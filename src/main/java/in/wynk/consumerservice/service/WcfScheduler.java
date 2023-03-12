package in.wynk.consumerservice.service;

import in.wynk.consumerservice.dto.PlanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class WcfScheduler {

    @Autowired
    private WcfCachingService wcfCachingService;

    @PostConstruct
    //@Scheduled(fixedDelay = 1000 * 60 * 10)
    public void processWcfCache() {
        wcfCachingService.evictCache();
        wcfCachingService.loadAllOffers();
        wcfCachingService.loadAllPlans();
    }
}
