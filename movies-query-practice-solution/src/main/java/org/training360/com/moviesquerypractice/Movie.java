package org.training360.com.moviesquerypractice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int year;

    @ManyToMany(mappedBy = "movies")
    private Set<Actor> actors = new HashSet<>();

    @ManyToOne
    @ToString.Exclude
    private Studio studio;

    public Movie(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public void addActor(Actor actor){
        actors.add(actor);
        actor.getMovies().add(this);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                '}';
    }
}
