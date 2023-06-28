package gb.library.official.controllers;


import gb.library.common.dtos.LibraryBookDTO;
import gb.library.backend.converters.LibraryBookConverter;
import gb.library.official.services.LibraryBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/libraryBook")
@CrossOrigin
public class LibraryBookController {
    private final LibraryBookService libraryBookService;
    private final LibraryBookConverter converter;

    private static final int DEFAULT_PAGE_SIZE = 10;

    @GetMapping
    public Page<LibraryBookDTO> findAll(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false, name = "title") String title,
                                        @RequestParam(required = false, name = "author") String author,
                                        @RequestParam(required = false, name = "genre") String genre,
                                        @RequestParam(required = false, name = "ageRating") String ageRating,
                                        @RequestParam(required = false, name = "description") String description) {

        if (pageSize == null || pageSize < 1) pageSize = DEFAULT_PAGE_SIZE;
        if (page < 1) page = 1;

        return libraryBookService.findAll(page, pageSize, title, author, genre, ageRating, description).map(converter::entityToDto);
    }

    @GetMapping("/{id}")
    public LibraryBookDTO findById(@PathVariable Integer id) {
        return converter.entityToDto(libraryBookService.findById(id));
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        libraryBookService.deleteById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public LibraryBookDTO update(@RequestBody @Valid LibraryBookDTO libraryBook) {
        return converter.entityToDto(libraryBookService.update(converter.dtoToEntity(libraryBook)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LibraryBookDTO saveNewLibraryBook(@RequestBody @Valid LibraryBookDTO libraryBook) {
        return converter.entityToDto(libraryBookService.save(converter.dtoToEntity(libraryBook)));
    }
}
