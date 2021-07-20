package meetingrooms;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
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
        EntityManager manager = factory.createEntityManager();
        List<String> meetingRooms = manager.createQuery("select m.name from MeetingRoom m order by m.name").getResultList();
        List<String> everySecond = new ArrayList<>();
        for (int i = 0; i < meetingRooms.size(); i += 2) {
            everySecond.add(meetingRooms.get(i));
        }
        return everySecond;
    }

    @Override
    public List<MeetingRoom> getMeetingRooms() {
        EntityManager manager = factory.createEntityManager();
        List<MeetingRoom> meetingRooms = manager.createQuery("select m from MeetingRoom m").getResultList();
        manager.close();
        return meetingRooms;
    }

    @Override
    public MeetingRoom getExactMeetingRoomByName(String name) {
        EntityManager manager = factory.createEntityManager();
        MeetingRoom meetingRoomWithNameParameter = manager.createQuery("select m from MeetingRoom m where m.name = :name", MeetingRoom.class)
                .setParameter("name", name)
                .getSingleResult();
        manager.close();
        return meetingRoomWithNameParameter;
    }

    @Override
    public List<MeetingRoom> getMeetingRoomsByPrefix(String nameOrPrefix) {
        EntityManager manager = factory.createEntityManager();
        List<MeetingRoom> meetingRoomsWithNameOrPrefix = manager.createQuery("select m from MeetingRoom m where m.name like :name order by m.name", MeetingRoom.class)
                .setParameter("name", nameOrPrefix + "%")
                .getResultList();
        manager.close();
        return meetingRoomsWithNameOrPrefix;
    }

    @Override
    public void deleteAll() {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.createQuery("delete from MeetingRoom").executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }
}
