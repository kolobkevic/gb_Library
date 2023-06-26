package gb.library.official.converters;

import gb.library.backend.converters.LibraryBookConverter;
import gb.library.backend.converters.WorldBookConverter;
import gb.library.common.entities.BookOnHands;
import gb.library.official.dtos.BookOnHandsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookOnHandsConverter {
    private final LibraryBookConverter libraryBookReaderConverter;
    private final WorldBookConverter worldBookConverter;
    private final UserConverter userConverter;

    public BookOnHandsDTO entityToDto(BookOnHands book){
        return new BookOnHandsDTO(
                book.getId(),
                libraryBookReaderConverter.entityToDto(book.getBook()),
                userConverter.entityToDto(book.getUser()),
                worldBookConverter.entityToDto(book.getBook().getWorldBook()),
                book.getTakenAt(),
                book.getTakenAt().plusDays(30),
                book.isReturned());
    }

    public BookOnHands dtoToEntity(BookOnHandsDTO bookOnHandsDTO){
        BookOnHands book = new BookOnHands();
        book.setId(bookOnHandsDTO.getId());
        book.setBook(libraryBookReaderConverter.dtoToEntity(bookOnHandsDTO.getLibraryBookDTO()));
        book.setUser(userConverter.dtoToEntity(bookOnHandsDTO.getUserDTO()));
        book.getBook().setWorldBook(worldBookConverter.dtoToEntity(bookOnHandsDTO.getWorldBookDTO()));
        book.setTakenAt(bookOnHandsDTO.getTakenAt());
        book.setReturned(bookOnHandsDTO.isReturned());
        return book;
    }
}
