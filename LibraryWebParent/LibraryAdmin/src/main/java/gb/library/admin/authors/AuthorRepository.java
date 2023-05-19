package gb.library.admin.authors;

import gb.library.admin.utils.paging.SearchRepository;
import gb.library.common.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorRepository extends SearchRepository<Author, Integer> {

    @Query("SELECT a FROM Author a WHERE a.firstName LIKE %?1% OR a.lastName LIKE %?1%")
    Page<Author> getAllWithFilter(String keyword, Pageable pageable);

    Author save(Author author);

    Author findByFirstNameAndLastName(String firstName, String lastName);
}
