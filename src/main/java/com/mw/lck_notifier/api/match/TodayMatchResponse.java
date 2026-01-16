package com.mw.lck_notifier.api.match;

import com.mw.lck_notifier.domain.match.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodayMatchResponse {
    private Long matchId;
    private LocalDateTime startTime;
    private MatchStatus status;
    private TeamSummary teamA;
    private TeamSummary teamB;
    private Integer matchOrder;
}
