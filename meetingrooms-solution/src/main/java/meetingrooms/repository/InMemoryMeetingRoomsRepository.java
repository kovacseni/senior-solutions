package meetingrooms.repository;

import meetingrooms.entity.MeetingRoom;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMeetingRoomsRepository implements MeetingRoomsRepository {

    private List<MeetingRoom> meetingRooms = new ArrayList<>();

    @Override
    public MeetingRoom save(String name, int width, int length) {
        MeetingRoom meetingRoom = new MeetingRoom(name, width, length);
        meetingRooms.add(meetingRoom);
        return meetingRoom;
    }

    @Override
    public List<String> getMeetingroomsOrderedByName() {
        return meetingRooms.stream()
                .map(MeetingRoom::getName)
                .sorted(Collator.getInstance(new Locale("hu", "HU")))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getEverySecondMeetingRoom() {
        return meetingRooms.stream()
                .map(MeetingRoom::getName)
                .sorted(Collator.getInstance(new Locale("hu", "HU")))
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingRoom> getMeetingRooms() {
        return new ArrayList<>(meetingRooms);
    }

    @Override
    public List<MeetingRoom> getExactMeetingRoomByName(String name) {
        return meetingRooms.stream()
                .filter(meetingRoom -> meetingRoom.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingRoom> getMeetingRoomsByPrefix(String prefix) {
        return meetingRooms.stream()
                .filter(meetingroom -> meetingroom.getName().contains(prefix))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        meetingRooms.clear();
    }

    @Override
    public void saveMeetingRoomAndMeetingsToo(MeetingRoom meetingRoom) {}

    @Override
    public List<MeetingRoom> loadMeetingRoomsWithMeetings() {
        return new ArrayList<>();
    }
}
