package covid;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class RegistrationRepository {

    private List<Person> registrated = new ArrayList<>();

/*    private List<Person> registrated = new ArrayList<>(Arrays.asList(new Person(1, "Józsi", "1234", "2021.01.02", "Budapest", "Kukutyin"),
                                                    new Person(2, "Béla", "2345", "2021.12.13", "Vecsés", "Győr"),
                                                    new Person(3, "Sanyi", "3456", "2021.10.03", "Gyömrő", "Hajdúhadház"))
            );*/

    public void registrate(Person person) {
        registrated.add(person);
    }

    public List<Person> getRegistrated() {
        return new ArrayList<>(registrated);
    }
}
