package gb.library.reader.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import gb.library.common.dtos.LibraryBookDTO;
import gb.library.common.dtos.WorldBookDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonPropertyOrder({"id", "user", "library_book", "world_book"})
public class ReservedBookReaderDto {

    @Min(value = 1, message = "ID бронируемой книги должен быть больше 0")
    private int id;

    @NotNull(message = "Данные о пользователе не заполнены.")
    @Valid
    private UserReaderDto user;

    @JsonProperty("library_book")
    @NotNull(message = "Данные экземпляра книги (Library Book) не заполнены.")
    @Valid
    private LibraryBookDTO libraryBook;

    @JsonProperty("world_book")
    @NotNull(message = "Общие данные о книге (World book) не заполнены")
    @Valid
    private WorldBookDTO worldBook;
}