package movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ElementCollection
    @CollectionTable(name = "ratings", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "rating")
    private List<Integer> ratings;

    public Movie(String title) {
        this.title = title;
    }

    public void addRating(int rating) {
        if (ratings == null) {
            ratings = new ArrayList<>();
        }
        ratings.add(rating);
    }
}
