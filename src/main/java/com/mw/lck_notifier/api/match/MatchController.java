package com.mw.lck_notifier.api.match;

import com.mw.lck_notifier.application.match.CreateMatchUseCase;
import com.mw.lck_notifier.application.match.GetFinishedMatchesUseCase;
import com.mw.lck_notifier.application.match.GetLiveMatchesUseCase;
import com.mw.lck_notifier.application.match.GetTodayMatchesUseCase;
import com.mw.lck_notifier.application.match.GetUpcomingMatchesUseCase;
import com.mw.lck_notifier.domain.match.Match;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
@AllArgsConstructor
public class MatchController {

    private final CreateMatchUseCase createMatchUseCase;
    private final GetTodayMatchesUseCase getTodayMatchesUseCase;
    private final GetLiveMatchesUseCase getLiveMatchesUseCase;
    private final GetUpcomingMatchesUseCase getUpcomingMatchesUseCase;
    private final GetFinishedMatchesUseCase getFinishedMatchesUseCase;

    @PostMapping
    public MatchIdResponse create(
            @RequestBody @Valid CreateMatchRequest request
    ) {
        Match match = createMatchUseCase.create(
                request.getStartTime(),
                request.getTeamAId(),
                request.getTeamBId(),
                request.getOrder(),
                request.getBestOf()
        );

        return new MatchIdResponse(match.getId());
    }

    @GetMapping("/today")
    public List<TodayMatchResponse> getTodayMatches() {
        return getTodayMatchesUseCase.getToday();
    }

    @GetMapping("/upcoming")
    public List<TodayMatchResponse> getUpcoming() {
        return getUpcomingMatchesUseCase.getUpcoming();
    }

    @GetMapping("/live")
    public List<TodayMatchResponse> getLive() {
        return getLiveMatchesUseCase.getLive();
    }

    @GetMapping("/result")
    public List<TodayMatchResponse> getFinished() {
        return getFinishedMatchesUseCase.getFinished();
    }

}
