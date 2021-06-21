package meetingrooms.repository;

import meetingrooms.entity.Meeting;
import meetingrooms.entity.MeetingRoom;
import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MariaDbMeetingRoomsRepository implements MeetingRoomsRepository {

    private JdbcTemplate jdbcTemplate;
    private MariaDbDataSource dataSource;

    public MariaDbMeetingRoomsRepository() {
        try {
            dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mariadb://localhost:3306/meetingrooms?useUnicode=true");
            dataSource.setUser("meetingrooms");
            dataSource.setPassword("meetingrooms");

            Flyway fw = Flyway.configure().dataSource(dataSource).load();
            fw.migrate();

            jdbcTemplate = new JdbcTemplate(dataSource);

        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot create DataSource.", sqle);
        }
    }

    @Override
    public MeetingRoom save(String name, int width, int length) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into meetingrooms(meetingroom_name, width, length) values (?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setInt(2, width);
            ps.setInt(3, length);
            return ps;
        }, keyHolder
        );
        long id = keyHolder.getKey().longValue();
        return new MeetingRoom(id, name, width, length);
    }

    @Override
    public List<String> getMeetingroomsOrderedByName() {
        return jdbcTemplate.query("select meetingroom_name from meetingrooms order by meetingroom_name;",
                (rs, i) -> rs.getString("meetingroom_name"));
    }

    @Override
    public List<String> getEverySecondMeetingRoom() {
        return getMeetingroomsOrderedByName();
    }

    @Override
    public List<MeetingRoom> getMeetingRooms() {
        return jdbcTemplate.query("select meetingroom_name, width, length from meetingrooms;",
                this::getMeetingRoomsFromResultSet);
    }

    @Override
    public List<MeetingRoom> getExactMeetingRoomByName(String name) {
        return jdbcTemplate.query("select meetingroom_name, width, length from meetingrooms where meetingroom_name = ?;",
                this::getMeetingRoomsFromResultSet, name);
    }

    @Override
    public List<MeetingRoom> getMeetingRoomsByPrefix(String prefix) {
        return jdbcTemplate.query("select meetingroom_name, width, length from meetingrooms where meetingroom_name like ? order by meetingroom_name;",
                this::getMeetingRoomsFromResultSet, "%" + prefix + "%");
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from meetingrooms;");
    }

    private List<MeetingRoom> getMeetingRoomsFromResultSet(ResultSet rs) {
        List<MeetingRoom> meetingRooms = new ArrayList<>();
        try {
            while (rs.next()) {
                meetingRooms.add(new MeetingRoom(rs.getString("meetingroom_name"), rs.getInt("width"), rs.getInt("length")));
            }
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot get data.", sqle);
        }
        return meetingRooms;
    }

    public void saveMeetingRoomAndMeetingsToo(MeetingRoom meetingRoom) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try {
                long id = saveMeetingRoom(meetingRoom, conn);
                saveMeetings(id, meetingRoom.getMeetings(), conn);

                conn.commit();

            } catch (Exception ex) {
                conn.rollback();
                throw new IllegalArgumentException("Transaction not succeeded!", ex);
            }

        } catch (SQLException sqle) {
            throw new IllegalStateException("Wrong connection.", sqle);
        }
    }

    private long saveMeetingRoom(MeetingRoom meetingRoom, Connection conn) throws Exception {
        long id = 0;
        try (PreparedStatement stmt = conn.prepareStatement("insert into meetingrooms(mr_name, mr_width, mr_length) values (?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, meetingRoom.getName());
            stmt.setInt(2, meetingRoom.getWidth());
            stmt.setInt(3, meetingRoom.getLength());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }
            return id;
        }
    }

    private void saveMeetings(long id, List<Meeting> meetings, Connection conn) throws Exception {
        for (Meeting m : meetings) {
            try (PreparedStatement stmt = conn.prepareStatement("insert into meetings(mr_id, holder_name, begin_time, end_time) values (?, ?, ?, ?);")) {
                stmt.setLong(1, id);
                stmt.setString(2, m.getNameOfMeetingHolder());
                stmt.setTimestamp(3, Timestamp.valueOf(m.getBeginTime()));
                stmt.setTimestamp(4, Timestamp.valueOf(m.getEndTime()));

                stmt.executeUpdate();
            }
        }
    }

    public List<MeetingRoom> loadMeetingRoomsWithMeetings() {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try {
                List<MeetingRoom> meetingRooms = loadMeetingRooms(conn);
                conn.commit();
                return meetingRooms;
            } catch (Exception ex) {
                conn.rollback();
                throw new IllegalStateException("Transaction not succeeded!", ex);
            }

        } catch (SQLException sqle) {
            throw new IllegalStateException("Wrong connection.", sqle);
        }
    }

    private List<MeetingRoom> loadMeetingRooms(Connection conn) throws Exception {
        List<MeetingRoom> meetingRooms = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from meetingrooms;")) {
            while (rs.next()) {
                long id = rs.getLong("mr_id");
                String name = rs.getString("mr_name");
                int width = rs.getInt("mr_width");
                int length = rs.getInt("mr_length");
                List<Meeting> meetings = loadMeetings(conn, id);

                meetingRooms.add(new MeetingRoom(id, name, width, length, meetings));
            }
        }
        return meetingRooms;
    }

    private List<Meeting> loadMeetings(Connection conn, long id) throws Exception {
        try (PreparedStatement stmt = conn.prepareStatement("select * from meetings where mr_id = ?;")) {
            stmt.setLong(1, id);

            return getMeetings(stmt);
        }
    }

    private List<Meeting> getMeetings(PreparedStatement stmt) throws Exception {
        List<Meeting> meetings = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String nameOfMeetingHolder = rs.getString("holder_name");
                LocalDateTime beginTime = rs.getTimestamp("begin_time").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("end_time").toLocalDateTime();

                meetings.add(new Meeting(nameOfMeetingHolder, beginTime, endTime));
            }
        }
        return meetings;
    }
}
