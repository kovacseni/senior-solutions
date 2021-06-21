package meetingrooms.entity;

import java.time.LocalDateTime;

public class Meeting {

    private String nameOfMeetingHolder;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;

    public Meeting() {
    }

    public Meeting(String nameOfMeetingHolder, LocalDateTime beginTime, LocalDateTime endTime) {
        this.nameOfMeetingHolder = nameOfMeetingHolder;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public String getNameOfMeetingHolder() {
        return nameOfMeetingHolder;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setNameOfMeetingHolder(String nameOfMeetingHolder) {
        this.nameOfMeetingHolder = nameOfMeetingHolder;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
