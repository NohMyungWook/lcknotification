package com.mw.lck_notifier.application.match.utils;

import com.mw.lck_notifier.api.match.TeamSummary;
import com.mw.lck_notifier.api.match.TodayMatchResponse;
import com.mw.lck_notifier.domain.match.Match;

public class MatchMapper {

    public static TodayMatchResponse toResponse(Match match) {
        return new TodayMatchResponse(
                match.getId(),
                match.getStartTime(),
                match.getStatus(),
                new TeamSummary(
                        match.getTeamA().getId(),
                        match.getTeamA().getName(),
                        match.getTeamA().getDisplayName()
                ),
                new TeamSummary(
                        match.getTeamB().getId(),
                        match.getTeamB().getName(),
                        match.getTeamB().getDisplayName()
                ),
                match.getMatchOrder()
        );
    }
}
