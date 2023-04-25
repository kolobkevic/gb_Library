package gb.library.admin.authors;

import gb.library.admin.utils.paging.SearchRepository;
import gb.library.common.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends SearchRepository<Author, Integer> {

    List<Author> findAll();

    @Query("SELECT a FROM Author a WHERE a.firstName LIKE %?1% OR a.lastName LIKE %?1%")
    Page<Author> findAll(String keyword, Pageable pageable);

    Optional<Author> findById(Integer id);

    Author save(Author author);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Author findByFirstNameAndLastName(String firstName, String lastName);
}
