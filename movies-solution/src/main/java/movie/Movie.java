package movie;

import java.time.LocalDate;
import java.util.Objects;

public class Movie {

    private String name;

    private int length;

    private LocalDate releaseDate;

    public Movie(String name, int length, LocalDate releaseDate) {
        this.name = name;
        this.length = length;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return length == movie.length && name.equals(movie.name) && releaseDate.equals(movie.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, length, releaseDate);
    }
}
