package in.wynk.consumerservice.handler;

import in.wynk.consumerservice.dto.SubscriptionEvent;
import in.wynk.consumerservice.service.WcfCachingService;
import in.wynk.consumerservice.service.WcfCallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class CallbackHandler {

    @Autowired
    private WcfCallbackService callbackService;

    @Autowired
    private WcfCachingService cahCachingService;

    @PostMapping
    public ResponseEntity<String> handleCallback(@RequestBody SubscriptionEvent subscriptionEvent) {
        try {
            callbackService.handleCallback(subscriptionEvent);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("error");
        }
    }
}
