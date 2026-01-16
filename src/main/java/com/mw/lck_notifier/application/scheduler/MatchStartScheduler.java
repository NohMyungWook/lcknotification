package com.mw.lck_notifier.application.scheduler;

import com.mw.lck_notifier.domain.device.Device;
import com.mw.lck_notifier.domain.device.DeviceRepository;
import com.mw.lck_notifier.domain.match.Match;
import com.mw.lck_notifier.domain.match.MatchRepository;
import com.mw.lck_notifier.domain.match.MatchStatus;
import com.mw.lck_notifier.domain.notification.NotificationLog;
import com.mw.lck_notifier.domain.notification.NotificationLogRepository;
import com.mw.lck_notifier.domain.subscription.SubscriptionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MatchStartScheduler {

    private final MatchRepository matchRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final NotificationLogRepository notificationLogRepository;
    private final DeviceRepository deviceRepository;

    public MatchStartScheduler(
            MatchRepository matchRepository,
            SubscriptionRepository subscriptionRepository,
            NotificationLogRepository notificationLogRepository,
            DeviceRepository deviceRepository
    ) {
        this.matchRepository = matchRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.notificationLogRepository = notificationLogRepository;
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    @Scheduled(fixedDelay = 60000)   // 60초마다
    public void checkMatchStart() {

        // 1. 현재 시간을 UTC로 계산
        LocalDateTime nowUtc = LocalDateTime.now(ZoneOffset.UTC);

        // 2. 시작 시간이 지났고 아직 경기 시작 안 된 매치 조회
        List<Match> matches = matchRepository
                .findByStatusAndStartTimeLessThanEqual(MatchStatus.SCHEDULED, nowUtc);

        for (Match match : matches) {

            // 3. 경기 상태 변경
            match.start();  // 내부에서 this.status = IN_PROGRESS;

            matchRepository.save(match);

            // 4. 구독자 조회 (양 팀 모두)
            Set<Long> deviceIdSet = new HashSet<>();

            List<Long> teamADeviceIds =
                    subscriptionRepository.findDeviceIdsByTeam(match.getTeamA().getId());
            deviceIdSet.addAll(teamADeviceIds);

            List<Long> teamBDeviceIds =
                    subscriptionRepository.findDeviceIdsByTeam(match.getTeamB().getId());
            deviceIdSet.addAll(teamBDeviceIds);  // 중복 자동 제거

            // 5. NotificationLog insert → 중복 방지 (unique(device_id, match_id, type))
            for (Long deviceId : deviceIdSet) {
                Device device = deviceRepository.getReferenceById(deviceId);

                NotificationLog log = NotificationLog.matchStart(device, match);
                notificationLogRepository.save(log);
            }

            // 6. (MVP) 여기까지가 "알림 생성" 단계.
            //    실제 푸시는 나중에 pushService.sendMatchStart(device, match)로 분리 예정.
        }
    }
}
