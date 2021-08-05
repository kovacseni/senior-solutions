package org.training360.com.moviesquerypractice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface StudioRepository extends JpaRepository<Studio, Long> {

//  Kérdezzük le azokat a stúdiókat melyek legalább 2 filmmel büszkélkedhetnek!

    @Query("select s from Studio s where s.movies.size > 1")
    Set<Studio> findStudiosByMoviesGreaterThan();

//  Kérdezzük le azokat a stúdiókat, ahol egy paraméterül átadott színész szerepelt legalább egy filmben!

    @Query("select s from Studio s where (select m from Movie m where (select a from Actor a where a = :actor) member of m.actors) member of s.movies")
    Set<Studio> findStudiosByMoviesContaining(Actor actor);

//  Adjuk vissza azt a stúdiót, ahol egy színész legalább két filmben szerepel!

    @Query("select s from Studio s")
    Set<Studio> findStudiosByMoviesContainingAnd(Actor actor);
}
