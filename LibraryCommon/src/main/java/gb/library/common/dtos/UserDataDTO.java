package gb.library.common.dtos;

import gb.library.common.entities.Role;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDataDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean enabled;

    private Set<Role> roles = new HashSet<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String phone1;
    private String phone2;
    private String address;
    private String passport;
}
