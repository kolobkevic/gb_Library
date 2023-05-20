package gb.library.reader.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserReaderDto {
    private int userId;
    private String email;
    private String firstName;
    private String lastName;
    private String password; //временное решение

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
