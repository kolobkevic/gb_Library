package gb.library.reader.dtos;

import gb.library.common.dtos.LibraryBookDTO;
import gb.library.common.dtos.WorldBookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReservedBookReaderDto {
    private int id;
    private UserReaderDto user;
    private LibraryBookDTO libraryBook;
    private WorldBookDTO worldBook;
}