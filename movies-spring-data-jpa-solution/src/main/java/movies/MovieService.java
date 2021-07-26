package movies;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private ModelMapper modelMapper;
    private MovieRepository repository;

    public List<MovieDto> getMovies() {
        Type targetListType = new TypeToken<List<MovieDto>>(){}.getType();
        List<Movie> filteredMovies = repository.findAll();
        return modelMapper.map(filteredMovies, targetListType);
    }

    public MovieDto createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(command.getTitle());
        repository.save(movie);
        return modelMapper.map(movie, MovieDto.class);
    }

    @Transactional
    public MovieDto addRating(long id, AddRatingCommand command) {
        Movie movie = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movie with id: " + id + " not found."));
        movie.addRating(command.getRating());
        return modelMapper.map(movie, MovieDto.class);
    }
}
