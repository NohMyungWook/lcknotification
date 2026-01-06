package com.mw.lck_notifier.api.device;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterDeviceRequest {

    @NotBlank
    private String pushToken;

    @NotBlank
    private String platform;
}
