package gb.library.backend.common.repositories;

import gb.library.common.entities.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface LibraryBookRepository extends JpaRepository<LibraryBook, Integer> , JpaSpecificationExecutor<LibraryBook> {
    Optional<LibraryBook> findByInventoryNumber(String inventoryNumber);

    List<LibraryBook> findAllByWorldBookId(Integer id);
}

