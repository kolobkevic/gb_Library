package gb.library.backend.repositories;


import gb.library.common.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> , JpaSpecificationExecutor<Genre> {

    @Query(value = "select g from Genre g where upper(g.name) like %?1%")
    List<Genre> findByGenre(String lastName);


//    List<Genre> findAllByNameLikeIgnoreCase(String name);
//
//    List<Genre> findAllByDescriptionLikeIgnoreCase(String description);

}

