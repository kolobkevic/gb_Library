package gb.library.backend.converters;

import gb.library.backend.services.StorageCommonService;
import gb.library.common.dtos.LibraryBookDTO;
import gb.library.common.dtos.StorageDTO;
import gb.library.common.entities.LibraryBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LibraryBookConverter {

    private final StorageCommonService storageService;

    public LibraryBookDTO entityToDto(LibraryBook libraryBook){
        return new LibraryBookDTO(libraryBook.getWorldBook(),
                libraryBook.getPublisher(),
                libraryBook.getIsbn(),
                libraryBook.getInventoryNumber(),
                libraryBook.isAvailable(),
                new StorageDTO(libraryBook.getPlacedAt().getZone(), libraryBook.getPlacedAt().getSector() )

        );

    }
    public LibraryBook dtoToEntity(LibraryBookDTO libraryBookDTO){
        LibraryBook libraryBook =new LibraryBook();

        libraryBook.setWorldBook(libraryBookDTO.getWorldBook());

        libraryBook.setPublisher(libraryBookDTO.getPublisher());
        libraryBook.setIsbn(libraryBookDTO.getIsbn());
        libraryBook.setInventoryNumber(libraryBookDTO.getInventoryNumber());
        libraryBook.setAvailable(libraryBookDTO.isAvailable());
        libraryBook.setPlacedAt(storageService.findBySectorAndZone(libraryBookDTO.getPlacedAt().getSector(),libraryBookDTO.getPlacedAt().getZone()));
        return libraryBook;

    }


}
