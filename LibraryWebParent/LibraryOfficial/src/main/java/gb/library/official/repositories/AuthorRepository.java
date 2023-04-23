package gb.library.official.repositories;

import gb.lib.common.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Page<Author> findAllByFirstName(Pageable pageable, String firstName);

    Page<Author> findAllByLastName(Pageable pageable, String lastName);

}
