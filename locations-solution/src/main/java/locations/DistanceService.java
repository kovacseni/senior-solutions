package locations;

import java.util.Optional;

public class DistanceService {

    private LocationRepository repository;

    public DistanceService(LocationRepository repository) {
        this.repository = repository;
    }

    Optional<Double> calculateDistance(String name1, String name2) {
        Optional<Location> findFirstLocation = repository.findByName(name1);
        Optional<Location> findSecondLocation = repository.findByName(name2);

        if (findFirstLocation.equals(Optional.empty()) || findSecondLocation.equals(Optional.empty())) {
            return Optional.empty();
        } else {
            double value = findFirstLocation.get().distanceFrom(findSecondLocation.get());
            return Optional.of(value);
        }
    }
}
