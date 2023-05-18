package gb.library.backend.repositories;

import gb.library.common.entities.BookOnHands;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("readerBookHistoryRepository")
public interface BookHistoryRepository extends JpaRepository<BookOnHands, Integer> {
    @Query("select b from BookOnHands b where b.user.id = ?1 and b.returned = false ")
    Page<BookOnHands> findUnreturnedByUserId(Integer userId, Pageable pageable);

    @Query("select b from BookOnHands b where b.user.id = ?1")
    Page<BookOnHands> findBooksByUserId(Integer userId, Pageable pageable);
}
