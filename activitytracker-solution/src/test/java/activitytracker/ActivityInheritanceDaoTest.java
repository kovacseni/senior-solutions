package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivityInheritanceDaoTest {

    ActivityInheritanceDao dao;

    @BeforeEach
    void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        dao = new ActivityInheritanceDao(factory);
    }

    @Test
    void testSaveAndFind() {
        dao.saveActivity(new ActivityInheritance(LocalDateTime.of(2021, 7, 13, 20, 55),
                "esti levezetés"));
        dao.saveActivity(new SimpleActivity(LocalDateTime.of(2021, 7, 15, 14, 55),
                "gyors kör a tó körül", "Szelidi tó"));
        dao.saveActivity(new ActivityWithTrack(LocalDateTime.of(2021, 7, 17, 5, 55),
                "hajnali bicózás az erdőben", 12.3, 3000));

        ActivityInheritance activity = dao.findActivityByDescription("esti");
        assertEquals(LocalDateTime.of(2021, 7, 13, 20, 55), activity.getStartTime());

        ActivityInheritance simple = dao.findActivityByDescription("tó");
        assertEquals("Szelidi tó", ((SimpleActivity)simple).getPlace());

        ActivityInheritance track = dao.findActivityByDescription("az erdőben");
        assertEquals(3000, ((ActivityWithTrack)track).getDuration());
    }
}
