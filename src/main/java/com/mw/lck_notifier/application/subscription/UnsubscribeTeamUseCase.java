package com.mw.lck_notifier.application.subscription;

import com.mw.lck_notifier.domain.subscription.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnsubscribeTeamUseCase {

    private final SubscriptionRepository subscriptionRepository;

    public UnsubscribeTeamUseCase(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional
    public void unsubscribe(Long deviceId, Long teamId) {
        int updated = subscriptionRepository.disableSubscription(deviceId, teamId);

        if (updated == 0) {
            throw new IllegalArgumentException("Subscription not found or already disabled");
        }
    }
}
