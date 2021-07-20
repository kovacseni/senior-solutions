package meetingrooms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeetingRoomsDaoTest {

    MeetingRoomsDao meetingRoomsDao;

    @BeforeEach
    void init() throws SQLException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        meetingRoomsDao = new MeetingRoomsDao(factory);
    }

    @Test
    void saveAndThanList() {
        meetingRoomsDao.save("egyes tárgyaló", 2, 3);
        meetingRoomsDao.save("kettes tárgyaló", 3,  4);
        meetingRoomsDao.save("hármas tárgyaló", 4, 5);

        List<String> expected = meetingRoomsDao.getMeetingroomsOrderedByName();

        assertEquals(3, expected.size());
        assertEquals("hármas tárgyaló", expected.get(1));
    }
}
