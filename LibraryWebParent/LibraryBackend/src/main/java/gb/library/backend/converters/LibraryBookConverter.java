package gb.library.backend.converters;

import gb.library.backend.services.StorageCommonService;
import gb.library.common.dtos.LibraryBookDTO;
import gb.library.common.dtos.StorageDTO;
import gb.library.common.dtos.WorldBookDTO;
import gb.library.common.entities.LibraryBook;
import gb.library.common.entities.WorldBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LibraryBookConverter {

    private final StorageCommonService storageService;
    private final WorldBookConverter worldBookConverter;

    public LibraryBookDTO entityToDto(LibraryBook libraryBook) {
        return new LibraryBookDTO(worldBookConverter.entityToDto(libraryBook.getWorldBook()),
                libraryBook.getPublisher(),
                libraryBook.getIsbn(),
                libraryBook.getInventoryNumber(),
                libraryBook.isAvailable(),
                new StorageDTO(libraryBook.getPlacedAt().getId(), libraryBook.getPlacedAt().getZone(), libraryBook.getPlacedAt().getSector())

        );

    }

    public LibraryBook dtoToEntity(LibraryBookDTO libraryBookDTO) {
        LibraryBook libraryBook = new LibraryBook();
        WorldBook worldBook = new WorldBook();
        worldBook.setId(libraryBookDTO.getWorldBookDTO().getId());
        libraryBook.setWorldBook(worldBook);

        libraryBook.setPublisher(libraryBookDTO.getPublisher());
        libraryBook.setIsbn(libraryBookDTO.getIsbn());
        libraryBook.setInventoryNumber(libraryBookDTO.getInventoryNumber());
        libraryBook.setAvailable(libraryBookDTO.isAvailable());
        libraryBook.setPlacedAt(storageService.findById((libraryBookDTO.getPlacedAt().getId())));
        return libraryBook;

    }


}
