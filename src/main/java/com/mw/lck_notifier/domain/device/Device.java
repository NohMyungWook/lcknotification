package com.mw.lck_notifier.domain.device;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
        name = "device",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_device_push_token", columnNames = "push_token")
        }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "push_token", nullable = false, length = 255)
    private String pushToken;

    @Column(name = "platform", nullable = false, length = 20)
    private String platform;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected Device() {
        // JPA only
    }

    private Device(String pushToken, String platform) {
        this.pushToken = pushToken;
        this.platform = platform;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    /**
     * 최초 디바이스 등록
     */
    public static Device register(String pushToken, String platform) {
        return new Device(pushToken, platform);
    }

    /**
     * push token 갱신 (앱 재설치 등)
     */
    public void updatePushToken(String newPushToken) {
        this.pushToken = newPushToken;
        this.updatedAt = LocalDateTime.now();
    }
}
