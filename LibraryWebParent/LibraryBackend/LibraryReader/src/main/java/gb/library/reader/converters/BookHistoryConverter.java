package gb.library.reader.converters;

import gb.library.common.entities.BookOnHands;
import gb.library.reader.dtos.BookHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static gb.library.reader.services.BookHistoryService.DAYS_TO_RETURN;


@Component("readerBookOnHandsConverter")
@RequiredArgsConstructor
public class BookHistoryConverter {
    private final LibraryBookConverter libraryBookConverter;
    private final WorldBookConverter worldBookConverter;
    private final UserConverter userConverter;

    public BookHistoryDto entityToDto(BookOnHands book){
        return new BookHistoryDto(book.getId(), libraryBookConverter.entityToDto(book.getBook()),
                userConverter.entityToDto(book.getUser()), worldBookConverter.entityToDto(book.getBook().getWorldBook()),
                book.getTakenAt(), book.getTakenAt().plusDays(DAYS_TO_RETURN), book.isReturned());
    }

    public BookOnHands dtoToEntity(BookHistoryDto bookDto){
        BookOnHands book = new BookOnHands();
        book.setId(bookDto.getBookOnHandsId());
        book.setBook(libraryBookConverter.dtoToEntity(bookDto.getLibraryBook()));
        book.setUser(userConverter.dtoToEntity(bookDto.getUser()));
        book.getBook().setWorldBook(worldBookConverter.dtoToEntity(bookDto.getWorldBook()));
        book.setTakenAt(bookDto.getTakenAt());
        book.setReturned(bookDto.isReturned());
        return book;
    }
}
