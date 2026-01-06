package com.mw.lck_notifier.domain.subscription;

import com.mw.lck_notifier.domain.device.Device;
import com.mw.lck_notifier.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findAllByDeviceAndEnabledTrue(Device device);

    Optional<Subscription> findByDeviceAndTeam(Device device, Team team);

    boolean existsByDeviceAndTeam(Device device, Team team);
}
