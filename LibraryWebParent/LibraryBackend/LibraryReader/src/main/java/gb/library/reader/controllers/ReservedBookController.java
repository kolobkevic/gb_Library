package gb.library.reader.controllers;

import gb.library.common.entities.ReservedBook;
import gb.library.reader.converters.ReservedBookConverter;
import gb.library.reader.dtos.ReservedBookReaderDto;
import gb.library.reader.services.ReservedBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reserved")
@CrossOrigin
public class ReservedBookController {
    private final ReservedBooksService reservedBooksService;
    private final ReservedBookConverter converter;

    @GetMapping("/{userId}")
    public Page<ReservedBookReaderDto> findAll(@RequestParam(name = "page") int pageIndex,
                                               @PathVariable int userId) {
        return reservedBooksService.getAllPageable(userId, pageIndex).map(converter::entityToDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFromReserved(@PathVariable int id) {
        reservedBooksService.delete(id);
    }

    @PostMapping("/create")
    public ReservedBookReaderDto reserveBook(@RequestBody ReservedBookReaderDto reservedBookDto) {
        ReservedBook book = reservedBooksService.create(converter.DtoToEntity(reservedBookDto));
        return converter.entityToDto(book);
    }
}
