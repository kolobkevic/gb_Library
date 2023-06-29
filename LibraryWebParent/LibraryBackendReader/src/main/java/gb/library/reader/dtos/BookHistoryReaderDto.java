package gb.library.reader.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import gb.library.common.dtos.WorldBookDTO;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookHistoryReaderDto {
    private int bookOnHandsId;
    private LibraryBookReaderDto libraryBook;
    private UserReaderDto user;
    private WorldBookDTO worldBook;
    private LocalDate takenAt;
    private LocalDate dateOfReturn;
    private boolean isReturned;
}
