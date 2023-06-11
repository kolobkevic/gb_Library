package gb.library.common.dtos;

import gb.library.common.entities.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean enabled;

    private Set<Role> roles = new HashSet<>();

    private LocalDate birthday;
    private String phone1;
    private String phone2;
    private String address;
    private String passport;
}
