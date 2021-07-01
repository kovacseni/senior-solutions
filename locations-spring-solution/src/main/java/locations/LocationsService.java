package locations;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LocationsService {

    private AtomicLong idGenerator = new AtomicLong();
    private ModelMapper modelMapper;

    @Value("${locations.name-auto-uppercase}")
    private boolean nameAutoUpperCase;

    private List<Location> locations = Collections.synchronizedList(new ArrayList<>(
            Arrays.asList(new Location(idGenerator.incrementAndGet(), "Budapest", 47.49571, 19.05507),
                          new Location(idGenerator.incrementAndGet(), "Berlin", 52.52125, 13.41421),
                          new Location(idGenerator.incrementAndGet(), "Párizs", 48.85273, 2.35881),
                          new Location(idGenerator.incrementAndGet(), "New York", 40.71698, -73.99798))));

    public LocationsService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<LocationDto> getLocations(Optional<String> namePrefix) {
        Type targetListType = new TypeToken<List<LocationDto>>(){}.getType();
        List<Location> filteredLocations = locations.stream()
                .filter(location -> namePrefix.isEmpty() || location.getName().startsWith(namePrefix.get()))
                .collect(Collectors.toList());
        return modelMapper.map(filteredLocations, targetListType);
    }

    public LocationDto findLocationById(long id) {
        return modelMapper.map(locations.stream()
                .filter(location -> location.getId() == id)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Location with id: " + id + " not found.")),
                LocationDto.class);
    }

    public List<LocationDto> getLocationsByNameLatLon(Optional<String> prefix,
                                                      Optional<Double> minLat,
                                                      Optional<Double> maxLat,
                                                      Optional<Double> minLon,
                                                      Optional<Double> maxLon) {
        Type targetListType = new TypeToken<List<LocationDto>>(){}.getType();
        List<Location> filteredLocations = locations.stream()
                .filter(location -> prefix.isEmpty() || location.getName().startsWith(prefix.get()))
                .filter(location -> minLat.isEmpty() || location.getLat() >= minLat.get())
                .filter(location -> maxLat.isEmpty() || location.getLat() <= maxLat.get())
                .filter(location -> minLon.isEmpty() || location.getLon() >= minLon.get())
                .filter(location -> maxLon.isEmpty() || location.getLon() <= maxLon.get())
                .collect(Collectors.toList());
        return modelMapper.map(filteredLocations, targetListType);
    }

    public LocationDto createLocation(CreateLocationCommand command) {
        Location location = new Location(idGenerator.incrementAndGet(), command.getName(), command.getLat(), command.getLon());
        /*
        if (nameAutoUpperCase == true) {
            location.setName(location.getName().toUpperCase());
        }
        */
        locations.add(location);

        log.info("Location with id = " + location.getId() + " has been created.");

        return modelMapper.map(location, LocationDto.class);
    }

    public LocationDto updateLocation(long id, UpdateLocationCommand command) {
        Location location = locations.stream()
                .filter(loc -> loc.getId() == id)
                .findFirst()
                .orElseThrow(() -> new LocationNotFoundException("Location with id: " + id + " not found."));
        location.setName(command.getName());
        location.setLat(command.getLat());
        location.setLon(command.getLon());

        log.info("Location with id = " + location.getId() + " has been updated.");

        return modelMapper.map(location, LocationDto.class);
    }

    public void deleteLocation(long id) {
        Location location = locations.stream()
                .filter(loc -> loc.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Location with id: " + id + " not found."));
        locations.remove(location);

        log.info("Location with id = " + location.getId() + " has been deleted.");
    }

    public void deleteAllLocations() {
        idGenerator = new AtomicLong();
        locations.clear();
    }
}
