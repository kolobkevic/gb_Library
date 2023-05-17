package gb.library.common.dtos;

import gb.library.common.entities.Storage;
import gb.library.common.entities.WorldBook;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLibraryBookDTO {
    private WorldBook worldBook;
    private String publisher;
    //Международный стандартный книжный номер (англ. International Standard Book Number, сокращённо — англ. ISBN)
    private String isbn;
    private String inventoryNumber;
    private boolean available;
    private Storage placedAt;
}
