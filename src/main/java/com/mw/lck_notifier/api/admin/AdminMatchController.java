package com.mw.lck_notifier.api.admin;

import com.mw.lck_notifier.application.match.FinishMatchUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/matches")
public class AdminMatchController {

    private final FinishMatchUseCase finishMatchUseCase;

    public AdminMatchController(FinishMatchUseCase finishMatchUseCase) {
        this.finishMatchUseCase = finishMatchUseCase;
    }

    @PatchMapping("/{matchId}/finish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finish(@PathVariable Long matchId) {
        finishMatchUseCase.finish(matchId);
    }
}