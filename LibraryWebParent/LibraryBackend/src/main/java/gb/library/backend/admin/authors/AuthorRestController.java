package gb.library.backend.admin.authors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorService service;

    @PostMapping("/authors/check_unique")
    public String checkUnique(@Param("id") Integer id,
                              @Param("firstName") String firstName,
                              @Param("lastName") String lastName){
        return service.checkUnique(id, firstName, lastName);
    }
}
