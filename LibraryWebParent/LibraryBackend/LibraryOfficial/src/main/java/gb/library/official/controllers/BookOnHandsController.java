package gb.library.official.controllers;


import gb.library.official.converters.BookOnHandsConverter;
import gb.library.official.dtos.BookOnHandsDTO;
import gb.library.official.services.BookOnHandsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book_on_hands")
@RequiredArgsConstructor
public class BookOnHandsController {
    private final BookOnHandsService bookOnHandsService;
    private final BookOnHandsConverter bookOnHandsConverter;

    @GetMapping
    public List<BookOnHandsDTO> findAll() {
        return bookOnHandsService.findAll().stream().map(bookOnHandsConverter::entityToDto).toList();
    }

    @PostMapping
    public BookOnHandsDTO lendOutBook(@RequestBody @Valid BookOnHandsDTO bookOnHandsDTO) {
        return bookOnHandsConverter.entityToDto(bookOnHandsService.lendOutBook(bookOnHandsConverter.dtoToEntity(bookOnHandsDTO)));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        bookOnHandsService.deleteById(id);
    }
}
