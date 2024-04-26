package gb.library.backend.admin.books.global;

import gb.library.common.entities.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorldBooksRestController {

    private final WorldBooksService service;

    @PostMapping("/world-books/check_unique")
    public String checkUnique(@Param("id") Integer id,
                              @Param("title") String title,
                              @Param("author") Author author){
        return service.checkUnique(id, title, author);
    }
}
