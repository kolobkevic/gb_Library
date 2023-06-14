package gb.library.backend.converters;

import gb.library.common.dtos.LibraryBookDTO;
import gb.library.common.dtos.StorageDTO;
import gb.library.common.dtos.WorldBookDTO;
import gb.library.common.entities.LibraryBook;
import gb.library.common.entities.Storage;
import gb.library.common.entities.WorldBook;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LibraryBookConverter {

    private final ModelMapper mapper;


    public LibraryBookDTO entityToDto(LibraryBook libraryBook) {

        LibraryBookDTO dto = mapper.map(libraryBook, LibraryBookDTO.class);
        dto.setWorldBookDTO(mapper.map(libraryBook.getWorldBook(), WorldBookDTO.class));
        dto.setPlacedAt(mapper.map(libraryBook.getPlacedAt(), StorageDTO.class));

        return dto;
    }

    public LibraryBook dtoToEntity(LibraryBookDTO dto) {
        LibraryBook book = mapper.map(dto, LibraryBook.class);
        book.setWorldBook(mapper.map(dto.getWorldBookDTO(), WorldBook.class));
        book.setPlacedAt(mapper.map(dto.getPlacedAt(), Storage.class));

        return book;
    }
}
