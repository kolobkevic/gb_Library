package gb.library.admin.books.wishlist;

import gb.library.common.entities.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WishedBooksRestController {

    private final WishedBooksService service;

    @PostMapping("/wished-books/check_unique")
    public String checkUnique(@Param("id") Integer id,
                              @Param("title") String title,
                              @Param("author") Author author){
        return service.checkUnique(id, title, author);
    }
}
