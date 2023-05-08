package gb.library.reader.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookOnHandsDto {
    private int bookOnHandsId;
    private LibraryBookDto libraryBook;
    private UserDto user;
    private WorldBookDto worldBook;
    private LocalDate takenAt;
    private LocalDate dateOfReturn;
}
