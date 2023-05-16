package gb.library.admin.books.local;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LibraryBooksRestController {

    private final LibraryBooksService service;

    @PostMapping("/library-books/check_unique")
    public String checkUnique(@Param("id") Integer id,
                              @Param("title") String invNumber){
        return service.checkUnique(id, invNumber);
    }
}
