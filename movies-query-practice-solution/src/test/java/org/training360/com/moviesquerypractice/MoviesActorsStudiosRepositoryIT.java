package org.training360.com.moviesquerypractice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class MoviesActorsStudiosRepositoryIT {

    Actor bradPitt;
    Actor tomCruise;
    Actor nicoleKidman;
    Actor antonioBanderas;
    Actor madonna;
    Actor dustinHoffmann;
    Actor marilynMonroe;
    Actor tonyCurtis;
    Actor jackLemmon;

    Studio warnerBros;
    Studio hollywoodPictures;
    Studio unitedArtists;

    Movie interviewWithTheVampire;
    Movie evita;
    Movie rainMan;
    Movie eyesWideShut;
    Movie someLikeItHot;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    StudioRepository studioRepository;

    @BeforeEach
    void setUp() {
        bradPitt = new Actor("Brad Pitt", LocalDate.of(1963, 12, 18));
        tomCruise = new Actor("Tom Cruise", LocalDate.of(1962, 7, 3));
        nicoleKidman = new Actor("Nicole Kidman", LocalDate.of(1967, 7, 20));
        antonioBanderas = new Actor("Antonio Banderas", LocalDate.of(1960, 8, 10));
        madonna = new Actor("Madonna", LocalDate.of(1958, 8, 16));
        dustinHoffmann = new Actor("Dustin Hoffmann", LocalDate.of(1937, 8, 8));
        marilynMonroe = new Actor("Marilyn Monroe", LocalDate.of(1926, 6, 1));
        tonyCurtis = new Actor("Tony Curtis", LocalDate.of(1925, 6, 3));
        jackLemmon = new Actor("Jack Lemmon", LocalDate.of(1925, 2, 8));

        actorRepository.saveAll(List.of(bradPitt, tomCruise, antonioBanderas, madonna, dustinHoffmann));

        warnerBros = new Studio("Warner Bros.");
        hollywoodPictures = new Studio("Hollywood Pictures");
        unitedArtists = new Studio("United Artists");

        studioRepository.saveAll(List.of(warnerBros, hollywoodPictures, unitedArtists));

        interviewWithTheVampire = new Movie(1L, "Interjú a vámpírral", 1994,
                                    Set.of(bradPitt, tomCruise, antonioBanderas), warnerBros);
        evita = new Movie(2L, "Evita", 1996, Set.of(antonioBanderas, madonna), hollywoodPictures);
        rainMan = new Movie(3L, "Esőember", 1988, Set.of(dustinHoffmann, tomCruise), unitedArtists);
        eyesWideShut = new Movie(4L, "Tágra zárt szemek", 1999, Set.of(tomCruise, nicoleKidman), warnerBros);
        someLikeItHot = new Movie(5L, "Van, aki forrón szereti", 1959, Set.of(marilynMonroe, tonyCurtis, jackLemmon), unitedArtists);

        movieRepository.saveAll(List.of(interviewWithTheVampire, evita, rainMan, someLikeItHot));
    }

    @Test
    void testFindMoviesByActorsIn() {
        Set<Movie> movies = Set.of(interviewWithTheVampire, evita);

        Set<Movie> expected = movieRepository.findMoviesByActorsIn("Antonio Banderas");

        assertEquals(movies, expected);
    }

    @Test
    void testFindMoviesByStudioAndYear() {
        Set<Movie> movies = Set.of(evita);

        Set<Movie> expected = movieRepository.findMoviesByStudioAndYear(hollywoodPictures, 1996);

        assertEquals(movies, expected);
    }

    @Test
    void testFindActorByDateOfBirthAfter() {
        Set<Actor> actors = Set.of(bradPitt, tomCruise, antonioBanderas);

        Set<Actor> expected = actorRepository.findActorByDateOfBirthAfter(LocalDate.of(1960, 1, 1));

        assertEquals(actors, expected);
    }

    @Test
    void testFindActorsByMoviesGreaterThan() {
        Set<Actor> actors = Set.of(antonioBanderas, tomCruise);

        Set<Actor> expected = actorRepository.findActorsByMoviesGreaterThan();

        assertEquals(actors, expected);
    }

    @Test
    void testFindActorByMoviesContains() {
        Set<Actor> actors = Set.of(antonioBanderas, madonna);

        Set<Actor> expected = actorRepository.findActorByMoviesContains(evita);

        assertEquals(actors, expected);
    }

    @Test
    void testFindStudiosByMovies() {
        Set<Studio> studios = Set.of(unitedArtists);

        Set<Studio> expected = studioRepository.findStudiosByMoviesGreaterThan();

        assertEquals(studios, expected);
    }

    @Test
    void testFindStudiosByMoviesContaining() {
        Set<Studio> studios = Set.of(unitedArtists, warnerBros);

        Set<Studio> expected = studioRepository.findStudiosByMoviesContaining(tomCruise);

        assertEquals(studios, expected);
    }

    @Test
    void testFindStudiosByMoviesContainingAnd() {
        Set<Studio> studios = Set.of(warnerBros);

        Set<Studio> expected = studioRepository.findStudiosByMoviesContainingAnd(tomCruise);

        assertEquals(studios, expected);
    }
}
