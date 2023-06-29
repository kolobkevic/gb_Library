package gb.library.official.controllers;

import gb.library.backend.converters.GenreConverter;
import gb.library.common.dtos.GenreDTO;
import gb.library.official.services.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genres")
@CrossOrigin
public class GenreController {
    private final GenreService genreService;
    private final GenreConverter converter;

    @GetMapping
    public List<GenreDTO> findAll(@RequestParam(required = false, name = "name") String name,
                               @RequestParam(required = false, name = "description") String description) {
        return converter.listEntity2Dto(genreService.findAll(name, description));
    }

    @GetMapping("/{id}")
    public GenreDTO findById(@PathVariable Integer id) {
        return converter.entityToDto(genreService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Integer id) {
        if (id < 1) return ResponseEntity.badRequest().build();

        genreService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateGenre(@RequestBody @Valid GenreDTO genreDTO) {
        return ResponseEntity.accepted().body(converter.entityToDto(genreService.update(genreDTO)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDTO saveNewGenre(@RequestBody @Valid GenreDTO genreDTO) {
        return converter.entityToDto(genreService.save(converter.dtoToEntity(genreDTO)));
    }

}
