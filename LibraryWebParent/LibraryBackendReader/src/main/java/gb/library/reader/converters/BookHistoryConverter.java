package gb.library.reader.converters;

import gb.library.common.entities.BookOnHands;
import gb.library.reader.dtos.BookHistoryReaderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import gb.library.backend.converters.WorldBookConverter;

import static gb.library.reader.services.BookHistoryService.DAYS_TO_RETURN;


@Component("readerBookOnHandsConverter")
@RequiredArgsConstructor
public class BookHistoryConverter {
    private final LibraryBookReaderConverter libraryBookReaderConverter;
    private final WorldBookConverter worldBookConverter;
    private final UserConverter userConverter;

    public BookHistoryReaderDto entityToDto(BookOnHands book){
        return new BookHistoryReaderDto(book.getId(), libraryBookReaderConverter.entityToDto(book.getBook()),
                userConverter.entityToDto(book.getUser()), worldBookConverter.entityToDto(book.getBook().getWorldBook()),
                book.getTakenAt(), book.getTakenAt().plusDays(DAYS_TO_RETURN), book.isReturned());
    }

    public BookOnHands dtoToEntity(BookHistoryReaderDto bookDto){
        BookOnHands book = new BookOnHands();
        book.setId(bookDto.getBookOnHandsId());
        book.setBook(libraryBookReaderConverter.dtoToEntity(bookDto.getLibraryBook()));
        book.setUser(userConverter.dtoToEntity(bookDto.getUser()));
        book.getBook().setWorldBook(worldBookConverter.dtoToEntity(bookDto.getWorldBook()));
        book.setTakenAt(bookDto.getTakenAt());
        book.setReturned(bookDto.isReturned());
        return book;
    }
}
