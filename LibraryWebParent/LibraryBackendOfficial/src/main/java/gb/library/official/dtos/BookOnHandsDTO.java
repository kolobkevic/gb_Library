package gb.library.official.dtos;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import gb.library.common.dtos.LibraryBookDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonPropertyOrder({"id", "library_book", "user", "taken_at", "date_of_return", "is_returned"})
public class BookOnHandsDTO {
    private int id;
    @NotNull(message = "Не заполнена книга из резерва")
    @Valid
    private LibraryBookDTO libraryBookDTO;
    @NotNull(message = "Нет данных пользователя")
    @Valid
    private UserDTO userDTO;
    private LocalDate takenAt;
    private boolean isReturned;
}
