package locations;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class LocationsService {

    private ModelMapper modelMapper;
    private List<Location> locations = Collections.synchronizedList(new ArrayList<>(Arrays.asList(new Location("Budapest"), new Location("Berlin"), new Location("Párizs"), new Location("New York"))));

    public LocationsService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<LocationDto> getLocations() {
        Type targetListType = new TypeToken<List<LocationDto>>(){}.getType();
        return modelMapper.map(locations, targetListType);
    }
}
