package gb.library.admin.genres;

import gb.library.admin.utils.paging.SearchRepository;
import gb.library.common.entities.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface GenreRepository extends SearchRepository<Genre, Integer> {

    @Query("SELECT g FROM Genre g WHERE g.name LIKE %?1%")
    Page<Genre> getAllWithFilter(String keyword, Pageable pageable);

    Genre save(Genre genre);

    Genre findByName(String name);
}
