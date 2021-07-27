package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AreaDaoTest {

    ActivityDao activityDao;

    AreaDao areaDao;

    @BeforeEach
    void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        activityDao = new ActivityDao(factory);
        areaDao = new AreaDao(factory);
    }

    @Test
    void testSaveArea() {
        Activity activity1 = new Activity(LocalDateTime.of(2021, 7, 13, 14, 55),
                "gyors kör a tó körül", ActivityType.RUNNING);
        Activity activity2 = new Activity(LocalDateTime.of(2021, 7, 17, 6, 0),
                "hajnali bicózás az erdőben", ActivityType.BIKING);
        Activity activity3 = new Activity(LocalDateTime.of(2018, 7, 15, 19, 15),
                "esti levezetés", ActivityType.RUNNING);

        activityDao.saveActivity(activity1);
        activityDao.saveActivity(activity2);
        activityDao.saveActivity(activity3);

        Area area1 = new Area("Kiskunság");
        Area area2 = new Area("Hortobágy");
        Area area3 = new Area("Északi Középhegység");

        area1.addActivity(activity1);
        area1.addActivity(activity2);

        area2.addActivity(activity2);

        area3.addActivity(activity1);
        area3.addActivity(activity2);
        area3.addActivity(activity3);

        areaDao.saveArea(area1);
        areaDao.saveArea(area2);
        areaDao.saveArea(area3);

        Area expected = areaDao.findAreaByName("Kiskunság");
        List<String> descriptions = List.of("gyors kör a tó körül", "hajnali bicózás az erdőben");
        List<String> descriptionsFound = expected.getActivities().stream().map(Activity::getDescription).collect(Collectors.toList());

        assertEquals(descriptions, descriptionsFound);
    }

    @Test
    void testSaveThanFind() {
        Area area = new Area("Kiskunság");
        area.getCities().put("Kecskemét", new City("Kecskemét", 110_687));
        area.getCities().put("Soltvadkert", new City("Soltvadkert", 7342));

        areaDao.saveArea(area);
        long id = area.getId();

        Area expected = areaDao.findByName("Kiskunság");

        assertEquals(110_687, expected.getCities().get("Kecskemét").getPopulation());
    }
}
