package gb.library.reader.converters;

import gb.library.common.entities.LibraryBook;
import gb.library.common.entities.ReservedBook;
import gb.library.common.entities.User;
import gb.library.common.entities.WorldBook;
import gb.library.reader.dtos.ReservedBookDto;
import gb.library.reader.temp.LibraryBookService;
import gb.library.reader.temp.UserService;
import gb.library.reader.temp.WorldBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservedBookConverter {
    private final UserService userService;
    private final WorldBookService worldBookService;
    private final LibraryBookService libraryBookService;

    public ReservedBookDto entityToDto(ReservedBook book) {
        return new ReservedBookDto(book.getId(), book.getUser().getId(), book.getLibraryBook().getId(),
                book.getWorldBook().getId(), book.getWorldBook().getTitle(), book.getLibraryBook().getInventoryNumber(),
                book.getUser().getEmail(), book.getWorldBook().getAuthor().getFirstName() + " " +
                book.getWorldBook().getAuthor().getLastName(), book.getCreatedAt());
    }

    public ReservedBook DtoToEntity(ReservedBookDto reservedBookDto){
        ReservedBook book = new ReservedBook();
        book.setId(reservedBookDto.getId());
        book.setUser(buildUser(reservedBookDto.getUserId(), reservedBookDto.getUsername()));
        book.setWorldBook(buildWorldBook(reservedBookDto.getWorldBookId(), reservedBookDto.getBookTitle(),
                reservedBookDto.getAuthor()));
        book.setLibraryBook(buildLibraryBook(reservedBookDto.getLibraryBookId(), reservedBookDto.getInventoryNumber()));
        return book;
    }

    public User buildUser(Integer id, String username){
        User user = userService.findById(id);
        user.setEmail(username);
        return user;
    }

    public WorldBook buildWorldBook(Integer id, String title, String author){
        WorldBook worldBook = worldBookService.findById(id);
        worldBook.setTitle(title);
        worldBook.getAuthor().setFirstName(author.split(" ")[0]);
        worldBook.getAuthor().setLastName(author.split(" ")[1]);
        return worldBook;
    }

    public LibraryBook buildLibraryBook(Integer id, String inventoryNumber){
        LibraryBook libraryBook = libraryBookService.findById(id);
        libraryBook.setInventoryNumber(inventoryNumber);
        return libraryBook;
    }
}
