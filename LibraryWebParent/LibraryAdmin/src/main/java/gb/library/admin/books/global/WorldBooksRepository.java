package gb.library.admin.books.global;

import gb.library.admin.utils.paging.SearchRepository;
import gb.library.common.entities.Author;
import gb.library.common.entities.WorldBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface WorldBooksRepository extends SearchRepository<WorldBook, Integer> {

    List<WorldBook> findAll();

    @Query("SELECT wb FROM WorldBook wb WHERE wb.title LIKE %?1% or wb.author.firstName LIKE %?1%"
            + " OR wb.author.lastName LIKE %?1% OR wb.genre.name LIKE %?1%")
    Page<WorldBook> findAll(String keyword, Pageable pageable);

    Optional<WorldBook> findById(Integer id);

    WorldBook save(WorldBook worldBook);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    WorldBook findByTitleAndAuthor(String title, Author author);
}
