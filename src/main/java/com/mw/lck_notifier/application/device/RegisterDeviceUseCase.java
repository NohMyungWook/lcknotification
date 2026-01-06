package com.mw.lck_notifier.application.device;

import com.mw.lck_notifier.domain.device.Device;
import com.mw.lck_notifier.domain.device.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterDeviceUseCase {

    private final DeviceRepository deviceRepository;

    public RegisterDeviceUseCase(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public Device register(String pushToken, String platform) {

        return deviceRepository.findByPushToken(pushToken)
                .orElseGet(() -> {
                    // 최초 등록
                    Device device = Device.register(pushToken, platform);
                    return deviceRepository.save(device);
                });
    }
}
