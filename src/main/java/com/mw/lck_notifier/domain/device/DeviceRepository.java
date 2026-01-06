package com.mw.lck_notifier.domain.device;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByPushToken(String pushToken);

    boolean existsByPushToken(String pushToken);
}
