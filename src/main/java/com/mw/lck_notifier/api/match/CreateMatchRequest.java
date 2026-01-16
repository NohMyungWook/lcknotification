package com.mw.lck_notifier.api.match;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateMatchRequest {

    @NotNull
    private LocalDateTime startTime;   // KST, full datetime

    @NotNull
    private Long teamAId;

    @NotNull
    private Long teamBId;

    @NotNull
    private Integer order;             // matchOrder: 1 or 2

    @NotNull
    private Integer bestOf;            // 3 or 5
}
