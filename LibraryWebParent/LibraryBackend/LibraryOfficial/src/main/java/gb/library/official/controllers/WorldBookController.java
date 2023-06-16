package gb.library.official.controllers;


import gb.library.common.dtos.WorldBookDTO;
import gb.library.backend.converters.WorldBookConverter;
import gb.library.official.services.WorldBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/worldBook")
@CrossOrigin
public class WorldBookController {
    private final WorldBookService worldBookService;
    private final WorldBookConverter converter;


    @GetMapping
    public List<WorldBookDTO> findAll(@RequestParam(required = false, name = "title") String title,
                                      @RequestParam(required = false, name = "author") String author,
                                      @RequestParam(required = false, name = "genre") String genre,
                                      @RequestParam(required = false, name = "ageRating") String ageRating,
                                      @RequestParam(required = false, name = "description") String description) {

        return converter.listEntities2dto(worldBookService.findAll(title, author, genre, ageRating, description));
    }

    @GetMapping("/{id}")
    public WorldBookDTO findById(@PathVariable Integer id) {
        return converter.entityToDto(worldBookService.findById(id));
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        worldBookService.deleteById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public WorldBookDTO update(@RequestBody @Valid WorldBookDTO worldBookDTO) {
        return converter.entityToDto(worldBookService.update(converter.dtoToEntity(worldBookDTO)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorldBookDTO saveNewBook(@RequestBody @Valid WorldBookDTO worldBookDTO) {
        return converter.entityToDto(worldBookService.save(converter.dtoToEntity(worldBookDTO)));
    }
}
