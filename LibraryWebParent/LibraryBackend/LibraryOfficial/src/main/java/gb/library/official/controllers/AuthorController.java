package gb.library.official.controllers;

import gb.library.common.entities.Author;
import gb.library.official.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authors")
@CrossOrigin
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public Page<Author> findAll(@RequestParam(defaultValue = "1", name = "p") int pageIndex,
                                @RequestParam(required = false, name = "firstName") String firstName,
                                @RequestParam(required = false, name = "lastName") String lastName) {

        if (firstName != null && !firstName.isBlank()) {
            return authorService.findByFirstName(pageIndex, firstName);
        }
        if (lastName != null && !lastName.isBlank()) {
            return authorService.findByLastName(pageIndex, lastName);
        }
        return authorService.findAll(pageIndex);
    }

    @GetMapping("/all")
    public List<Author> getAll() {
        return authorService.searchAuthors("");
    }



    @GetMapping("/{id}")
    public Author findById(@PathVariable Integer id) {
        return authorService.findById(id);
    }

    @DeleteMapping("delete/{id}")
    public void deleteAuthor(@PathVariable Integer id) {
        authorService.deleteById(id);
    }

    @PutMapping
    public Author updateAuthor(@RequestBody Author author) {
        return authorService.update(author);
    }

    @PostMapping
    public Author saveNewAuthor(@RequestBody Author author) {
        return authorService.save(author);
    }
}
