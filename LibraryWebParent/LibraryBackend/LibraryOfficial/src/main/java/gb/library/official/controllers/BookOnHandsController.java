package gb.library.official.controllers;


import gb.library.official.converters.BookOnHandsConverter;
import gb.library.official.dtos.BookOnHandsDTO;
import gb.library.official.services.BookOnHandsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/book_hands")
@RequiredArgsConstructor
public class BookOnHandsController {
    private final BookOnHandsService bookOnHandsService;
    private final BookOnHandsConverter bookOnHandsConverter;

    @GetMapping
    public Page<BookOnHandsDTO> findAll(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageIndex,
                                        @RequestParam(name = "pages", defaultValue = "10", required = false) Integer pages,
                                        @RequestParam(name = "st", required = false) String searchText,
                                        @RequestParam(name = "a", required = false) Integer returnStatus) {
        return bookOnHandsService.findAll(pageIndex, pages, searchText, returnStatus).map(bookOnHandsConverter::entityToDto);
    }

    @GetMapping("/{id}")
    public BookOnHandsDTO findById(@PathVariable Integer id) {
        return bookOnHandsConverter.entityToDto(bookOnHandsService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookOnHandsDTO save(@RequestBody @Valid BookOnHandsDTO bookOnHandsDTO) {
        return bookOnHandsConverter.entityToDto(bookOnHandsService.lendOutBook(bookOnHandsConverter.dtoToEntity(bookOnHandsDTO)));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BookOnHandsDTO update(@RequestBody @Valid BookOnHandsDTO bookOnHandsDTO) {
        return bookOnHandsConverter.entityToDto(bookOnHandsService.updateOrder(bookOnHandsConverter.dtoToEntity(bookOnHandsDTO)));
    }
}
