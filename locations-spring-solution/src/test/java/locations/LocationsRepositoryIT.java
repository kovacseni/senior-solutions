package locations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class LocationsRepositoryIT {

    @Autowired
    LocationsRepository repository;

    @Test
    void testFindAll() {
        Location location1 = new Location("Róma", 41.90383, 12.50557);
        Location location2 = new Location("Athén", 37.97954, 23.72638);
        Location location3 = new Location("Budapest", 47.497912, 19.040235);

        repository.save(location1);
        repository.save(location2);
        repository.save(location3);

        List<Location> expected = repository.findAll();

        Assertions.assertEquals(3, expected.size());
    }

    @Test
    void testFindById() {
        Location location = new Location("Budapest", 47.497912, 19.040235);
        repository.save(location);
        List<Location> locations = repository.findAll();
        long id = locations.get(0).getId();

        Location expected = repository.findById(id).get();

        Assertions.assertEquals("Budapest", expected.getName());
        Assertions.assertEquals(47.497912, expected.getLat());
        Assertions.assertEquals(19.040235, expected.getLon());
    }

    @Test
    void testUpdate() {
        Location location = new Location("Budapest", 47.497912, 19.040235);
        repository.save(location);
        List<Location> locations = repository.findAll();
        long id = locations.get(0).getId();

        new LocationsService(new ModelMapper(), repository).updateLocation(id, new UpdateLocationCommand("Budapest", 2.2, 3.3));

        Location expected = repository.findById(id).get();

        Assertions.assertEquals("Budapest", expected.getName());
        Assertions.assertEquals(2.2, expected.getLat());
        Assertions.assertEquals(3.3, expected.getLon());
    }

    @Test
    void testDeleteById() {
        Location location = new Location("Budapest", 47.497912, 19.040235);
        repository.save(location);
        List<Location> locations = repository.findAll();
        long id = locations.get(0).getId();

        repository.deleteById(id);

        List<Location> expected = repository.findAll();

        Assertions.assertEquals(0, expected.size());
    }

    @Test
    void testDeleteAll() {
        Location location1 = new Location("Róma", 41.90383, 12.50557);
        Location location2 = new Location("Athén", 37.97954, 23.72638);
        Location location3 = new Location("Budapest", 47.497912, 19.040235);

        repository.save(location1);
        repository.save(location2);
        repository.save(location3);

        repository.deleteAll();

        List<Location> expected = repository.findAll();

        Assertions.assertEquals(0, expected.size());
    }
}
