package gb.library.official.controllers;

import gb.library.backend.converters.AuthorConverter;
import gb.library.common.dtos.AuthorDTO;
import gb.library.official.services.AuthorService;
import jakarta.validation.Valid;
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
    private final AuthorConverter converter;

    @GetMapping
    public Page<AuthorDTO> findAll(@RequestParam(defaultValue = "1", name = "p") int pageIndex,
                                @RequestParam(required = false, name = "firstName") String firstName,
                                @RequestParam(required = false, name = "lastName") String lastName) {

        if (firstName != null && !firstName.isBlank()) {
            return authorService.findByFirstName(pageIndex, firstName).map(converter::entity2dto);
        }
        if (lastName != null && !lastName.isBlank()) {
            return authorService.findByLastName(pageIndex, lastName).map(converter::entity2dto);
        }
        return authorService.findAll(pageIndex).map(converter::entity2dto);
    }

    @GetMapping("/all")
    public List<AuthorDTO> getAll() {
        return converter.listEntity2dto(authorService.searchAuthors(""));
    }

    @GetMapping("/{id}")
    public AuthorDTO findById(@PathVariable Integer id) {
        return converter.entity2dto(authorService.findById(id));
    }

    @DeleteMapping("delete/{id}")
    public void deleteAuthor(@PathVariable Integer id) {
        authorService.deleteById(id);
    }

    @PutMapping
    public AuthorDTO updateAuthor(@RequestBody @Valid AuthorDTO dto) {
        return converter.entity2dto(authorService.update(converter.dto2entity(dto)));
    }

    @PostMapping
    public AuthorDTO saveNewAuthor(@RequestBody @Valid AuthorDTO dto) {
        return converter.entity2dto(authorService.save(converter.dto2entity(dto)));
    }
}
