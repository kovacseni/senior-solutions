package movies;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private List<Movie> movies = new ArrayList<>();
    private ModelMapper modelMapper;
    private AtomicLong idGenerator = new AtomicLong();

    public MovieService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public List<MovieDto> getMovies(Optional<String> prefix) {
        Type targetListType = new TypeToken<List<MovieDto>>(){}.getType();
        List<Movie> filteredMovies = movies.stream()
                .filter(movie -> prefix.isEmpty() || movie.getTitle().startsWith(prefix.get()))
                .collect(Collectors.toList());
        filteredMovies.forEach(movie -> movie.setRatingsAverage(movie.getAverage()));
        return modelMapper.map(filteredMovies, targetListType);
    }

    public MovieDto findMovieById(long id) {
        Movie result = movies.stream()
                        .filter(movie -> movie.getId() == id)
                        .findAny()
                        .orElseThrow(() -> new IllegalArgumentException("Movie with id: " + id + " not found."));
        result.setRatingsAverage(result.getAverage());
        return modelMapper.map(result, MovieDto.class);
    }


    public MovieDto createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(idGenerator.incrementAndGet(), command.getTitle(), command.getLength(), new ArrayList<>(), 0.0);
        movies.add(movie);
        return modelMapper.map(movie, MovieDto.class);
    }

    public double addRating(long id, AddRatingCommand command) {
        Movie movie = movies.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Movie with id: " + id + " not found."));
        movie.getRatings().add(command.getRating());
        return movie.getAverage();
    }

    public void deleteMovie(long id) {
        Movie movie = movies.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Movie with id: " + id + " not found."));
        movies.remove(movie);
    }
}

