package gb.library.backend.repositories;

import gb.library.common.entities.ReservedBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservedBooksRepository extends JpaRepository<ReservedBook, Integer> {
    Page<ReservedBook> findAllByUserId(Integer userId, Pageable pageable);
}
