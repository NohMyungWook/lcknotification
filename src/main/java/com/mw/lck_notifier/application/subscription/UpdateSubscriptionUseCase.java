package com.mw.lck_notifier.application.subscription;

import com.mw.lck_notifier.domain.device.Device;
import com.mw.lck_notifier.domain.device.DeviceRepository;
import com.mw.lck_notifier.domain.subscription.Subscription;
import com.mw.lck_notifier.domain.subscription.SubscriptionRepository;
import com.mw.lck_notifier.domain.team.Team;
import com.mw.lck_notifier.domain.team.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateSubscriptionUseCase {

    private final DeviceRepository deviceRepository;
    private final TeamRepository teamRepository;
    private final SubscriptionRepository subscriptionRepository;

    public UpdateSubscriptionUseCase(
            DeviceRepository deviceRepository,
            TeamRepository teamRepository,
            SubscriptionRepository subscriptionRepository
    ) {
        this.deviceRepository = deviceRepository;
        this.teamRepository = teamRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional
    public void update(Long deviceId, Long teamId, boolean enabled) {

        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found"));

        subscriptionRepository.findByDeviceAndTeam(device, team)
                .ifPresentOrElse(
                        subscription -> {
                            if (enabled) {
                                subscription.enable();
                            } else {
                                subscription.disable();
                            }
                        },
                        () -> {
                            if (enabled) {
                                Subscription subscription =
                                        Subscription.subscribe(device, team);
                                subscriptionRepository.save(subscription);
                            }
                        }
                );
    }
}
