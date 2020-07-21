package server.network.security;

import java.time.LocalDateTime;

public class DangerousIp {
    private LocalDateTime timeWentDangerous;
    private String ip;

    public DangerousIp(String ip) {
        this.timeWentDangerous = LocalDateTime.now();
        this.ip = ip;
    }

    public boolean isStillDangerous() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime after = timeWentDangerous.plusMinutes(3);
        return !now.isAfter(after);
    }

    public LocalDateTime getTimeWentBlack() {
        return timeWentDangerous;
    }

    public String getIp() {
        return ip;
    }
}
