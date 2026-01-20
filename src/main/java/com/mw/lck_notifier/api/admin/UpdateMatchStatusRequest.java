package com.mw.lck_notifier.api.admin;

import com.mw.lck_notifier.domain.match.MatchStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateMatchStatusRequest {

    @NotNull
    private MatchStatus status;
}