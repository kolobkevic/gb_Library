package gb.library.reader.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDto {
    private int userId;
    private String username;
    private String fullName;
}
