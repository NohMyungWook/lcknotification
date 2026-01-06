package com.mw.lck_notifier.api.team;

import com.mw.lck_notifier.domain.team.Team;
import com.mw.lck_notifier.domain.team.TeamRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public TeamListResponse getActiveTeams() {

        List<TeamResponse> teams = teamRepository.findAllByActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();

        return new TeamListResponse(teams);
    }

    private TeamResponse toResponse(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getDisplayName()
        );
    }
}
