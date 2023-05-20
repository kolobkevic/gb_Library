package gb.library.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorDTO {
    private int id;
    private String firstName;
    private String lastName;
}
