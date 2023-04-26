package gb.library.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenreDTO {
    private int id;
    private String name;
    private String description;
}
