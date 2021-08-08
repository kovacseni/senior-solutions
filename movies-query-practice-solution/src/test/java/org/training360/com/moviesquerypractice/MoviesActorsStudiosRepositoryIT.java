package org.training360.com.moviesquerypractice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class MoviesActorsStudiosRepositoryIT {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    StudioRepository studioRepository;

    Actor actor1;
    Actor actor2;
    Actor actor3;
    Movie movie1;
    Movie movie2;
    Movie movie3;
    Movie movie4;
    Studio studio1;
    Studio studio2;

    @BeforeEach
    void init() {
        actor1 = new Actor("name1", LocalDate.now());
        actor2 = new Actor("name2", LocalDate.now());
        actor3 = new Actor("name3", LocalDate.now().minusYears(10));
        movie1 = new Movie("title1", 2020);
        movie2 = new Movie("title2", 2019);
        movie3 = new Movie("title3", 2019);
        movie4 = new Movie("title4", 2020);
        studio1 = new Studio("studio1");
        studio2 = new Studio("studio2");
        movie1.addActor(actor1);
        movie1.addActor(actor2);
        movie2.addActor(actor1);
        movie3.addActor(actor3);
        movie4.addActor(actor1);
        movie4.addActor(actor3);
        studio1.addMovie(movie1);
        studio1.addMovie(movie2);
        studio1.addMovie(movie3);
        studio2.addMovie(movie4);
        studioRepository.save(studio1);
        studioRepository.save(studio2);
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);
        movieRepository.save(movie4);
        actorRepository.save(actor1);
        actorRepository.save(actor2);
        actorRepository.save(actor3);
    }

    @Test
    void testFindMoviesByActorsIn() {
        Set<Movie> movies = Set.of(movie1, movie2, movie4);

        Set<Movie> expected = movieRepository.findMoviesByActorsIn(actor1.getName());

        assertEquals(movies, expected);
    }

    @Test
    void testFindMoviesByStudioAndYear() {
        Set<Movie> movies = Set.of(movie1);

        Set<Movie> expected = movieRepository.findMoviesByStudioAndYear(studio1, 2020);

        assertEquals(movies, expected);
    }

    @Test
    void testFindActorByDateOfBirthAfterDate() {
        Set<Actor> actors = Set.of(actor1, actor2);

        Set<Actor> expected = actorRepository.findActorByDateOfBirthAfterDate(LocalDate.now().minusDays(1));

        assertEquals(actors, expected);
    }

    @Test
    void testFindActorByDateOfBirthAfterYear() {
        Set<Actor> actors = Set.of(actor1, actor2);

        Set<Actor> expected = actorRepository.findActorByDateOfBirthAfterYear(LocalDate.now().minusYears(1).getYear());

        assertEquals(actors, expected);
    }

    @Test
    void testFindActorsByMoviesGreaterThan() {
        Set<Actor> actors = Set.of(actor1, actor3);

        Set<Actor> expected = actorRepository.findActorsByMoviesGreaterThan();

        assertEquals(actors, expected);
    }

    @Test
    void testFindActorByMoviesContains() {
        Set<Actor> actors = Set.of(actor1, actor3);

        Set<Actor> expected = actorRepository.findActorByMoviesContains(movie4.getTitle());

        assertEquals(actors, expected);
    }

    @Test
    void testFindStudiosByMovies() {
        Set<Studio> studios = Set.of(studio1);

        Set<Studio> expected = studioRepository.findStudiosByMoviesGreaterThan();

        assertEquals(studios, expected);
    }

    @Test
    void testFindStudiosByMoviesContaining() {
        Set<Studio> studios = Set.of(studio1, studio2);

        Set<Studio> expected = studioRepository.findStudiosByMoviesContaining(actor1.getName());

        assertEquals(studios, expected);
    }

    @Test
    void testFindStudiosByMoviesContainingAnd() {
        Set<Studio> studios = Set.of(studio1);

        Set<Studio> expected = studioRepository.findStudiosByActorWithMinTwoFilms();

        assertEquals(studios, expected);
    }

/*  Actor bradPitt;
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
*/

/*  @BeforeEach
    void setUp() {
        warnerBros = new Studio("Warner Bros.");
        hollywoodPictures = new Studio("Hollywood Pictures");
        unitedArtists = new Studio("United Artists");

        bradPitt = new Actor("Brad Pitt", LocalDate.of(1963, 12, 18));
        tomCruise = new Actor("Tom Cruise", LocalDate.of(1962, 7, 3));
        nicoleKidman = new Actor("Nicole Kidman", LocalDate.of(1967, 7, 20));
        antonioBanderas = new Actor("Antonio Banderas", LocalDate.of(1960, 8, 10));
        madonna = new Actor("Madonna", LocalDate.of(1958, 8, 16));
        dustinHoffmann = new Actor("Dustin Hoffmann", LocalDate.of(1937, 8, 8));
        marilynMonroe = new Actor("Marilyn Monroe", LocalDate.of(1926, 6, 1));
        tonyCurtis = new Actor("Tony Curtis", LocalDate.of(1925, 6, 3));
        jackLemmon = new Actor("Jack Lemmon", LocalDate.of(1925, 2, 8));

        interviewWithTheVampire = new Movie("Interjú a vámpírral", 1994);
        evita = new Movie("Evita", 1996);
        rainMan = new Movie("Esőember", 1988);
        eyesWideShut = new Movie("Tágra zárt szemek", 1999);
        someLikeItHot = new Movie("Van, aki forrón szereti", 1959);

        warnerBros.addMovie(interviewWithTheVampire);
        warnerBros.addMovie(eyesWideShut);
        hollywoodPictures.addMovie(evita);
        unitedArtists.addMovie(rainMan);
        unitedArtists.addMovie(someLikeItHot);

        bradPitt.getMovies().add(interviewWithTheVampire);
        tomCruise.getMovies().add(interviewWithTheVampire);
        tomCruise.getMovies().add(rainMan);
        tomCruise.getMovies().add(eyesWideShut);
        nicoleKidman.getMovies().add(eyesWideShut);
        antonioBanderas.getMovies().add(interviewWithTheVampire);
        antonioBanderas.getMovies().add(evita);
        madonna.getMovies().add(evita);
        dustinHoffmann.getMovies().add(rainMan);
        marilynMonroe.getMovies().add(someLikeItHot);
        tonyCurtis.getMovies().add(someLikeItHot);
        jackLemmon.getMovies().add(someLikeItHot);

        interviewWithTheVampire.setStudio(warnerBros);
        interviewWithTheVampire.addActor(bradPitt);
        interviewWithTheVampire.addActor(tomCruise);
        interviewWithTheVampire.addActor(antonioBanderas);
        evita.setStudio(hollywoodPictures);
        evita.addActor(antonioBanderas);
        evita.addActor(madonna);
        rainMan.setStudio(unitedArtists);
        rainMan.addActor(dustinHoffmann);
        rainMan.addActor(tomCruise);
        eyesWideShut.setStudio(warnerBros);
        eyesWideShut.addActor(tomCruise);
        eyesWideShut.addActor(nicoleKidman);
        someLikeItHot.setStudio(unitedArtists);
        someLikeItHot.addActor(marilynMonroe);
        someLikeItHot.addActor(tonyCurtis);
        someLikeItHot.addActor(jackLemmon);

        actorRepository.saveAll(List.of(bradPitt, tomCruise, antonioBanderas, madonna, dustinHoffmann));

        movieRepository.saveAll(List.of(interviewWithTheVampire, evita, rainMan, someLikeItHot));

        studioRepository.saveAll(List.of(warnerBros, hollywoodPictures, unitedArtists));
    }
*/
}
