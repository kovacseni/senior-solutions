package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
}
