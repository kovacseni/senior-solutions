package movies;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public List<MovieDto> getMovies(@RequestParam Optional<String> prefix) {
        return service.getMovies(prefix);
    }

    @GetMapping("/{id}")
    public MovieDto findMovieById(@PathVariable("id") long id) {
        return service.findMovieById(id);
    }

    @PostMapping
    public MovieDto createMovie(@RequestBody CreateMovieCommand command) {
        return service.createMovie(command);
    }

    @PostMapping("/{id}/rating")
    public double addRating(@PathVariable ("id") long id, @RequestBody AddRatingCommand command) {
        return service.addRating(id, command);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable("id") long id) {
        service.deleteMovie(id);
    }
}