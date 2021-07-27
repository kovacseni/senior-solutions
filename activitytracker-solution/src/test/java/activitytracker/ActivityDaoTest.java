package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
        Activity activity = new Activity(LocalDateTime.of(2021, 7, 13, 14, 55),
                "gyors kör a tó körül", ActivityType.RUNNING);
        activityDao.saveActivity(activity);
        Activity expected = activityDao.findActivityById(activity.getId());

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

    @Test
    void testUpdateActivity() {
        Activity activity = new Activity(LocalDateTime.of(2021, 7, 13, 14, 55),
                "gyors kör a tó körül", ActivityType.RUNNING);
        activityDao.saveActivity(activity);
        Activity expected = activityDao.findActivityById(activity.getId());

        assertEquals("gyors kör a tó körül", expected.getDescription());

        activityDao.updateActivity(activity.getId(), "Futáááás!");
        Activity modified = activityDao.findActivityById(activity.getId());

        assertEquals("Futáááás!", modified.getDescription());
    }

    @Test
    void testFindActivityByIdWithLabels() {
        Activity activity = new Activity(LocalDateTime.of(2021, 7, 13, 14, 55),
                "gyors kör a tó körül", ActivityType.RUNNING);
        activity.setLabels(Arrays.asList("déli futás", "Szelidi-tó"));
        activityDao.saveActivity(activity);

        Activity expected = activityDao.findActivityByIdWithLabels(activity.getId());

        assertEquals(Arrays.asList("déli futás", "Szelidi-tó"), expected.getLabels());
    }

    @Test
    void testFindActivityByIdWithTrackPoints() {
        Activity activity = new Activity(LocalDateTime.of(2021, 7, 13, 14, 55),
                "gyors kör a tó körül", ActivityType.RUNNING);
        activity.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 2, 3, 4, 5), 47.497912, 19.040235));
        activity.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 4, 5, 6, 7), -33.88223, 151.33140));
        activity.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 3, 4, 5, 6), 48.87376, 2.25120));

        activityDao.saveActivity(activity);

        Activity expected = activityDao.findActivityByIdWithTrackPoints(activity.getId());

        assertEquals(3, expected.getTrackPoints().size());
        assertEquals(-33.88223, expected.getTrackPoints().get(2).getLat());
        assertEquals(2.25120, expected.getTrackPoints().get(1).getLon());
    }

    @Test
    void testFindTrackPointCoordinatesByDate() {
        Activity activity1 = new Activity(LocalDateTime.of(2021, 7, 13, 14, 55),
                "gyors kör a tó körül", ActivityType.RUNNING);
        activity1.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 2, 3, 4, 5), 47.497912, 19.040235));
        activity1.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 4, 5, 6, 7), -33.88223, 151.33140));
        activity1.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 3, 4, 5, 6), 48.87376, 2.25120));

        activityDao.saveActivity(activity1);

        Activity activity2 = new Activity(LocalDateTime.of(2021, 7, 17, 6, 0),
                "hajnali bicózás az erdőben", ActivityType.BIKING);
        activity2.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 2, 3, 4, 5), 47.497912, 19.040235));
        activity2.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 4, 5, 6, 7), -33.88223, 151.33140));
        activity2.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 3, 4, 5, 6), 48.87376, 2.25120));

        activityDao.saveActivity(activity2);

        Activity activity3 = new Activity(LocalDateTime.of(2018, 7, 15, 19, 15),
                "esti levezetés", ActivityType.RUNNING);
        activity3.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 2, 3, 4, 5), 47.497912, 19.040235));
        activity3.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 4, 5, 6, 7), -33.88223, 151.33140));
        activity3.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 3, 4, 5, 6), 48.87376, 2.25120));

        activityDao.saveActivity(activity3);

        List<Coordinate> expected = activityDao.findTrackPointCoordinatesByDate(LocalDateTime.of(2019, 6, 7, 8, 9), 1, 20);

        assertEquals(3, expected.size());
    }

    @Test
    void testFindTrackPointCountByActivity() {
        Activity activity1 = new Activity(LocalDateTime.of(2021, 7, 13, 14, 55),
                "gyors kör a tó körül", ActivityType.RUNNING);
        activity1.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 2, 3, 4, 5), 47.497912, 19.040235));
        activity1.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 4, 5, 6, 7), -33.88223, 151.33140));

        activityDao.saveActivity(activity1);

        Activity activity2 = new Activity(LocalDateTime.of(2021, 7, 17, 6, 0),
                "hajnali bicózás az erdőben", ActivityType.BIKING);
        activity2.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 2, 3, 4, 5), 47.497912, 19.040235));

        activityDao.saveActivity(activity2);

        Activity activity3 = new Activity(LocalDateTime.of(2021, 7, 15, 19, 15),
                "esti levezetés", ActivityType.RUNNING);
        activity3.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 2, 3, 4, 5), 47.497912, 19.040235));
        activity3.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 4, 5, 6, 7), -33.88223, 151.33140));
        activity3.addTrackPoint(new TrackPoint(LocalDateTime.of(2021, 3, 4, 5, 6), 48.87376, 2.25120));

        activityDao.saveActivity(activity3);

        List<Object[]> expected = activityDao.findTrackPointCountByActivity();

        Object[] dataOfActivity1 = new Object[]{"gyors kör a tó körül", 2};
        Object[] dataOfActivity2 = new Object[]{"hajnali bicózás az erdőben", 1};
        Object[] dataOfActivity3 = new Object[]{"esti levezetés", 3};

        assertEquals(3, expected.size());
        assertArrayEquals(dataOfActivity1, expected.get(0));
        assertArrayEquals(dataOfActivity2, expected.get(2));
        assertArrayEquals(dataOfActivity3, expected.get(1));
    }
}
