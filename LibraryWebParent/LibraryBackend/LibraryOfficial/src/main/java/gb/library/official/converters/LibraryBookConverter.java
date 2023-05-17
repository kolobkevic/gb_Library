package gb.library.official.converters;

import gb.library.common.dtos.AddLibraryBookDTO;
import gb.library.common.dtos.LibraryBookDTO;
import gb.library.common.dtos.StorageDTO;
import gb.library.common.entities.LibraryBook;
import org.springframework.stereotype.Component;

@Component
public class LibraryBookConverter {

    public LibraryBookDTO entityToDto(LibraryBook libraryBook){
        return new LibraryBookDTO(libraryBook.getWorldBook(),
                libraryBook.getPublisher(),
                libraryBook.getIsbn(),
                libraryBook.getInventoryNumber(),
                libraryBook.isAvailable(),
                new StorageDTO(libraryBook.getPlacedAt().getZone(), libraryBook.getPlacedAt().getSector() )

        );

    }
    public LibraryBook dtoToEntity(AddLibraryBookDTO libraryBookDTO){
        LibraryBook libraryBook =new LibraryBook();

        libraryBook.setWorldBook(libraryBookDTO.getWorldBook());

        libraryBook.setPublisher(libraryBookDTO.getPublisher());
        libraryBook.setIsbn(libraryBookDTO.getIsbn());
        libraryBook.setInventoryNumber(libraryBookDTO.getInventoryNumber());
        libraryBook.setAvailable(libraryBookDTO.isAvailable());
        libraryBook.setPlacedAt(libraryBookDTO.getPlacedAt());
        return libraryBook;

    }


}
