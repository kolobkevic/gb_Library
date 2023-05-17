package gb.library.official.controllers;

import gb.library.common.dtos.GenreDTO;
import gb.library.official.converters.GenreConverter;
import gb.library.official.services.GenreService;
import gb.library.official.validators.GenreValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genres")
@CrossOrigin
public class GenreController {
    private final GenreService genreService;
    private final GenreConverter converter;
    private final GenreValidator validator;

    @GetMapping
    public List<GenreDTO> findAll(@RequestParam(required = false, name = "name") String name,
                               @RequestParam(required = false, name = "description") String description) {


        return genreService.findAll(name, description).stream().map(converter::entityToDto).toList();
    }

    @GetMapping("/{id}")
    public GenreDTO findById(@PathVariable Integer id) {
        return converter.entityToDto(genreService.findById(id));
    }

    @DeleteMapping("delete/{id}")
    public void deleteAuthor(@PathVariable Integer id) {
        genreService.deleteById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GenreDTO updateGenre(@RequestBody GenreDTO genreDTO) {
        validator.validate(genreDTO);
        return converter.entityToDto(genreService.update(genreDTO));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDTO saveNewGenre(@RequestBody GenreDTO genreDTO) {
        validator.validate(genreDTO);
        return converter.entityToDto(genreService.save(converter.dtoToEntity(genreDTO)));
    }
}
