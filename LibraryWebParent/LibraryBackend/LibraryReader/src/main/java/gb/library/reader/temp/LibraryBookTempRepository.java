package gb.library.reader.temp;

import gb.library.common.entities.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBookTempRepository extends JpaRepository<LibraryBook, Integer> {
    LibraryBook findAllById(Integer id);
}
