package org.training360.com.moviesquerypractice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Set;

public interface ActorRepository extends JpaRepository<Actor, Long> {

//  Kérdezzük le azokat a színészeket, akik egy bizonyos év után születtek!

    @Query("select a from Actor a where a.dateOfBirth > :date")
    Set<Actor> findActorByDateOfBirthAfter(LocalDate date);

//  Kérdezzük le azokat a színészeket akik több filmben is szerepelnek!

    @Query("select a from Actor a where a.movies.size > 1")
    Set<Actor> findActorsByMoviesGreaterThan();

//  Kérdezzük le azokat a színészeket, akik egy paraméterül átadott filmben szerepelnek!

    @Query("select a from Actor a where :movie member of a.movies")
    Set<Actor> findActorByMoviesContains(Movie movie);
}
