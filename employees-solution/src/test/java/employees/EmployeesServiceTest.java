package employees;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeesServiceTest {

    EmployeesService employeesServiceMemory = new EmployeesService(new InMemoryEmployeesRepository());
    EmployeesService employeesServiceDatabase = new EmployeesService(new MariaDbEmployeesRepository());

    @BeforeEach
    void init() {
        employeesServiceMemory.deleteAll();
        employeesServiceDatabase.deleteAll();
    }

    @Test
    void testSaveThanListMemory() {

        employeesServiceMemory.save("John Doe");

        List<Employee> employees = employeesServiceMemory.listEmployees();

        Assertions.assertEquals(1, employees.size());
        Assertions.assertEquals("John Doe", employees.get(0).getName());
    }

    @Test
    void testSaveTwoThanListMemory() {

        employeesServiceMemory.save("John Doe");
        employeesServiceMemory.save("Jane Doe");

        List<Employee> employees = employeesServiceMemory.listEmployees();

        Assertions.assertEquals(2, employees.size());
        Assertions.assertEquals("Jane Doe", employees.get(0).getName());
        Assertions.assertEquals("John Doe", employees.get(1).getName());
    }

    @Test
    void testSaveThanListDatabase() {

        employeesServiceDatabase.save("John Doe");

        List<Employee> employees = employeesServiceDatabase.listEmployees();

        Assertions.assertEquals(1, employees.size());
        Assertions.assertEquals("John Doe", employees.get(0).getName());
    }

    @Test
    void testSaveTwoThanListDatabase() {

        employeesServiceDatabase.save("John Doe");
        employeesServiceDatabase.save("Jane Doe");

        List<Employee> employees = employeesServiceDatabase.listEmployees();

        Assertions.assertEquals(2, employees.size());
        Assertions.assertEquals("Jane Doe", employees.get(0).getName());
        Assertions.assertEquals("John Doe", employees.get(1).getName());
    }
}
