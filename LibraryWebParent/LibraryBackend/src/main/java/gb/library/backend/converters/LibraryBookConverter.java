package gb.library.backend.converters;

import gb.library.backend.services.StorageCommonService;
import gb.library.backend.services.WorldBookCommonService;
import gb.library.common.dtos.LibraryBookDTO;
import gb.library.common.dtos.StorageDTO;
import gb.library.common.entities.LibraryBook;
import gb.library.common.entities.WorldBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LibraryBookConverter {

    private final StorageCommonService storageService;
    private final WorldBookConverter worldBookConverter;
    private final WorldBookCommonService worldBookCommonService;

    public LibraryBookDTO entityToDto(LibraryBook libraryBook) {
        WorldBook worldBook = worldBookCommonService.findById(libraryBook.getWorldBook().getId());
        return new LibraryBookDTO(libraryBook.getId(),
                worldBookConverter.entityToDto(worldBook),
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
        libraryBook.setPlacedAt(storageService.findBySectorAndZone(libraryBookDTO.getPlacedAt().getSector(),libraryBookDTO.getPlacedAt().getZone()));
        return libraryBook;

    }


}
