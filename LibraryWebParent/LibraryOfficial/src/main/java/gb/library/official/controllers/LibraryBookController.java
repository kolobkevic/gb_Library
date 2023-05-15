package gb.library.official.controllers;


import gb.library.common.dtos.AddLibraryBookDTO;
import gb.library.common.dtos.LibraryBookDTO;
import gb.library.common.entities.LibraryBook;
import gb.library.official.converters.LibraryBookConverter;
import gb.library.official.services.LibraryBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/libraryBook")
@CrossOrigin
public class LibraryBookController {
    private final LibraryBookService libraryBookService;
    private final LibraryBookConverter converter;

//    private final LibraryBookValidator validator;

    @GetMapping
    public Page<LibraryBookDTO> findAll(@RequestParam(required = false, name = "title") String title,
                                        @RequestParam(required = false, name = "author") String author,
                                        @RequestParam(required = false, name = "genre") String genre,
                                        @RequestParam(required = false, name = "ageRating") String ageRating,
                                        @RequestParam(required = false, name = "description") String description) {


        return libraryBookService.findAll().map(converter::entityToDto);
    }

    @GetMapping("/{id}")
    public LibraryBook findById(@PathVariable Integer id) {
        return libraryBookService.findById(id);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        libraryBookService.deleteById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public LibraryBookDTO update(@RequestBody AddLibraryBookDTO libraryBook) {
//        validator.validate(libraryBook);
        return converter.entityToDto(libraryBookService.update(converter.dtoToEntity(libraryBook)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LibraryBookDTO saveNewGenre(@RequestBody AddLibraryBookDTO libraryBook) {
//        validator.validate(libraryBook);
        return converter.entityToDto(libraryBookService.save(converter.dtoToEntity(libraryBook)));
    }
}
