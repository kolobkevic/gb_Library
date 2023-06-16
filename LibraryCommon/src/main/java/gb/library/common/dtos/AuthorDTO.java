package gb.library.common.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "firstName", "lastName"})
public class AuthorDTO {
    @Min(value = 0, message = "ID не может быть отрицательным")
    private int id;

    @NotNull(message = "Имя автора должно быть заполнено")
    @Length(min = 1, max = 45, message = "Имя автора должно состоять из 1-45 символов")
    private String firstName;

    @NotNull(message = "Фамилия автора должна быть указана")
    @Length(max = 60, message = "Фамилия автора не должна превышать 60 символов")
    private String lastName;

}
