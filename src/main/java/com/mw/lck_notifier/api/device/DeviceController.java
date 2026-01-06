package com.mw.lck_notifier.api.device;

import com.mw.lck_notifier.application.device.RegisterDeviceUseCase;
import com.mw.lck_notifier.domain.device.Device;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final RegisterDeviceUseCase registerDeviceUseCase;

    public DeviceController(RegisterDeviceUseCase registerDeviceUseCase) {
        this.registerDeviceUseCase = registerDeviceUseCase;
    }

    @PostMapping
    public RegisterDeviceResponse register(
            @RequestBody @Valid RegisterDeviceRequest request
    ) {
        Device device = registerDeviceUseCase.register(
                request.getPushToken(),
                request.getPlatform()
        );

        return new RegisterDeviceResponse(device.getId());
    }
}
