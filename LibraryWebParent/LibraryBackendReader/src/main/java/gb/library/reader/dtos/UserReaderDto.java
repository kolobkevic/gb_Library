package gb.library.reader.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@Data
@JsonPropertyOrder({"user_id", "email", "first_name", "last_name", "password"})
public class UserReaderDto {

    @Min(value = 1, message = "ID пользователя должен быть больше 0")
    @JsonProperty("user_id")
    private int userId;

    @NotNull(message = "Email не может быть пустым")
    @Length(min = 6, max = 128, message = "Email должен содержать 6-128 символов")
    private String email;

    @NotNull(message = "Имя не может быть равным NULL")
    @Length(min = 1, max = 45, message = "Имя должно содержать 1 - 45 символа")
    @JsonProperty("first_name")
    private String firstName;

    @NotNull(message = "Фамилия не может быть равной NULL")
    @Length(max = 60, message = "Фамилия может состоять максимум из 60 символов")
    @JsonProperty("last_name")
    private String lastName;

    private String password; //временное решение

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
