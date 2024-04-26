package gb.library.backend.common.repositories;

import gb.library.common.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Page<Author> findAllByFirstName(Pageable pageable, String firstName);

    Page<Author> findAllByLastName(Pageable pageable, String lastName);



    //!! В этот и подобный методы нужно передавать строки в ВЕРХНЕМ регистре. Параметр like upper(%?1%) не сработал
    @Query(value = "select a from Author a where upper(a.firstName) like %?1%")
    List<Author> findByFirstNameLike(String firstName);

    @Query(value = "select a from Author a where upper(a.lastName) like %?1%")
    List<Author> findByLastNameLike(String lastName);


    @Query(value = "SELECT a FROM Author a WHERE upper(a.firstName) LIKE %?1% and upper(a.lastName) LIKE %?2%")
    List<Author> findByFirstNameAndLastNameLike(String firstName, String lastName);




}
