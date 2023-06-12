package gb.library.common.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@ToString
public class GenreDTO {
    @Min(value = 0, message = "ID не может быть отрицательным")
    private int id;

    @NotNull(message = "Название жанра должно быть заполнено")
    @Length(min = 3, max = 40, message = "Название жанра должно состоять из 3-40 символов")
    private String name;

    @Length(max = 200, message = "Максимальная длина описания не должна превышать 200 символов")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
}
