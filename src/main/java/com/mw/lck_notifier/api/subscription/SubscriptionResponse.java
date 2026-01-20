package com.mw.lck_notifier.api.subscription;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubscriptionResponse {
    private Long teamId;
    private String teamName;
    private String teamDisplayName;
}
