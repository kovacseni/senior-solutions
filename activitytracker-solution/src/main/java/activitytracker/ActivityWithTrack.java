package activitytracker;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class ActivityWithTrack extends ActivityInheritance {

    private double distance;

    private long duration;

    public ActivityWithTrack() {
    }

    public ActivityWithTrack(LocalDateTime startTime, String description, double distance, long duration) {
        super(startTime, description);
        this.distance = distance;
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public long getDuration() {
        return duration;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
