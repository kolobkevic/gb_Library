package gb.library.reader.temp;

import gb.library.common.entities.WorldBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldBookRepository extends JpaRepository<WorldBook, Integer> {
    WorldBook findAllById(Integer id);
}
