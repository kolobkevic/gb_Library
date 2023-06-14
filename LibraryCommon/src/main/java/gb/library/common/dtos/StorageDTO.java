package gb.library.common.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageDTO {

    @Min(value = 1, message = "ID должен иметь положительное значение")
    private Integer id;

    @NotNull(message = "Название зоны должно быть заполнено")
    @Length(min = 1, max = 40, message = "Название зоны должно содержать 1-40 символов")
    private String zone;

    @NotNull(message = "Название сектора должно быть заполнено")
    @Length(min = 1, max = 10, message = "Название сектора должно содержать 1-10 символов")
    private String sector;

    private boolean available;
}
