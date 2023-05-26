package gb.library.reader.services;

import gb.library.backend.repositories.LibraryBookRepository;
import gb.library.backend.services.LibraryBookCommonService;
import gb.library.common.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibraryBookServiceTest {

    @InjectMocks
    private LibraryBookCommonService service;

    @Mock
    LibraryBookRepository repository;

    private final List<LibraryBook> libraryBooks = new ArrayList<>();

    @BeforeEach
    public void init() {
        LibraryBook book = new LibraryBook();
        book.setWorldBook(new WorldBook());
        book.setInventoryNumber("Inventory number 1");
        book.setIsbn("1234567898765");
        book.setPublisher("Publisher");
        book.setPlacedAt(new Storage());
        book.setAvailable(true);
        book.setId(1);
        libraryBooks.add(book);

        book = new LibraryBook();
        book.setWorldBook(new WorldBook());
        book.setInventoryNumber("Inventory number 2");
        book.setIsbn("1234567898764");
        book.setPublisher("Publisher 2");
        book.setPlacedAt(new Storage());
        book.setAvailable(true);
        book.setId(2);
        libraryBooks.add(book);

        book = new LibraryBook();
        book.setWorldBook(new WorldBook());
        book.setInventoryNumber("Inventory number 3");
        book.setIsbn("1111111111111");
        book.setPublisher("Publisher 3");
        book.setPlacedAt(new Storage());
        book.setAvailable(true);
        book.setId(20);
        libraryBooks.add(book);
    }

    @Test
    public void TestFindById() {
        for (LibraryBook libraryBook : libraryBooks) {
            when(repository.findById(libraryBook.getId())).thenReturn(Optional.of(libraryBook));
        }

        for (LibraryBook libraryBook : libraryBooks) {
            Assertions.assertEquals(libraryBook.getId(), service.findById(libraryBook.getId()).getId());
            Assertions.assertEquals(libraryBook.getPublisher(), service.findById(libraryBook.getId()).getPublisher());
            Assertions.assertEquals(libraryBook.getInventoryNumber(), service.findById(libraryBook.getId()).getInventoryNumber());
        }

    }
}
