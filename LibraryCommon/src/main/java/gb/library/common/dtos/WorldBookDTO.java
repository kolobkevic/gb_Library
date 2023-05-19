package gb.library.common.dtos;

import gb.library.common.entities.AgeRating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorldBookDTO {
    private int id;
    private String title;
    private AuthorDTO authorDTO;
    private GenreDTO genreDTO;
    private AgeRating ageRating;
    private String description;
}
