package com.mw.lck_notifier.api.match;

import com.mw.lck_notifier.application.match.CreateMatchUseCase;
import com.mw.lck_notifier.domain.match.Match;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final CreateMatchUseCase createMatchUseCase;

    public MatchController(CreateMatchUseCase createMatchUseCase) {
        this.createMatchUseCase = createMatchUseCase;
    }

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
}
