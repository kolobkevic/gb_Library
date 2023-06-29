package gb.library.reader.controllers;

import gb.library.common.entities.ReservedBook;
import gb.library.reader.converters.ReservedBookConverter;
import gb.library.reader.dtos.ReservedBookReaderDto;
import gb.library.reader.services.ReservedBooksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reserved")
@CrossOrigin("*")
public class ReservedBookController {
    private final ReservedBooksService reservedBooksService;
    private final ReservedBookConverter converter;

    @GetMapping
    public Page<ReservedBookReaderDto> findAllByUserLogin(@RequestParam(name = "p", defaultValue = "1", required = false) int pageIndex,
                                                          @RequestHeader String username) {
        return reservedBooksService.findAllByUserLogin(username, pageIndex).map(converter::entityToDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFromReserved(@PathVariable int id) {
        if (id < 1)
            return ResponseEntity.badRequest().build();

        reservedBooksService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ReservedBookReaderDto reserveBook(@RequestBody @Valid ReservedBookReaderDto reservedBookDto) {
        ReservedBook book = reservedBooksService.create(converter.DtoToEntity(reservedBookDto));
        return converter.entityToDto(book);
    }
}
