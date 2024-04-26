package gb.library.backend.admin.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GenreRestController {

    private final GenreService service;

    @PostMapping("/genres/check_unique")
    public String checkUnique(@Param("id") Integer id, @Param("name") String name){
        return service.checkUnique(id, name);
    }
}
