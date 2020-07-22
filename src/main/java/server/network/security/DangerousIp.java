package server.network.security;

import java.time.LocalDateTime;

public class DangerousIp {
    private LocalDateTime timeWentDangerous;
    private DangerousIpType dangerousIpType;
    private String ip;

    public DangerousIp(String ip, DangerousIpType dangerousIpType) {
        this.timeWentDangerous = LocalDateTime.now();
        this.ip = ip;
    }

    public boolean isStillDangerous() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime after;
        if (dangerousIpType.equals(DangerousIpType.LOGIN_DANGER))
            after = timeWentDangerous.plusMinutes(3);
        else
            after = timeWentDangerous.plusMinutes(300);
        return !now.isAfter(after);
    }

    public LocalDateTime getTimeWentBlack() {
        return timeWentDangerous;
    }

    public String getIp() {
        return ip;
    }
}
