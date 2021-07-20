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

    @Test
    void testGetEverySecondMeetingRoom() {
        meetingRoomsDao.save("egyes tárgyaló", 2, 3);
        meetingRoomsDao.save("kettes tárgyaló", 3,  4);
        meetingRoomsDao.save("hármas tárgyaló", 4, 5);

        List<String> expected = meetingRoomsDao.getEverySecondMeetingRoom();

        assertEquals(2, expected.size());
        assertEquals("kettes tárgyaló", expected.get(1));
    }

    @Test
    void testGetExactMeetingRoomByName() {
        meetingRoomsDao.save("egyes tárgyaló", 2, 3);
        meetingRoomsDao.save("kettes tárgyaló", 3,  4);
        meetingRoomsDao.save("hármas tárgyaló", 4, 5);

        MeetingRoom expected = meetingRoomsDao.getExactMeetingRoomByName("kettes tárgyaló");

        assertEquals("kettes tárgyaló", expected.getName());
        assertEquals(3, expected.getWidth());
        assertEquals(4, expected.getLength());
    }

    @Test
    void testGetMeetingRoomsByPrefix() {
        meetingRoomsDao.save("egyes tárgyaló", 2, 3);
        meetingRoomsDao.save("kettes tárgyaló", 3, 4);
        meetingRoomsDao.save("hármas tárgyaló", 4, 5);

        List<MeetingRoom> expected = meetingRoomsDao.getMeetingRoomsByPrefix("ket");

        assertEquals("kettes tárgyaló", expected.get(0).getName());
        assertEquals(3, expected.get(0).getWidth());
        assertEquals(4, expected.get(0).getLength());
    }

    @Test
    void testDeleteAll() {
        meetingRoomsDao.save("egyes tárgyaló", 2, 3);
        meetingRoomsDao.save("kettes tárgyaló", 3,  4);
        meetingRoomsDao.save("hármas tárgyaló", 4, 5);

        meetingRoomsDao.deleteAll();

        List<MeetingRoom> expected = meetingRoomsDao.getMeetingRooms();

        assertEquals(0, expected.size());
    }
}
