package gb.library.reader.repositories;

import gb.library.common.entities.ReservedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservedBooksRepository extends JpaRepository<ReservedBook, Integer> {
}
