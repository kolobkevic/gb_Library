package gb.library.common.dtos;

import gb.library.common.entities.Storage;
import gb.library.common.entities.WorldBook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryBookDTO {
    private int id;
    private WorldBookDTO worldBookDTO;
    private String publisher;
    //Международный стандартный книжный номер (англ. International Standard Book Number, сокращённо — англ. ISBN)
    private String isbn;
    private String inventoryNumber;
    private boolean available;
    private StorageDTO placedAt;
}
