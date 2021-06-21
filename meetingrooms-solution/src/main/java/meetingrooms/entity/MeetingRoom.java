package meetingrooms.entity;

import java.util.List;

public class MeetingRoom {

    private long id;
    private String name;
    private int width;
    private int length;
    private List<Meeting> meetings;

    public MeetingRoom() {
    }

    public MeetingRoom(String name) {
        this.name = name;
    }

    public MeetingRoom(long id, String name) {
        this(name);
        this.id = id;
    }

    public MeetingRoom(String name, int width, int length) {
        this(name);
        this.width = width;
        this.length = length;
    }

    public MeetingRoom(long id, String name, int width, int length) {
        this(name, width, length);
        this.id = id;
    }

    public MeetingRoom(String name, int width, int length, List<Meeting> meetings) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.meetings = meetings;
    }

    public MeetingRoom(long id, String name, int width, int length, List<Meeting> meetings) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.length = length;
        this.meetings = meetings;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getArea() {
        return width * length;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
}
