package com.mw.lck_notifier.domain.notification;

import com.mw.lck_notifier.domain.match.Match;
import com.mw.lck_notifier.domain.device.Device;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "notification_log",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_notification_once",
                        columnNames = {"device_id", "match_id", "type"}
                )
        }
)
@Getter
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne(optional = false)
    @JoinColumn(name = "match_id")
    private Match match;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    protected NotificationLog() {
    }

    private NotificationLog(Device device, Match match, NotificationType type, LocalDateTime sentAt) {
        this.device = device;
        this.match = match;
        this.type = type;
        this.sentAt = sentAt;
    }

    public static NotificationLog matchStart(Device device, Match match) {
        return new NotificationLog(
                device,
                match,
                NotificationType.MATCH_START,
                LocalDateTime.now()
        );
    }
}
