package bicycle;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BikeService {

    private List<Bike> bikes = new ArrayList<>();

    public void addBike(Bike bike) {
        bikes.add(bike);
    }

    public List<Bike> getBikes() {
        if (bikes.size() == 0) {
            loadBikes();
        }
        return new ArrayList<>(bikes);
    }

    private void loadBikes() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(BikeService.class.getResourceAsStream("/bikes.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] temp = line.split(";");
                String[] dateTime = temp[2].split(" ");
                LocalDateTime latestDepo = LocalDateTime.of(LocalDate.parse(dateTime[0]), LocalTime.parse(dateTime[1]));
                double km = Double.parseDouble(temp[3]);
                bikes.add(new Bike(temp[0], temp[1], latestDepo, km));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not read file.", ioe);
        }
    }

    public List<String> getUserIds() {
        getBikes();
        List<String> userIds = bikes.stream()
                .map(bike -> bike.getUserId())
                .collect(Collectors.toList());

        return new ArrayList<>(userIds);
    }
}
