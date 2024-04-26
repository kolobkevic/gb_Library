package gb.library.backend.admin.roles;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RolesRestController {
    private final RolesService service;

    @PostMapping("/roles/check_unique")
    public String checkUnique(@Param("id") Integer id,
                              @Param("name") String name,
                              @Param("type") String type){
        return service.checkUnique(id, name, type);
    }
}
