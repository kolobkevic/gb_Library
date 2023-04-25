package gb.library.admin.genres;

import gb.library.admin.utils.paging.SearchRepository;
import gb.library.common.entities.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface GenreRepository extends SearchRepository<Genre, Integer> {
    List<Genre> findAll();

    @Query("SELECT g FROM Genre g WHERE g.name LIKE %?1%")
    Page<Genre> findAll(String keyword, Pageable pageable);

    Optional<Genre> findById(Integer id);

    Genre save(Genre genre);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Genre findByName(String name);
}
