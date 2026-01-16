package com.mw.lck_notifier.api.match;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamSummary {
    private Long id;
    private String name;
    private String displayName;
}
