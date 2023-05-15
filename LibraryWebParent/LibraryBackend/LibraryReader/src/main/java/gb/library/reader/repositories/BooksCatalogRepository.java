package gb.library.reader.repositories;

import gb.library.common.entities.WorldBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BooksCatalogRepository extends JpaRepository<WorldBook, Integer> {
}
