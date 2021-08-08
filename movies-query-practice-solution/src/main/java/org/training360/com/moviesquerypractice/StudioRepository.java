package org.training360.com.moviesquerypractice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface StudioRepository extends JpaRepository<Studio, Long> {

//  Kérdezzük le azokat a stúdiókat melyek legalább 2 filmmel büszkélkedhetnek!

    @Query("select s from Studio s where s.movies.size > 1")
    Set<Studio> findStudiosByMoviesGreaterThan();

//  Kérdezzük le azokat a stúdiókat, ahol egy paraméterül átadott színész szerepelt legalább egy filmben!

    @Query("select s from Studio s join fetch s.movies m join fetch m.actors a where a.name = :name")
    Set<Studio> findStudiosByMoviesContaining(@Param("name") String name);

//  Adjuk vissza azt a stúdiót, ahol egy színész legalább két filmben szerepel!

    @Query("select distinct s from Movie m left join m.studio s left join m.actors a group by a.name, m.studio.id having count (s.name) > 1")
    Set<Studio> findStudiosByActorWithMinTwoFilms();
}
