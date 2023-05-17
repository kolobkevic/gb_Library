package gb.library.reader.controllers;


import gb.library.common.entities.WorldBook;
import gb.library.reader.converters.WorldBookConverter;
import gb.library.reader.dtos.WorldBookDto;
import gb.library.reader.repositories.specifications.WorldBookSpecification;
import gb.library.reader.services.BooksCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
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
                                      @RequestParam(name = "search_text", required = false) String searchText,
                                      @RequestParam(name = "genre", required = false) String bookGenre,
                                      @RequestParam(name = "age_rate", required = false) String bookAgeRate) {

        if (pageIndex < 1) pageIndex = 1;
        Specification<WorldBook> specification = Specification.where(null);

        if (searchText != null) {
            specification = specification.or(WorldBookSpecification.titleLike(searchText));
            specification = specification.or(WorldBookSpecification.authorFirstNameLike(searchText));
            specification = specification.or(WorldBookSpecification.authorSecondNameLike(searchText));
            specification = specification.or(WorldBookSpecification.genreLike(searchText));
//            specification = specification.or(WorldBookSpecification.ageRatingLike(searchText));
        }
        if (bookGenre != null) specification = specification.or(WorldBookSpecification.genreLike(bookGenre));
//        if (bookAgeRate != null) specification = specification.or(WorldBookSpecification.ageRatingLike(bookAgeRate));
        return booksCatalogService.findAll(pageIndex - 1, pageSize, specification).map(worldBookConverter::entityToDto);
    }
}
