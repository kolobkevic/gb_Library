package gb.library.backend.common.repositories;

import gb.library.common.entities.ReservedBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservedBooksRepository extends JpaRepository<ReservedBook, Integer> {
    Page<ReservedBook> findAllByUserId(Integer userId, Pageable pageable);

    @Query("SELECT r FROM ReservedBook r JOIN r.user u WHERE " +
            "CONCAT(r.id, ' ', u.firstName, ' ', u.lastName, ' ', u.email) LIKE %?1%")
    Page<ReservedBook> findAllLikeSearchText(String searchText, Pageable pageable);
}
