package com.mw.lck_notifier.application.subscription;

import com.mw.lck_notifier.api.subscription.SubscriptionResponse;
import com.mw.lck_notifier.domain.subscription.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSubscriptionsUseCase {

    private final SubscriptionRepository subscriptionRepository;

    public GetSubscriptionsUseCase(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<SubscriptionResponse> get(Long deviceId) {
        return subscriptionRepository.findActiveByDevice(deviceId)
                .stream()
                .map(s -> new SubscriptionResponse(
                        s.getTeam().getId(),
                        s.getTeam().getName(),
                        s.getTeam().getDisplayName()
                ))
                .toList();
    }
}
