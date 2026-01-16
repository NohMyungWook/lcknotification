package com.mw.lck_notifier.infra.push;

import com.mw.lck_notifier.application.push.PushSender;
import com.mw.lck_notifier.domain.device.Device;
import com.mw.lck_notifier.domain.match.Match;
import org.springframework.stereotype.Component;

@Component
public class ConsolePushSender implements PushSender {

    @Override
    public void sendMatchStart(Device device, Match match) {
        System.out.println(
                "[PUSH][MATCH_START] " +
                        device.getPushToken() + " -> " +
                        match.getTeamA().getDisplayName() + " vs " +
                        match.getTeamB().getDisplayName()
        );
    }
}
