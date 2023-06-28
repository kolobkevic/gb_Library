package gb.library.reader.controllers;


import gb.library.backend.converters.WorldBookConverter;
import gb.library.common.dtos.WorldBookDTO;
import gb.library.reader.services.BooksCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/books_catalog")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BooksCatalogController {
    private final BooksCatalogService booksCatalogService;
    private final WorldBookConverter worldBookConverter;

    @GetMapping
    public Page<WorldBookDTO> findAll(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageIndex,
                                      @RequestParam(name = "page_size", defaultValue = "10", required = false) Integer pageSize,
                                      @RequestParam(name = "search_text", required = false) String searchText,
                                      @RequestParam(name = "chosen_genres", required = false) List<String> chosenGenres,
                                      @RequestParam(name = "chosen_age_rates", required = false) List<String> chosenAgeRates) {
        if (pageIndex < 1) pageIndex = 1;
        return booksCatalogService.findAll(pageIndex - 1, pageSize, searchText, chosenGenres, chosenAgeRates).map(worldBookConverter::entityToDto);
    }
}
