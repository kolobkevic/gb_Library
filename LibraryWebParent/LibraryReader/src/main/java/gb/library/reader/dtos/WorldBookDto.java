package gb.library.reader.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WorldBookDto {
    private int worldBookId;
    private String author;
    private String title;
    private String genre;
    private String ageRating;
    private String description;
}
