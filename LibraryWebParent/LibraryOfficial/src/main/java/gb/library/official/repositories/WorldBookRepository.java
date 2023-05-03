package gb.library.official.repositories;

import gb.library.common.entities.WorldBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldBookRepository extends JpaRepository<WorldBook, Integer> , JpaSpecificationExecutor<WorldBook> {

}

