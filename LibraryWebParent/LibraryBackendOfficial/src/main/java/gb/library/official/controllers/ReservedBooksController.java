package gb.library.official.controllers;


import gb.library.official.converters.ReservedBooksConverter;
import gb.library.official.dtos.ReservedBookDTO;
import gb.library.official.services.ReservedBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reserved_books")
@CrossOrigin
@RequiredArgsConstructor
public class ReservedBooksController {
    private final ReservedBooksService reservedBooksService;
    private final ReservedBooksConverter reservedBooksConverter;

    @GetMapping
    public Page<ReservedBookDTO> findAll(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageIndex,
                                         @RequestParam(name = "pages", defaultValue = "10", required = false) Integer pages,
                                         @RequestParam(name = "search", required = false) String searchText) {
        return reservedBooksService.findAll(pageIndex, pages, searchText).map(reservedBooksConverter::entityToDto);
    }

    @PostMapping
    public ReservedBookDTO createNewOrder(@RequestBody ReservedBookDTO reservedBookDTO) {
        return reservedBooksConverter.entityToDto(reservedBooksService.createNewOrder(reservedBooksConverter.DtoToEntity(reservedBookDTO)));
    }

    @PutMapping
    public ReservedBookDTO updateOrder(@RequestBody ReservedBookDTO reservedBookDTO) {
        return reservedBooksConverter.entityToDto(reservedBooksService.updateOrder(reservedBooksConverter.DtoToEntity(reservedBookDTO)));
    }

    @GetMapping("/{id}")
    public ReservedBookDTO findById(@PathVariable Integer id) {
        return reservedBooksConverter.entityToDto(reservedBooksService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        reservedBooksService.deleteById(id);
    }
}
