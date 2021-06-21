package employees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryEmployeesRepository implements EmployeesRepository {

    private List<Employee> employees = new ArrayList<>();

    @Override
    public void save(String name) {
        employees.add(new Employee(name));
    }

    @Override
    public List<Employee> findAll() {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getName))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        employees.clear();
    }
}
