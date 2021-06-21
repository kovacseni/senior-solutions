package meetingrooms.service;

import meetingrooms.entity.MeetingRoom;
import meetingrooms.repository.MeetingRoomsRepository;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MeetingRoomsService {

    private MeetingRoomsRepository mrr;

    public MeetingRoomsService(MeetingRoomsRepository mrr) {
        this.mrr = mrr;
    }

    public void save(String name, int width, int length) {
        mrr.save(name, width, length);
    }

    public List<String> getMeetingroomsOrderedByName() {
        return mrr.getMeetingroomsOrderedByName();
    }

    public List<String> getEverySecondMeetingRoom() {
        List<String> names = mrr.getEverySecondMeetingRoom();
        return IntStream.range(0, names.size())
                .filter(index -> index % 2 == 0)
                .mapToObj(names::get)
                .collect(Collectors.toList());
    }

    public List<String> getMeetingRoomsInString(ListType type) {
        List<MeetingRoom> meetingRooms = getMeetingRooms(type);
        return getStrings(meetingRooms);
    }

    public List<MeetingRoom> getMeetingRooms(ListType type) {
        if (type == ListType.AREAS) {
            List<MeetingRoom> meetingRooms = mrr.getMeetingRooms();
            meetingRooms.sort(Comparator.comparing(MeetingRoom::getArea));
            return meetingRooms;
        } else {
            throw new IllegalArgumentException("Unexpected type of list.");
        }
    }

    public List<String> getMeetingRoomsInString(ListType type, String nameOrPrefix) {
        List<MeetingRoom> meetingRooms = getMeetingRooms(type, nameOrPrefix);
        return getStrings(meetingRooms);
    }

    public List<MeetingRoom> getMeetingRooms(ListType type, String nameOrPrefix) {
        List<MeetingRoom> meetingRooms;
        if (type == ListType.EXACT) {
            meetingRooms = mrr.getExactMeetingRoomByName(nameOrPrefix);
            meetingRooms.sort(Comparator.comparing(MeetingRoom::getArea));
        } else if (type == ListType.PREFIX) {
            meetingRooms = mrr.getMeetingRoomsByPrefix(nameOrPrefix);
            meetingRooms.sort(Comparator.comparing(MeetingRoom::getName, Collator.getInstance(new Locale("hu", "HU"))));
        } else {
            throw new IllegalArgumentException("Unexpected type of list.");
        }
        return meetingRooms;
    }

    public List<String> getMeetingRoomsInString(ListType type, int area) {
        List<MeetingRoom> meetingRooms = getMeetingRooms(type, area);
        return getStrings(meetingRooms);
    }

    public List<MeetingRoom> getMeetingRooms(ListType type, int area) {
        if (type == ListType.AREA_GREATER) {
            return mrr.getMeetingRooms().stream()
                    .filter(meetingRoom -> meetingRoom.getArea() > area)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Unexpected type of list.");
        }
    }

    private List<String> getStrings(List<MeetingRoom> meetingRooms) {
        return meetingRooms.stream()
                .map(meetingroom -> "Tárgyaló neve: " + meetingroom.getName() + ", szélessége: " + meetingroom.getWidth()
                        + ", hosszúsága: " + meetingroom.getLength() + ", területe: " + meetingroom.getArea() + " m2")
                .collect(Collectors.toList());
    }

    public void deleteAll() {
        mrr.deleteAll();
    }

    public void saveMeetingRoomAndMeetingsToo(MeetingRoom meetingRoom) {
        mrr.saveMeetingRoomAndMeetingsToo(meetingRoom);
    }

    public void saveMeetingRoomsAndMeetingsToo(List<MeetingRoom> meetingRooms) {
        meetingRooms.forEach(meetingRoom -> mrr.saveMeetingRoomAndMeetingsToo(meetingRoom));
    }

    public List<MeetingRoom> loadMeetingRoomsWithMeetings() {
        return mrr.loadMeetingRoomsWithMeetings();
    }

    /*
     List<MeetingRoom> meetingRooms = new ArrayList<>();
        MeetingRoom meetingRoom;
        while ((meetingRoom = mrr.loadMeetingRoomWithMeetings()) != null) {
            meetingRooms.add(meetingRoom);
        }
     */
}
