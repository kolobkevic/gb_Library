package gb.library.admin.genres;

import gb.library.common.entities.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {

}
