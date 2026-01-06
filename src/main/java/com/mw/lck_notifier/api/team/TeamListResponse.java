package com.mw.lck_notifier.api.team;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TeamListResponse {
    private List<TeamResponse> teams;
}
