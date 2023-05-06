package gb.library.reader.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReservedBookDto {
    private int id;
    private UserDto user;
    private LibraryBookDto libraryBook;
    private WorldBookDto worldBook;
}