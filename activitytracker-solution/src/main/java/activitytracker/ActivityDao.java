package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActivityDao {

    private EntityManagerFactory factory;

    public ActivityDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void saveActivity(Activity activity) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(activity);
        manager.getTransaction().commit();
        manager.close();
    }

    public Activity findActivityById(long id) {
        EntityManager manager = factory.createEntityManager();
        Activity activity = manager.find(Activity.class, id);
        manager.close();
        return activity;
    }

    public List<Activity> listActivities() {
        EntityManager manager = factory.createEntityManager();
        List<Activity> activities = manager.createQuery("select activity from Activity activity order by activity.startTime", Activity.class).getResultList();
        manager.close();
        return activities;
    }

    public void updateActivity(long id, String description) {
        EntityManager manager = factory.createEntityManager();
        Activity activity = manager.find(Activity.class, id);
        manager.getTransaction().begin();
        activity.setDescription(description);
        manager.getTransaction().commit();
        manager.close();
    }

    public Activity findActivityByIdWithLabels(long id) {
        EntityManager manager = factory.createEntityManager();
        Activity activity = manager.createQuery("select a from Activity a join fetch a.labels where a.id = :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        manager.close();
        return activity;
    }

    public Activity findActivityByIdWithTrackPoints(long id) {
        EntityManager manager = factory.createEntityManager();
        Activity activity = manager.createQuery("select a from Activity a join fetch a.trackPoints where a.id = :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        manager.close();
        return activity;
    }

    public List<Coordinate> findTrackPointCoordinatesByDate(LocalDateTime afterThis, int start, int max) {
        EntityManager manager = factory.createEntityManager();
        List<Activity> activities = manager.createNamedQuery("trackPointsAfterDate", Activity.class)
                .setParameter("startTime", afterThis)
                .setFirstResult(start)
                .setMaxResults(max)
                .getResultList();
        manager.close();
        List<Coordinate> coordinates = getCoordinates(activities);
        return coordinates;
    }

    private List<Coordinate> getCoordinates(List<Activity> activities) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (Activity a : activities) {
            for (TrackPoint t : a.getTrackPoints()) {
                coordinates.add(new Coordinate(t.getLat(), t.getLon()));
            }
        }
        return coordinates;
    }

    public List<Object[]> findTrackPointCountByActivity() {
        EntityManager manager = factory.createEntityManager();
        List<Object[]> activities = manager.createQuery("select a.description, count(t) from Activity a join a.trackPoints t group by a.description order by a.startTime", Object[].class)
                .getResultList();
        manager.close();
        return activities;
    }
}
