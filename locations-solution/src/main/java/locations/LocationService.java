package locations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocationService {

    public void writeLocations(Path file, List<Location> locations) {
        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            for (Location l : locations) {
                bw.write(l.getName() + ",");
                bw.write(l.getLat() + ",");
                bw.write(l.getLon() + "\n");
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not write file.", ioe);
        }
    }

    public List<Location> readLocations(Path path) {
        List<Location> locations = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] temp = line.split(",");
                locations.add(new Location(temp[0], Double.parseDouble(temp[1]), Double.parseDouble(temp[2])));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not read file.", ioe);
        }
        return locations;
    }
}
