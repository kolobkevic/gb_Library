package gb.library.official.repositories;

import gb.library.common.entities.LibraryBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LibraryBookRepository extends JpaRepository<LibraryBook, Integer> , JpaSpecificationExecutor<LibraryBook> {



}

