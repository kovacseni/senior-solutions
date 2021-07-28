package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ActivityInheritanceDao {

    private EntityManagerFactory factory;

    public ActivityInheritanceDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void saveActivity(ActivityInheritance activity) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(activity);
        manager.getTransaction().commit();
        manager.close();
    }

    public ActivityInheritance findActivityByDescription(String description) {
        EntityManager manager = factory.createEntityManager();
        ActivityInheritance activity = manager
                .createQuery("select a from ActivityInheritance a where a.description like :description", ActivityInheritance.class)
                .setParameter("description", "%" + description + "%")
                .getSingleResult();
        manager.close();
        return activity;
    }
}
