package gb.library.backend.repositories;

import gb.library.common.entities.BookOnHands;
import gb.library.common.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("readerBookHistoryRepository")
public interface BookHistoryRepository extends JpaRepository<BookOnHands, Integer> {
    @Query("select b from BookOnHands b where b.user.id = ?1 and b.returned = false ")
    Page<BookOnHands> findUnreturnedByUserId(Integer userId, Pageable pageable);

    @Query("select b from BookOnHands b where b.user.id = ?1")
    Page<BookOnHands> findBooksByUserId(Integer userId, Pageable pageable);

    BookOnHands findAllByUser(User user);

    @Query("SELECT b FROM BookOnHands b JOIN b.user u WHERE " +
            "CONCAT(b.id, ' ', u.firstName, ' ', u.lastName, ' ', u.email, ' ', b.returned, ' ', b.takenAt) " +
            "LIKE %?1%")
    Page<BookOnHands> findAllByFilter(String searchText, Pageable pageable);

    @Query("SELECT b FROM BookOnHands b JOIN b.user u WHERE " +
            "CONCAT(b.id, ' ', u.firstName, ' ', u.lastName, ' ', u.email, ' ', b.returned, ' ', b.takenAt) " +
            "LIKE %?1% AND b.returned = false")
    Page<BookOnHands> findAllActiveWithFilter(String searchText, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE BookOnHands b SET b.returned = true WHERE b.id = ?1")
    void updateWithRecordId(Integer id);
}
