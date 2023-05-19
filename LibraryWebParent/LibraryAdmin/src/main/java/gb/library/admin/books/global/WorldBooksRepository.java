package gb.library.admin.books.global;

import gb.library.admin.utils.paging.SearchRepository;
import gb.library.common.entities.Author;
import gb.library.common.entities.WorldBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface WorldBooksRepository extends SearchRepository<WorldBook, Integer> {

    @Query("SELECT wb FROM WorldBook wb WHERE wb.title LIKE %?1% or wb.author.firstName LIKE %?1%"
            + " OR wb.author.lastName LIKE %?1% OR wb.genre.name LIKE %?1%")
    Page<WorldBook> getAllWithFilter(String keyword, Pageable pageable);

    WorldBook save(WorldBook worldBook);

    WorldBook findByTitleAndAuthor(String title, Author author);
}
