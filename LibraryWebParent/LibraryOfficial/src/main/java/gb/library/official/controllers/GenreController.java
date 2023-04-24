package gb.library.official.controllers;

import gb.library.common.entities.Genre;
import gb.library.official.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genres")
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public List<Genre> findAll(@RequestParam(required = false, name = "genre") String genre,
                               @RequestParam(required = false, name = "description") String description) {

        return genreService.findAll(genre, description);
    }

    @GetMapping("/{id}")
    public Genre findById(@PathVariable Integer id) {
        return genreService.findById(id);
    }

    @DeleteMapping("delete/{id}")
    public void deleteAuthor(@PathVariable Integer id) {
        genreService.deleteById(id);
    }

    @PutMapping
    public Genre updateGenre(@RequestBody Genre genre) {
        return genreService.update(genre);
    }

    @PostMapping
    public Genre saveNewGenre(@RequestBody Genre genre) {
        return genreService.save(genre);
    }
}
