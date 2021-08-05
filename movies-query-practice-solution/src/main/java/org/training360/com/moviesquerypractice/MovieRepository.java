package org.training360.com.moviesquerypractice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long> {

//  Kérdezzük le azokat filmeket, amelyekben a paraméterül átadott nevű színész szerepel!

    @Query("select m from Movie m where (select a from Actor a where a.name = :name) member of m.actors")
    Set<Movie> findMoviesByActorsIn(@Param("name") String name);

//  Kérdezzük le azokat a filmeket amik egy paraméterül átadott stúdióban készültek egy paraméterül átadott évben!

    @Query("select m from Movie m where m.studio = :studio and m.year = :year")
    Set<Movie> findMoviesByStudioAndYear(Studio studio, int year);
}
