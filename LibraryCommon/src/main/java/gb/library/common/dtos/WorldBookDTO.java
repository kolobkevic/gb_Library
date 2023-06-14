package gb.library.common.dtos;

import gb.library.common.enums.AgeRating;
import gb.library.common.enums.ValueOfEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorldBookDTO {

    @Min(value = 0, message = "ID не может быть отрицательным")
    private int id;

    @NotNull(message = "Название книги должно быть заполнено")
    @Length(min = 3, max = 128, message = "Название книги должно состоять из 1-128 символов")
    private String title;

    @Valid
    private AuthorDTO authorDTO;

    @Valid
    private GenreDTO genreDTO;

    @ValueOfEnum(enumClass = AgeRating.class, message = "Недопустимое значение возрастного рейтинга")
    private String ageRating;

    @Length(max = 250, message = "Максимальная длина описания книги - 250 символов")
    private String description;
}
