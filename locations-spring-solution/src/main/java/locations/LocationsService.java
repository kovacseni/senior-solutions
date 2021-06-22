package locations;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LocationsService {

    private ModelMapper modelMapper;
    private List<Location> locations = Collections.synchronizedList(new ArrayList<>(Arrays.asList(new Location("Budapest"), new Location("Berlin"), new Location("Párizs"), new Location("New York"))));

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

    public LocationDto getLocationById(long id) {
        return modelMapper.map(locations.stream()
                .filter(location -> location.getId() == id)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Location with id: " + id + " not found.")),
                LocationDto.class);
    }
}
