package gb.library.common.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryBookDTO {

    @Min(value = 0, message = "ID не может быть отрицательным")
    private int id;

    @Valid
    private WorldBookDTO worldBookDTO;

    @NotNull(message = "Имя издателя должно быть заполнено")
    @Length(min = 3, max = 40, message = "Имя издателя должно состоять из 3-40 символов")
    private String publisher;

    //Международный стандартный книжный номер (англ. International Standard Book Number, сокращённо — англ. ISBN)
    @NotNull(message = "ISBN должен быть заполнен")
    @Length(min = 13, max = 13, message = "Длина ISBN кода равна 13 символов")
    private String isbn;

    @NotNull(message = "Инвентарный номер должен быть заполнен")
    @Length(min = 20, max = 20, message = "Длина инвентарного номера равна 20 символам")
    private String inventoryNumber;

    private boolean available;

    @Valid
    private StorageDTO placedAt;
}
