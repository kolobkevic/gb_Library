package gb.library.reader.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LibraryBookDto {
    private int libraryBookId;
    private String inventoryNumber;
}
