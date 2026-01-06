package com.mw.lck_notifier.api.subscription;

import com.mw.lck_notifier.application.subscription.UpdateSubscriptionUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final UpdateSubscriptionUseCase updateSubscriptionUseCase;

    public SubscriptionController(UpdateSubscriptionUseCase updateSubscriptionUseCase) {
        this.updateSubscriptionUseCase = updateSubscriptionUseCase;
    }

    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.NO_CONTENT)
    public void updateSubscription(
            @RequestBody @Valid UpdateSubscriptionRequest request
    ) {
        updateSubscriptionUseCase.update(
                request.getDeviceId(),
                request.getTeamId(),
                request.getEnabled()
        );
    }
}
