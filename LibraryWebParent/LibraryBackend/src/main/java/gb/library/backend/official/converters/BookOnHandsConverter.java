package gb.library.backend.official.converters;

import gb.library.backend.common.converters.LibraryBookConverter;
import gb.library.backend.common.converters.WorldBookConverter;
import gb.library.backend.official.dtos.BookOnHandsDTO;
import gb.library.common.entities.BookOnHands;
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
                book.getTakenAt(),
                book.isReturned());
    }

    public BookOnHands dtoToEntity(BookOnHandsDTO bookOnHandsDTO){
        BookOnHands book = new BookOnHands();
        book.setId(bookOnHandsDTO.getId());
        book.setBook(libraryBookReaderConverter.dtoToEntity(bookOnHandsDTO.getLibraryBookDTO()));
        book.setUser(userConverter.dtoToEntity(bookOnHandsDTO.getUserDTO()));
        book.setTakenAt(bookOnHandsDTO.getTakenAt());
        return book;
    }
}
