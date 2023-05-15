package gb.library.reader.controllers;


import gb.library.official.repositories.WorldBookRepository;
import gb.library.reader.converters.WorldBookConverter;
import gb.library.reader.dtos.WorldBookDto;
import gb.library.reader.services.BooksCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/books_catalog")
@RequiredArgsConstructor
public class BooksCatalogController {
    private final BooksCatalogService booksCatalogService;
    private final WorldBookConverter worldBookConverter;

    @GetMapping
    public Page<WorldBookDto> findAll(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageIndex,
                                      @RequestParam(name = "page_size", defaultValue = "10", required = false) Integer pageSize,
                                      @RequestParam(name = "genre", required = false) String genre,
                                      @RequestParam(name = "age_rating", required = false) String ageRating) {
        if (pageIndex < 1) pageIndex = 1;
        return booksCatalogService.findAll(pageIndex - 1, pageSize).map(worldBookConverter::entityToDto);
    }
}
