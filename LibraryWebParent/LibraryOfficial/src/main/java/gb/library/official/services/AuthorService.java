package gb.library.official.services;

import gb.library.common.entities.Author;
import org.springframework.data.domain.Page;


public interface AuthorService {
    Author findById(Integer id);

    Page<Author> findAll(int pageIndex, int pageSize);

    Page<Author> findByFirstName(int pageIndex, int pageSize, String firstName);

    Page<Author> findByLastName(int pageIndex, int pageSize, String lastName);

    Author save(Author author);

    void deleteById(Integer id);

    Author update(Author author);
}