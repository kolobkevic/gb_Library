package gb.library.official.converters;

import gb.library.backend.converters.LibraryBookConverter;
import gb.library.backend.converters.WorldBookConverter;
import gb.library.common.entities.ReservedBook;
import gb.library.official.dtos.ReservedBookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservedBooksConverter {
    private final LibraryBookConverter libraryBookConverter;
    private final WorldBookConverter worldBookConverter;
    private final UserConverter userConverter;

    public ReservedBookDTO entityToDto(ReservedBook reservedBook) {
        return new ReservedBookDTO(reservedBook.getId(), userConverter.entityToDto(reservedBook.getUser()),
                libraryBookConverter.entityToDto(reservedBook.getLibraryBook()),
                worldBookConverter.entityToDto(reservedBook.getWorldBook()));
    }

    public ReservedBook DtoToEntity(ReservedBookDTO reservedBookDto) {
        ReservedBook reservedBook = new ReservedBook();
        reservedBook.setId(reservedBookDto.getId());
        reservedBook.setWorldBook(worldBookConverter.dtoToEntity(reservedBookDto.getWorldBookDTO()));
        reservedBook.setLibraryBook(libraryBookConverter.dtoToEntity(reservedBookDto.getLibraryBookDTO()));
        reservedBook.setUser(userConverter.dtoToEntity(reservedBookDto.getUserDTO()));
        return reservedBook;
    }
}
