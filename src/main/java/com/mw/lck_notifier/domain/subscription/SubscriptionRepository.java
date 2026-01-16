package com.mw.lck_notifier.domain.subscription;

import com.mw.lck_notifier.domain.device.Device;
import com.mw.lck_notifier.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findAllByDeviceAndEnabledTrue(Device device);

    @Query("select s.device.id from Subscription s where s.team.id = :teamId and s.enabled = true")
    List<Long> findDeviceIdsByTeam(Long teamId);

    Optional<Subscription> findByDeviceAndTeam(Device device, Team team);

    boolean existsByDeviceAndTeam(Device device, Team team);
}
