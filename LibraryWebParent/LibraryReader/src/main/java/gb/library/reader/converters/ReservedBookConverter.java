package gb.library.reader.converters;

import gb.library.common.entities.ReservedBook;
import gb.library.reader.dtos.ReservedBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservedBookConverter {
    private final LibraryBookConverter libraryBookConverter;
    private final WorldBookConverter worldBookConverter;
    private final UserConverter userConverter;

    public ReservedBookDto entityToDto(ReservedBook book) {
        return new ReservedBookDto(book.getId(), userConverter.entityToDto(book.getUser()),
                libraryBookConverter.entityToDto(book.getLibraryBook()),
                worldBookConverter.entityToDto(book.getWorldBook()));
    }

    public ReservedBook DtoToEntity(ReservedBookDto reservedBookDto) {
        ReservedBook book = new ReservedBook();
        book.setId(reservedBookDto.getId());
        book.setWorldBook(worldBookConverter.dtoToEntity(reservedBookDto.getWorldBook()));
        book.setLibraryBook(libraryBookConverter.dtoToEntity(reservedBookDto.getLibraryBook()));
        book.setUser(userConverter.dtoToEntity(reservedBookDto.getUser()));
        return book;
    }
}
