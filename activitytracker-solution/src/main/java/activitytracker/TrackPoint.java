package activitytracker;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TrackPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tp_time")
    private LocalDateTime time;

    private double lat;

    private double lon;

    @ManyToOne
    private Activity activity;

    public TrackPoint() {
    }

    public TrackPoint(LocalDateTime time, double lat, double lon) {
        this.time = time;
        this.lat = lat;
        this.lon = lon;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
