package com.mw.lck_notifier.api.subscription;

import com.mw.lck_notifier.application.subscription.GetSubscriptionsUseCase;
import com.mw.lck_notifier.application.subscription.UnsubscribeTeamUseCase;
import com.mw.lck_notifier.application.subscription.UpdateSubscriptionUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices/{deviceId}/subscriptions")
public class SubscriptionController {

    private final UpdateSubscriptionUseCase updateSubscriptionUseCase;
    private final GetSubscriptionsUseCase getSubscriptionsUseCase;
    private final UnsubscribeTeamUseCase unsubscribeTeamUseCase;

    public SubscriptionController(
            UpdateSubscriptionUseCase updateSubscriptionUseCase,
            GetSubscriptionsUseCase getSubscriptionsUseCase,
            UnsubscribeTeamUseCase unsubscribeTeamUseCase
    ) {
        this.updateSubscriptionUseCase = updateSubscriptionUseCase;
        this.getSubscriptionsUseCase = getSubscriptionsUseCase;
        this.unsubscribeTeamUseCase = unsubscribeTeamUseCase;
    }

    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.NO_CONTENT)
    public void updateSubscription(
            @PathVariable Long deviceId,
            @RequestBody @Valid UpdateSubscriptionRequest request
    ) {
        updateSubscriptionUseCase.update(
                deviceId,
                request.getTeamId(),
                request.getEnabled()
        );
    }

    @GetMapping
    public List<SubscriptionResponse> list(@PathVariable Long deviceId) {
        return getSubscriptionsUseCase.get(deviceId);
    }

    @DeleteMapping("/{teamId}")
    public void unsubscribe(
            @PathVariable Long deviceId,
            @PathVariable Long teamId
    ) {
        unsubscribeTeamUseCase.unsubscribe(deviceId, teamId);
    }
}
