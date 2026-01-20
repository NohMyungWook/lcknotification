package com.mw.lck_notifier.api.subscription;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateSubscriptionRequest {

    @NotNull
    private Long teamId;

    @NotNull
    private Boolean enabled;
}
