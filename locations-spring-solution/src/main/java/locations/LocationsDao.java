/* package locations;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@AllArgsConstructor
public class LocationsDao {

    private JdbcTemplate jdbcTemplate;

    public List<Location> getLocations() {
        return jdbcTemplate.query("select * from locations",
                this::mapRow);
    }

    public Location findLocationById(long id) {
        return jdbcTemplate.queryForObject("select * from locations where id = ?",
        this::mapRow, id);
    }

    public Location createLocation(Location location) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement stmt = connection.prepareStatement("insert into locations(latitude, longitude, location_name) values (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setDouble(1, location.getLat());
                stmt.setDouble(2, location.getLon());
                stmt.setString(3, location.getName());
                return stmt;
            }
        }, keyHolder);
        location.setId(keyHolder.getKey().longValue());
        return location;
    }

    private Location mapRow(ResultSet rs, int i) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("location_name");
        Location location = new Location(id, name);
        return location;
    }

    public Location updateLocation(Location location) {
        jdbcTemplate.update("update locations set latitude = ?, longitude = ?, location_name = ? where id = ?",
                location.getLat(), location.getLon(), location.getName(), location.getId());
        return findLocationById(location.getId());
    }

    public void deleteById(long id) {
        jdbcTemplate.update("delete from locations where id = ?", id);
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from locations");
    }
} */
