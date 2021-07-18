package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class ActivityTrackerMain {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        EntityManager manager = factory.createEntityManager();
        insert(manager);
    }

    private static void insert(EntityManager manager) {
        manager.getTransaction().begin();

        manager.persist(new Activity(LocalDateTime.of(2021, 7, 13, 14, 55),
                "gyors kör a tó körül", ActivityType.RUNNING));
        manager.persist(new Activity(LocalDateTime.of(2021, 7, 15, 19, 15),
                "esti levezetés", ActivityType.RUNNING));
        manager.persist(new Activity(LocalDateTime.of(2021, 7, 17, 6, 0),
                "hajnali bicózás az erdőben", ActivityType.BIKING));

        manager.getTransaction().commit();
    }
}
