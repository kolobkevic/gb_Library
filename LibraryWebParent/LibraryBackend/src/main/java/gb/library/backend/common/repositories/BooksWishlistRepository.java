package gb.library.backend.common.repositories;


import gb.library.common.entities.BooksWishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksWishlistRepository extends JpaRepository<BooksWishlist, Integer> {

    Optional<BooksWishlist> findByUserAndBook(Integer readerId, Integer bookId);

    Page<BooksWishlist> findAllByUserId(Integer id, Pageable pageable);
}
