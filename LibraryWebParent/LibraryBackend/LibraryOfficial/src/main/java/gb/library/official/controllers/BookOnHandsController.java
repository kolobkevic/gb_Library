package gb.library.official.controllers;


import gb.library.official.converters.BookOnHandsConverter;
import gb.library.official.dtos.BookOnHandsDTO;
import gb.library.official.services.BookOnHandsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book_hands")
@RequiredArgsConstructor
public class BookOnHandsController {
    private final BookOnHandsService bookOnHandsService;
    private final BookOnHandsConverter bookOnHandsConverter;

    @GetMapping
    public List<BookOnHandsDTO> findAll() {
        return bookOnHandsService.findAll().stream().map(bookOnHandsConverter::entityToDto).toList();
    }

    @PostMapping
    public BookOnHandsDTO save(@RequestBody @Valid BookOnHandsDTO bookOnHandsDTO) {
        return bookOnHandsConverter.entityToDto(bookOnHandsService.lendOutBook(bookOnHandsConverter.dtoToEntity(bookOnHandsDTO)));
    }
}
