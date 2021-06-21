package meetingrooms;

import meetingrooms.entity.Meeting;
import meetingrooms.entity.MeetingRoom;
import meetingrooms.repository.MariaDbMeetingRoomsRepository;
import meetingrooms.service.MeetingRoomsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingRoomAndMeetingsHandlerTest {

    MeetingRoomsService mrs = new MeetingRoomsService(new MariaDbMeetingRoomsRepository());
    List<MeetingRoom> meetingRooms;

    @BeforeEach
    void setUp() {
        mrs.deleteAll();

        List<Meeting> meetings1 = new ArrayList<>();
        meetings1.add(new Meeting("Kiss József", LocalDateTime.of(2021, 6, 14, 10, 30), LocalDateTime.of(2021, 6, 14, 12, 0)));
        meetings1.add(new Meeting("Nagy Béla", LocalDateTime.of(2021, 6, 14, 12, 30), LocalDateTime.of(2021, 6, 14, 13, 0)));
        meetings1.add(new Meeting("Szabó László", LocalDateTime.of(2021, 6, 15, 9, 0), LocalDateTime.of(2021, 6, 15, 10, 30)));

        List<Meeting> meetings2 = new ArrayList<>();
        meetings2.add(new Meeting("Takács Lajos", LocalDateTime.of(2021, 6, 15, 14, 30), LocalDateTime.of(2021, 6, 15, 15, 0)));
        meetings2.add(new Meeting("Németh Géza", LocalDateTime.of(2021, 6, 15, 16, 0), LocalDateTime.of(2021, 6, 15, 16, 15)));

        List<Meeting> meetings3 = new ArrayList<>();
        meetings3.add(new Meeting("Kiss Klára", LocalDateTime.of(2021, 6, 16, 10, 30), LocalDateTime.of(2021, 6, 16, 11, 0)));
        meetings3.add(new Meeting("Porosz Péter", LocalDateTime.of(2021, 6, 16, 11, 30), LocalDateTime.of(2021, 6, 16, 13, 0)));
        meetings3.add(new Meeting("Tímár Gergely", LocalDateTime.of(2021, 6, 16, 15, 0), LocalDateTime.of(2021, 6, 16, 15, 30)));
        meetings3.add(new Meeting("Jánosi Róbert", LocalDateTime.of(2021, 6, 17, 12, 30), LocalDateTime.of(2021, 6, 17, 13, 30)));
        meetings3.add(new Meeting("Hidas Nóra", LocalDateTime.of(2021, 6, 17, 14, 0), LocalDateTime.of(2021, 6, 17, 15, 0)));

        meetingRooms = new ArrayList<>();
        meetingRooms.add(new MeetingRoom("Egyes tárgyaló", 3, 4, meetings1));
        meetingRooms.add(new MeetingRoom("Kettes tárgyaló", 4, 5, meetings2));
        meetingRooms.add(new MeetingRoom("Hármas tárgyaló", 5, 6, meetings3));
    }

    @Test
    void testSaveMeetingRoomsAndMeetingsToo() {
       mrs.saveMeetingRoomsAndMeetingsToo(meetingRooms);
       List<MeetingRoom> expected = mrs.loadMeetingRoomsWithMeetings();

        Assertions.assertEquals(3, expected.size());
        Assertions.assertEquals("Kettes tárgyaló", expected.get(1).getName());
        Assertions.assertEquals("Tímár Gergely", expected.get(2).getMeetings().get(2).getNameOfMeetingHolder());
        Assertions.assertEquals(2, expected.get(1).getMeetings().size());
        Assertions.assertEquals(LocalDateTime.of(2021, 6, 14, 13, 0), expected.get(0).getMeetings().get(1).getEndTime());
    }
}
