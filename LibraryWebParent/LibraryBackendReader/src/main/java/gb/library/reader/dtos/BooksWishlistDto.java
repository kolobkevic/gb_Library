package gb.library.reader.dtos;

import gb.library.common.dtos.WorldBookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BooksWishlistDto {
    private Integer id;
    private WorldBookDTO book;
    private UserReaderDto user;
}
