package com.mw.lck_notifier.api.team;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamResponse {
    private Long id;
    private String name;
    private String displayName;
}
