package gb.library.common.dtos;

import gb.library.common.entities.AgeRating;
import gb.library.common.entities.Author;
import gb.library.common.entities.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorldBookDTO {
    private int id;
    private String title;
    private Author author;
    private Genre genre;
    private AgeRating ageRating;
    private String description;
}
