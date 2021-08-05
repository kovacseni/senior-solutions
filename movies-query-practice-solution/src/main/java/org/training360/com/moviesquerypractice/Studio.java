package org.training360.com.moviesquerypractice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="studios")
public class Studio {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "studio")
    @EqualsAndHashCode.Exclude
    private Set<Movie> movies= new HashSet<>();

    public Studio(String name) {
        this.name = name;
    }

    public void addMovie(Movie movie){
        movies.add(movie);
        movie.setStudio(this);
    }

    @Override
    public String toString() {
        return "Studio{" +
                "name='" + name + '\'' +
                '}';
    }
}