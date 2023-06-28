package gb.library.reader.converters;

import gb.library.backend.converters.LibraryBookConverter;
import gb.library.backend.converters.WorldBookConverter;
import gb.library.common.entities.ReservedBook;
import gb.library.reader.dtos.ReservedBookReaderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservedBookConverter {
    private final LibraryBookConverter libraryBookConverter;
    private final WorldBookConverter worldBookConverter;
    private final UserConverter userConverter;

    public ReservedBookReaderDto entityToDto(ReservedBook book) {
        return new ReservedBookReaderDto(book.getId(), userConverter.entityToDto(book.getUser()),
                libraryBookConverter.entityToDto(book.getLibraryBook()),
                worldBookConverter.entityToDto(book.getWorldBook()));
    }

    public ReservedBook DtoToEntity(ReservedBookReaderDto reservedBookDto) {
        ReservedBook book = new ReservedBook();
        book.setId(reservedBookDto.getId());
        book.setWorldBook(worldBookConverter.dtoToEntity(reservedBookDto.getWorldBook()));
        book.setLibraryBook(libraryBookConverter.dtoToEntity(reservedBookDto.getLibraryBook()));
        book.setUser(userConverter.dtoToEntity(reservedBookDto.getUser()));
        return book;
    }
}
