package gb.library.admin.books.local;

import gb.library.admin.utils.paging.SearchRepository;
import gb.library.common.entities.LibraryBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBooksRepository extends SearchRepository<LibraryBook, Integer> {

    LibraryBook save(LibraryBook libraryBook);

    @Query("SELECT lb FROM LibraryBook lb WHERE lb.inventoryNumber LIKE %?1% OR lb.publisher LIKE %?1%"
            + " OR lb.worldBook.title LIKE %?1% OR lb.worldBook.author.firstName LIKE %?1%"
            + " OR lb.worldBook.author.lastName LIKE %?1% OR lb.worldBook.genre.name LIKE %?1%")
    Page<LibraryBook> findAll(String keyword, Pageable pageable);
}
