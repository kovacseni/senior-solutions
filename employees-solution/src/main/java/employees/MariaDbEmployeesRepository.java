package employees;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public class MariaDbEmployeesRepository implements EmployeesRepository {

    private JdbcTemplate jdbcTemplate;

    public MariaDbEmployeesRepository() {
        try {
            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mariadb://localhost:3306/employees?useUnicode=true");
            dataSource.setUser("employees");
            dataSource.setPassword("employees");

            Flyway fw = Flyway.configure().dataSource(dataSource).load();
            fw.migrate();

            jdbcTemplate = new JdbcTemplate(dataSource);

        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot create DataSource.", sqle);
        }
    }

    @Override
    public void save(String name) {
        jdbcTemplate.update("insert into employees(emp_name) values (?);", name);
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query("select id, emp_name from employees order by emp_name;", (rs, i) -> new Employee(rs.getLong("id"), rs.getString("emp_name")));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from employees;");
    }
}
