package gb.library.admin.users;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersRestController {
    private final UsersService service;

    @PostMapping("/users/check_unique")
    public String checkUniqueEmail(@Param("id") Integer id, @Param("email") String email){
        return service.checkUnique(id, email);
    }
}
