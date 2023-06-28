package gb.library.reader.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LibraryBookReaderDto {
    private int libraryBookId;
    private String inventoryNumber;
    private String publisher;
    private String isbn;
}
