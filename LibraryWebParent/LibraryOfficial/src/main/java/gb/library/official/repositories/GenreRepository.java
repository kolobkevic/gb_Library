package gb.library.official.repositories;


import gb.library.common.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> , JpaSpecificationExecutor<Genre> {

    List<Genre> findAllByNameLikeIgnoreCase(String name);

    List<Genre> findAllByDescriptionLikeIgnoreCase(String description);


}

