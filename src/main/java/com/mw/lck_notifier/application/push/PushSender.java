package com.mw.lck_notifier.application.push;

import com.mw.lck_notifier.domain.device.Device;
import com.mw.lck_notifier.domain.match.Match;

public interface PushSender {
    void sendMatchStart(Device device, Match match);
}
