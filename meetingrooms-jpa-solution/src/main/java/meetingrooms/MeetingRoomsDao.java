package meetingrooms;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class MeetingRoomsDao implements MeetingRoomsRepository {

    private EntityManagerFactory factory;

    public MeetingRoomsDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public MeetingRoom save(String name, int width, int length) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        MeetingRoom meetingRoom = new MeetingRoom(name, width, length);
        manager.persist(meetingRoom);
        manager.getTransaction().commit();
        manager.close();
        return meetingRoom;
    }

    @Override
    public List<String> getMeetingroomsOrderedByName() {
        EntityManager manager = factory.createEntityManager();
        List<String> meetingRooms = manager.createQuery("select m.name from MeetingRoom m order by m.name").getResultList();
        manager.close();
        return meetingRooms;
    }

    @Override
    public List<String> getEverySecondMeetingRoom() {
        return null;
    }

    @Override
    public List<MeetingRoom> getMeetingRooms() {
        return null;
    }

    @Override
    public List<MeetingRoom> getExactMeetingRoomByName(String name) {
        return null;
    }

    @Override
    public List<MeetingRoom> getMeetingRoomsByPrefix(String nameOrPrefix) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
