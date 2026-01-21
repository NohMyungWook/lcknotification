package com.mw.lck_notifier.api.device;

import com.mw.lck_notifier.api.match.TodayMatchResponse;
import com.mw.lck_notifier.application.match.GetSubscribedTodayMatchesUseCase;
import com.mw.lck_notifier.application.match.GetSubscribedUpcomingMatchesUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices/{deviceId}/matches")
public class DeviceMatchController {

    private final GetSubscribedTodayMatchesUseCase getTodayMatchesUseCase;
    private final GetSubscribedUpcomingMatchesUseCase getUpcomingMatchesUseCase;

    public DeviceMatchController(
            GetSubscribedTodayMatchesUseCase getTodayMatchesUseCase,
            GetSubscribedUpcomingMatchesUseCase getUpcomingMatchesUseCase
    ) {
        this.getTodayMatchesUseCase = getTodayMatchesUseCase;
        this.getUpcomingMatchesUseCase = getUpcomingMatchesUseCase;
    }

    @GetMapping("/today")
    public List<TodayMatchResponse> today(@PathVariable Long deviceId) {
        return getTodayMatchesUseCase.get(deviceId);
    }

    @GetMapping("/upcoming")
    public List<TodayMatchResponse> upcoming(@PathVariable Long deviceId) {
        return getUpcomingMatchesUseCase.get(deviceId);
    }
}
