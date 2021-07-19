package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivityDaoTest {

    ActivityDao activityDao;

    @BeforeEach
    void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        activityDao = new ActivityDao(factory);
    }

    @Test
    void testSaveThanFind() {
        Activity employee = new Activity(LocalDateTime.of(2021, 7, 13, 14, 55),
                "gyors kör a tó körül", ActivityType.RUNNING);
        activityDao.saveActivity(employee);
        Activity expected = activityDao.findActivityById(employee.getId());

        assertEquals("gyors kör a tó körül", expected.getDescription());
    }

    @Test
    void testSaveThanListAll() {
        activityDao.saveActivity(new Activity(LocalDateTime.of(2021, 7, 13, 14, 55),
                "gyors kör a tó körül", ActivityType.RUNNING));
        activityDao.saveActivity(new Activity(LocalDateTime.of(2021, 7, 17, 6, 0),
                "hajnali bicózás az erdőben", ActivityType.BIKING));
        List<Activity> expected = activityDao.listActivities();

        assertEquals("gyors kör a tó körül", expected.get(0).getDescription());
        assertEquals("hajnali bicózás az erdőben", expected.get(1).getDescription());
    }
}
