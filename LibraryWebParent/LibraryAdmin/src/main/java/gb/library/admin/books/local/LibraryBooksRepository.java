package gb.library.admin.books.local;

import gb.library.admin.utils.paging.SearchRepository;
import gb.library.common.entities.LibraryBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBooksRepository extends SearchRepository<LibraryBook, Integer> {

    LibraryBook save(LibraryBook libraryBook);

    @Query("SELECT lb FROM LibraryBook lb WHERE lb.inventoryNumber LIKE %?1% OR lb.publisher LIKE %?1%"
            + " OR lb.worldBook.title LIKE %?1% OR lb.worldBook.author.firstName LIKE %?1%"
            + " OR lb.worldBook.author.lastName LIKE %?1% OR lb.worldBook.genre.name LIKE %?1%")
    Page<LibraryBook> getAllWithFilter(String keyword, Pageable pageable);

    LibraryBook findByInventoryNumber(String inventoryNumber);

    @Modifying
    @Query("UPDATE LibraryBook lb SET lb.available = ?2 WHERE lb.id = ?1")
    void updateAvailableStatus(Integer id, boolean available);
}
