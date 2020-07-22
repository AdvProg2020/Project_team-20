package server.network.security;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ActivityDensity {
    ArrayList<LocalDateTime> allActivities = new ArrayList<>();

    public void addActivity() {
        allActivities.add(LocalDateTime.now());
    }

    public int getActivityDensity() {
        int activityDensity=0;
        ArrayList<LocalDateTime> updatedActivities = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        now = now.minusSeconds(10);
        for (LocalDateTime activityTime:allActivities) {
            if  (activityTime.isAfter(now)) {
                updatedActivities.add(activityTime);
                activityDensity++;
            }
        }
        allActivities = updatedActivities;
        return activityDensity;
    }
}
