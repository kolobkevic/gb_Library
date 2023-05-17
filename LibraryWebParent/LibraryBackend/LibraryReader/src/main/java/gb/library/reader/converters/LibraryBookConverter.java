package gb.library.reader.converters;

import gb.library.common.entities.LibraryBook;
import gb.library.reader.dtos.LibraryBookDto;
import gb.library.reader.services.ReservedBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("readerLibraryBookConverter")
@RequiredArgsConstructor
public class LibraryBookConverter {
    private final ReservedBooksService service;

    public LibraryBookDto entityToDto(LibraryBook book) {
        return new LibraryBookDto(book.getId(), book.getInventoryNumber(), book.getPublisher(), book.getIsbn());
    }

    public LibraryBook dtoToEntity(LibraryBookDto libraryBookDto){
        LibraryBook book = service.findLibraryBookById(libraryBookDto.getLibraryBookId());
        book.setId(libraryBookDto.getLibraryBookId());
        book.setInventoryNumber(libraryBookDto.getInventoryNumber());
        book.setPublisher(libraryBookDto.getPublisher());
        book.setIsbn(libraryBookDto.getIsbn());
        return book;
    }
}
