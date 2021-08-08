package org.training360.com.moviesquerypractice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Set;

public interface ActorRepository extends JpaRepository<Actor, Long> {

//  Kérdezzük le azokat a színészeket, akik egy bizonyos dátum után születtek!

    @Query("select a from Actor a where a.dateOfBirth > :date")
    Set<Actor> findActorByDateOfBirthAfterDate(LocalDate date);

//  Kérdezzük le azokat a színészeket, akik egy bizonyos év után születtek!

    @Query("select a from Actor a where YEAR(a.dateOfBirth) > :year")
    Set<Actor> findActorByDateOfBirthAfterYear(int year);

//  Kérdezzük le azokat a színészeket akik több filmben is szerepelnek!

    @Query("select a from Actor a join fetch a.movies where a.movies.size > 1")
    Set<Actor> findActorsByMoviesGreaterThan();

//  Kérdezzük le azokat a színészeket, akik egy paraméterül átadott filmben szerepelnek!

    @Query("select distinct a from Actor a join fetch a.movies m where m.title = :title")
    Set<Actor> findActorByMoviesContains(@Param("title") String title);
}
