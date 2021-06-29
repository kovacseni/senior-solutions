package movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private long id;
    private String title;
    private int length;
    private List<Integer> ratings;
    private double ratingsAverage;

    public double getAverage() {
        return ((double) ratings.stream()
                .mapToInt(number -> number.intValue())
                .sum()
                /
                ratings.stream()
                .count());
    }
}
