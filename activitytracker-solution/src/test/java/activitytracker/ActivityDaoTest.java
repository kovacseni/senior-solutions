package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Arrays;
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

 /*   @Test
    void testPhoneNumbers() {
        PhoneNumber phoneNumberWork = new PhoneNumber("work", "4321");
        PhoneNumber phoneNumberHome = new PhoneNumber("home", "1234");

        Employee employee = new Employee("John Doe");
        employee.addPhoneNumber(phoneNumberWork);
        employee.addPhoneNumber(phoneNumberHome);
        employeeDao.saveEmployee(employee);

        Employee anotherEmployee = employeeDao.findEmployeeByIdWithPhoneNumbers(employee.getId());
        assertEquals(2, anotherEmployee.getPhoneNumbers().size());
        assertEquals("work", anotherEmployee.getPhoneNumbers().get(0).getType());
    }*/

}
