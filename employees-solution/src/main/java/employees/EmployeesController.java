package employees;

import java.util.Scanner;

//UI: Scanner, System.out.println()
public class EmployeesController {

    private Scanner scanner = new Scanner(System.in);

    private EmployeesService employeesServiceMemory = new EmployeesService(new InMemoryEmployeesRepository());

    private EmployeesService employeesServiceDatabase = new EmployeesService(new MariaDbEmployeesRepository());

    public static void main(String[] args) {
        new EmployeesController().start();
    }

    public void start() {
        System.out.println("");

        for (int i = 0; i < 5; i++) {
            String name = scanner.nextLine();
            employeesServiceMemory.save(name);
        }

        System.out.println(employeesServiceMemory.listEmployees());

        System.out.println("");

        for (int i = 0; i < 5; i++) {
            String name = scanner.nextLine();
            employeesServiceDatabase.save(name);
        }

        System.out.println(employeesServiceDatabase.listEmployees());
    }
}
