package activitytracker;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "cities",
        joinColumns = @JoinColumn(name = "area_id"),
        inverseJoinColumns = @JoinColumn(name = "city_id"))
    @MapKey(name = "name")
    private Map<String, City> cities = new HashMap<>();

    public Area() {
    }

    public Area(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public Map<String, City> getCities() {
        return cities;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public void setCities(Map<String, City> cities) {
        this.cities = cities;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
        activity.getAreas().add(this);
    }
}
